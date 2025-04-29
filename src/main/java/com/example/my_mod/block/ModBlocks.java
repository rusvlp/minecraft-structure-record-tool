package com.example.my_mod.block;

import com.example.my_mod.block.buildingCapture.BuildBlock;
import com.example.my_mod.block.buildingCapture.StartBuildBlock;
import com.example.my_mod.block.custom.MyBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.example.my_mod.ExampleMod.MODID;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Block> MY_BLOCK = BLOCK_REGISTRY.register("my_block", () -> new MyBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));

    public static final RegistryObject<Block> BUILD_BLOCK = BLOCK_REGISTRY.register("build_block", ()->new BuildBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    public static final RegistryObject<Block> START_BLOCK = BLOCK_REGISTRY.register("start_block", ()-> new StartBuildBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));

    public static void register(IEventBus bus){
        BLOCK_REGISTRY.register(bus);
    }
}
