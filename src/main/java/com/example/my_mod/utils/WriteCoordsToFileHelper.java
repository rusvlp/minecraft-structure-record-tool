package com.example.my_mod.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class WriteCoordsToFileHelper {
    public final String filename;
    public static List<WriteCoordsToFileHelper> previousCaptures = new ArrayList<WriteCoordsToFileHelper>();
    private static WriteCoordsToFileHelper instance;
    private final HashMap<BlockPos, Class<? extends Block>> blocks = new HashMap<>();

    private BlockPos startBlockPos;

    public static void startCapture(String filename) {
        instance = new WriteCoordsToFileHelper(filename);
    }


    public static Optional<WriteCoordsToFileHelper> getInstance() {
        return Optional.ofNullable(instance);
    }

    public static boolean isCapturing(){
        return instance != null;
    }


    private WriteCoordsToFileHelper(String filename) {
        this.filename = filename;
    }

    public void setStartBlock(BlockPos pos) {
        this.startBlockPos = pos;
        for (Map.Entry<BlockPos, Class<? extends Block>> entry : blocks.entrySet()) {
            BlockPos newPos = entry.getKey().offset(startBlockPos);
            Class<? extends Block> block = entry.getValue();
            blocks.put(newPos, block);
        }

    }

    public void removeStartBlock() {
        this.startBlockPos = null;
    }

    private void save(){
        // Write to file
    }

    public void addBlock(BlockPos pos, Class<? extends Block> clazz){
        if (startBlockPos == null){
            throw new NoStartBlockException();
        }
        blocks.put(pos, clazz);
    }

    public void removeBlock(BlockPos pos){
        blocks.remove(pos);
    }

    public boolean isStartBlockSet(){
        return startBlockPos != null;
    }

    public void endCapture(){
        save();
        previousCaptures.add(this);
        instance = null;
    }
}
