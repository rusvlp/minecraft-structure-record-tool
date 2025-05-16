package com.example.my_mod.utils;

import com.example.my_mod.utils.annotations.PlaceStructure;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class CoordsFileReader {
    public static Map<Block, Map<BlockPos, Block>> blockPositions = new HashMap<>();

    public static Map<BlockPos, Block> getStructureByBLock(Block block){
        return blockPositions.get(block);
    }

    public static void initialize()  {
        System.out.println("Initializing CoordsFileReader");
        for (Block block : ForgeRegistries.BLOCKS.getValues()) {
            Class<? extends Block> clazz = block.getClass();
            System.out.println(clazz.getName());
            if (clazz.isAnnotationPresent(PlaceStructure.class)){
                PlaceStructure ann = clazz.getDeclaredAnnotation(PlaceStructure.class);
                String path = ann.value();
                Map<BlockPos, Block> blocks = getBlocksByJson(path);
                System.out.println("Found place structure block " + clazz.getSimpleName());
                blockPositions.put(block, blocks);
            }
        }

    }

    private static Map<BlockPos, Block> getBlocksByJson(String path){
        Map<BlockPos, Block> blocks = new HashMap<>();
        try (InputStream fis = new FileInputStream(path);
            JsonReader reader = Json.createReader(fis)) {

            JsonObject jsonObject = reader.readObject();

            for (Map.Entry<String, javax.json.JsonValue> entry : jsonObject.entrySet()) {
                String coordKey = entry.getKey();
                String blockId = jsonObject.getString(coordKey);

                String[] parts = coordKey.split(":");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int z = Integer.parseInt(parts[2]);

                ResourceLocation loc = ResourceLocation.parse(blockId);
                Block block = ForgeRegistries.BLOCKS.getValue(loc);

                BlockPos pos = new BlockPos(x, y, z);
                blocks.put(pos, block);
            }

        } catch (Exception e) {
            CommandHelper.sendMessageToChat("Не удалось прочтитать json файл. Подробности в консоли");
            e.printStackTrace();
        }
        return blocks;
    }


}
