package com.example.my_mod.utils;

import net.minecraft.core.BlockPos;

public class UtilMethods {
    public static BlockPos negativeOffset(BlockPos origin, BlockPos pos){
        return new BlockPos(origin.getX()-pos.getX(), origin.getY()-pos.getY(), origin.getZ() - pos.getZ());
    }

}
