package com.example.my_mod.block;

import com.example.my_mod.ExampleMod;
import com.example.my_mod.block.entities.MyBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ExampleMod.MODID);


    public static final RegistryObject<BlockEntityType<MyBlockEntity>> GEM_POLISHING_BE =
            BLOCK_ENTITIES.register("gem_polishing_be", () ->
                    BlockEntityType.Builder.of(MyBlockEntity::new,
                            ModBlocks.MY_BLOCK.get()).build(null));



        public static void register(IEventBus eventBus){
            BLOCK_ENTITIES.register(eventBus);
        }
}
