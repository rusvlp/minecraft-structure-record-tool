package com.example.my_mod;

import com.example.my_mod.block.buildingCapture.StartBuildBlock;
import com.example.my_mod.utils.CommandHelper;
import com.example.my_mod.utils.CoordsFileReader;
import com.example.my_mod.utils.WriteCoordsToFileHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {

        if (!(event.getEntity() instanceof Player)) return;

        BlockPos pos = event.getPos();
        BlockState blockState = event.getPlacedBlock();
        Block block = blockState.getBlock();
        if (blockState.getBlock() instanceof StartBuildBlock) return;

        ResourceLocation loc = BuiltInRegistries.BLOCK.getKey(block);
        WriteCoordsToFileHelper.getInstance().ifPresent(
                helper -> {
                    if (helper.isStartBlockSet()){
                        helper.addBlock(pos, loc);
                    }
                }
        );

        handleStructurePlacement(event);
    }

    private static void handleStructurePlacement(BlockEvent.EntityPlaceEvent event){
        BlockState blockState = event.getPlacedBlock();
        Block block = blockState.getBlock();
        Map<BlockPos, Block> blocks = CoordsFileReader.getStructureByBLock(block);
        if (blocks == null) return;
        for (Map.Entry<BlockPos, Block> entry: blocks.entrySet()){
            Minecraft.getInstance().level.setBlock(entry.getKey(), entry.getValue().defaultBlockState(), 2);
        }
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

