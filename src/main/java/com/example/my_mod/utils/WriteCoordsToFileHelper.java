package com.example.my_mod.utils;

import com.example.my_mod.ExampleMod;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

import static com.mojang.text2speech.Narrator.LOGGER;

public class WriteCoordsToFileHelper {
    public final String filename;
    public static List<WriteCoordsToFileHelper> previousCaptures = new ArrayList<WriteCoordsToFileHelper>();
    private static WriteCoordsToFileHelper instance;
    private final HashMap<BlockPos, ResourceLocation> blocks = new HashMap<>();

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
        for (Map.Entry<BlockPos, ResourceLocation> entry : blocks.entrySet()) {
            BlockPos newPos = entry.getKey().offset(startBlockPos);
            ResourceLocation loc = entry.getValue();
            blocks.put(newPos, loc);
        }

    }

    public void removeStartBlock() {
        this.startBlockPos = null;
    }

    private void save() throws IOException {

        JsonObjectBuilder builder = Json.createObjectBuilder();
         this.blocks.forEach((key, value) -> builder.add(key.getX() + ":" + key.getY() + ":" + key.getZ(),
                 value.toString()
         ));
        String json = builder.build().toString();
        LOGGER.debug(json);
        writeFile(json);
    }

    private void writeFile(String json){
        try {
            Path path = Path.of(filename);

            // Создаём директории, если их нет
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            // Запись файла
            Files.write(path, json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("JSON успешно сохранён в " + filename);
        } catch (IOException e) {
            e.printStackTrace(); // или логгирование
        }
    }


    public void addBlock(BlockPos pos, ResourceLocation loc){
        if (startBlockPos == null){
            throw new NoStartBlockException();
        }
        BlockPos newPos = startBlockPos.offset(pos);
        CommandHelper.sendMessageToChat("Блок записан " + newPos.getX() + " " + newPos.getY() + " " + newPos.getZ() + ", " + loc);
        blocks.put(newPos, loc);
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
        System.out.println("end capture");
        save();
        ExampleMod.removeBlockByPosition(startBlockPos);
        previousCaptures.add(this);
        instance = null;
    }
}
