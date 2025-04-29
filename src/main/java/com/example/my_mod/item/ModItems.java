package com.example.my_mod.item;

import com.example.my_mod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.example.my_mod.ExampleMod.MODID;

public class ModItems {
    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<BlockItem> MY_BLOCK_ITEM = ITEM_REGISTRY.register("my_block_item", () -> new BlockItem(ModBlocks.MY_BLOCK.get(), new Item.Properties()));
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    public static final RegistryObject<BlockItem> BUILD_BLOCK_ITEM = ITEM_REGISTRY.register("build_block_item", () -> new BlockItem(ModBlocks.BUILD_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> START_BLOCK_ITEM = ITEM_REGISTRY.register("start_block_item", () -> new BlockItem(ModBlocks.START_BLOCK.get(), new Item.Properties()));
    public static void register(IEventBus modEventBus){
        ITEM_REGISTRY.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);

    }
}
