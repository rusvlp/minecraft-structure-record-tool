package com.example.my_mod.item.blockItems;

import com.example.my_mod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.example.my_mod.ExampleMod.MODID;

public class MyBlockItem extends BlockItem {
    public static final int variable = 0;


    public MyBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }


    //public static final RegistryObject<BlockEntityType<MyBlockEntity>> MY_BLOCK_ENTITY = BLOCK_ENTITIES.register("my_block_entity", () -> BlockEntityType.Builder.of(MyBlockEntityю, MyBlock.MY_BLOCK.get()).build(null));


    @Override
    protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState) {
        return super.placeBlock(pContext, pState);
    }
}
