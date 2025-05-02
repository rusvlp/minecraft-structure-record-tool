package com.example.my_mod;

import com.example.my_mod.utils.CommandHelper;
import com.example.my_mod.utils.WriteCoordsToFileHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        BlockPos pos = event.getPos();
        BlockState block = event.getPlacedBlock();



        WriteCoordsToFileHelper.getInstance().ifPresent(
                helper -> {
                    if (helper.isStartBlockSet()){
                        helper.addBlock(pos, block.getBlock().getClass());
                        CommandHelper.sendMessageToChat("Блок записан " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
                    }
                }
        );
    }

    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        BlockPos pos = event.getPos();
        WriteCoordsToFileHelper.getInstance().ifPresent(
                helper -> {
                    if (helper.isStartBlockSet() && helper.containsBlock(pos)){
                        helper.removeBlock(pos);
                        CommandHelper.sendMessageToChat("Блок удален " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
                    }
                }
        );
    }


}

