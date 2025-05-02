package com.example.my_mod.utils;

import com.example.my_mod.ExampleMod;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

import java.io.IOException;
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
        startBlockPos = null;
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

    private void save() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.blocks);
        System.out.println(json);
        CommandHelper.sendMessageToChat("SAVING FROM SAVE FUNCTION");
    }

    public void addBlock(BlockPos pos, Class<? extends Block> clazz){
        if (startBlockPos == null){
            throw new NoStartBlockException();
        }
        BlockPos newPos = startBlockPos.offset(startBlockPos);
        blocks.put(newPos, clazz);
    }

    public boolean containsBlock(BlockPos pos){
        return blocks.containsKey(pos);
    }

    public void removeBlock(BlockPos pos){
        blocks.remove(pos);
    }

    public boolean isStartBlockSet(){
        return startBlockPos != null;
    }

    public void endCapture() throws IOException {
        save();
        ExampleMod.removeBlockByPosition(startBlockPos);
        previousCaptures.add(this);
        instance = null;
    }
}
