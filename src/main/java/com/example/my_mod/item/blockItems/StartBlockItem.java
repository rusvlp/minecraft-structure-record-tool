package com.example.my_mod.item.blockItems;

import com.example.my_mod.ExampleMod;
import com.example.my_mod.utils.CommandHelper;
import com.example.my_mod.utils.WriteCoordsToFileHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class StartBlockItem extends BlockItem {
    public StartBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        if (pContext.getLevel().isClientSide()) {
            return InteractionResult.FAIL;
        }
        Optional<WriteCoordsToFileHelper> helperOptional = WriteCoordsToFileHelper.getInstance();

        if (helperOptional.isEmpty()) {
            CommandHelper.sendMessageToChat("Запись не ведется");
            return InteractionResult.FAIL;
        }

        WriteCoordsToFileHelper helper = helperOptional.get();

        if (helper.isStartBlockSet()){
            CommandHelper.sendMessageToChat("Стартовый блок уже установлен");
            return InteractionResult.FAIL;
        }

        helper.setStartBlock(pContext.getClickedPos());


        return super.place(pContext);
    }
}
