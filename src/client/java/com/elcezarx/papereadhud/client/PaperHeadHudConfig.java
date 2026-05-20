package com.elcezarx.papereadhud.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class PaperHeadHudConfig {

    public static int offsetY = 135;
    public static float alpha = 1.0f;
    public static int headSize = 16;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("paperheadhud.json");

    public static void load() {
        try {
            if (!CONFIG_PATH.toFile().exists()) {
                save();
                return;
            }

            FileReader reader = new FileReader(CONFIG_PATH.toFile());
            Data data = GSON.fromJson(reader, Data.class);
            reader.close();

            offsetY = data.offsetY;
            alpha = data.alpha;
            headSize = data.headSize;
        } catch (Exception e) {
            save();
        }
    }

    public static void save() {
        try {
            Data data = new Data();
            data.offsetY = offsetY;
            data.alpha = alpha;
            data.headSize = headSize;

            FileWriter writer = new FileWriter(CONFIG_PATH.toFile());
            GSON.toJson(data, writer);
            writer.close();
        } catch (Exception ignored) {
        }
    }

    private static class Data {
        int offsetY = 135;
        float alpha = 1.0f;
        int headSize = 16;
    }

}