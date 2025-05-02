package com.example.my_mod.block.buildingCapture;

import com.example.my_mod.utils.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;



import java.util.Optional;

@SuppressWarnings("deprecation")
public abstract class CapturableBlock extends Block {
    public CapturableBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
//        Optional<WriteCoordsToFileHelper> heplerOptional = WriteCoordsToFileHelper.getInstance();
//        if (!heplerOptional.isPresent()) {
//            CommandHelper.sendMessageToChat("Запись не ведется");
//            return;
//        }
//        WriteCoordsToFileHelper helper = heplerOptional.get();
//        if (!helper.isStartBlockSet()){
//            CommandHelper.sendMessageToChat("Стартовый блок не установлен");
//            return;
//        }
//        helper.addBlock(pPos, this.getClass());
//        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }


    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
//        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
