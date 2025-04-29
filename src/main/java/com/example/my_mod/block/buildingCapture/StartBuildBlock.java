package com.example.my_mod.block.buildingCapture;

import com.example.my_mod.ExampleMod;
import com.example.my_mod.utils.CommandHelper;
import com.example.my_mod.utils.WriteCoordsToFileHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class StartBuildBlock extends Block {
    public StartBuildBlock(Properties pProperties) {
        super(pProperties);
    }


    //TODO: Do not allow place block if start block is already exists or capturing is not started
    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {

        WriteCoordsToFileHelper.getInstance().ifPresentOrElse(
                h -> {
                    if (!h.isStartBlockSet()){
                        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
                        h.setStartBlock(pPos);
                        CommandHelper.sendMessageToChat("Стартовый блок установлен");
                    } else {
                        CommandHelper.sendMessageToChat("Стартовый блок уже установлен. Для установки нового сломайте предыдущий");
                        ExampleMod.removeBlockByPosition(pPos);
                    }
                },
                () -> ExampleMod.removeBlockByPosition(pPos)
        );
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        WriteCoordsToFileHelper.getInstance().ifPresent(h -> {
           h.removeStartBlock();
           CommandHelper.sendMessageToChat("Стартовый блок удален");
        });
    }


}
