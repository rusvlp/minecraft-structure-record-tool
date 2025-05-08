package com.example.my_mod.utils;

import com.example.my_mod.ExampleMod;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandHelper {


    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event){
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();


        dispatcher.register(
                Commands.literal("startCapture")
                        .then(Commands.argument("path", StringArgumentType.greedyString())
                                .executes(CommandHelper::processStartCaptureCommand) // <- теперь здесь
                        )
        );

        dispatcher.register(Commands.literal("stopCapture")
                .executes(CommandHelper::processStopCaptureCommand));
    }


    private static int processStartCaptureCommand(CommandContext<CommandSourceStack> context){
        String path = StringArgumentType.getString(context, "path");
        System.out.println("Starting capture");
        if (WriteCoordsToFileHelper.isCapturing()){
            context.getSource().sendSuccess(() -> Component.literal("Запись уже ведется"), false);
            return 1;
        }
        WriteCoordsToFileHelper.startCapture(path);
        context.getSource().sendSuccess(() -> Component.literal("Запись началась"), false);
        return 1;
    }



    private static int processStopCaptureCommand(CommandContext<CommandSourceStack> context){

        Optional<WriteCoordsToFileHelper> helperOptional = WriteCoordsToFileHelper.getInstance();
        if (helperOptional.isEmpty()){
            context.getSource().sendSuccess(() -> Component.literal("Запись не была начата"), false);
            return 1;
        }

        WriteCoordsToFileHelper helper = helperOptional.get();
        try {
            helper.endCapture();
        } catch (IOException e){
            context.getSource().sendSuccess(() -> Component.literal("Не удалось сериализовать данные"), false);
            return 1;
        }

        context.getSource().sendSuccess(() -> Component.literal("Запись остановлена"), false);
        return 1;
    }

    public static void sendMessageToChat(String message){
        Minecraft.getInstance().gui.getChat().addMessage(Component.literal(message));
    }
}