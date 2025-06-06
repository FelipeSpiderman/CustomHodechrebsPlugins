package com.FelipeSpiderman.custommobsounds;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Sound;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class ConfigHandler {
    private static final String CONFIG_DIR = "plugins/CustomMobSounds/";
    private static final String CONFIG_PATH = CONFIG_DIR + "custom_mob_sounds.json";
    private static final String SOUNDS_DIR = CONFIG_DIR + "sounds/";
    private static final String SOUNDS_JSON_PATH = CONFIG_DIR + "sounds.json";
    private static final Gson GSON = new Gson();

    public static JsonObject config;

    public static void loadConfig() {
        try {
            Path configPath = Paths.get(CONFIG_PATH);
            File configFile = configPath.toFile();
            Path soundsDir = Paths.get(SOUNDS_DIR);

            // Create config directory and default config if not exists
            if (!configFile.exists()) {
                Files.createDirectories(configPath.getParent());
                createDefaultConfig(configFile);
            }

            // Create sounds directory if not exists
            Files.createDirectories(soundsDir);

            // Load config
            try (FileReader reader = new FileReader(configFile)) {
                config = JsonParser.parseReader(reader).getAsJsonObject();
            }

            // Generate sounds.json based on sound files
            generateSoundsJson();
        } catch (IOException e) {
            CustomMobSounds.getInstance().getLogger().severe("Failed to load config: " + e.getMessage());
        }
    }

    private static void createDefaultConfig(File configFile) throws IOException {
        JsonObject defaultConfig = new JsonObject();
        JsonObject creeperSounds = new JsonObject();
        creeperSounds.addProperty("ambient", "ENTITY_CREEPER_PRIMED");
        creeperSounds.addProperty("hurt", "ENTITY_CREEPER_HURT");
        creeperSounds.addProperty("death", "ENTITY_CREEPER_DEATH");
        defaultConfig.add("creeper", creeperSounds);

        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(defaultConfig, writer);
        }
    }

    private static void generateSoundsJson() throws IOException {
        JsonObject soundsJson = new JsonObject();
        Path soundsDir = Paths.get(SOUNDS_DIR);

        File[] soundFiles = soundsDir.toFile().listFiles((dir, name) -> name.endsWith(".ogg") || name.endsWith(".mp3"));
        if (soundFiles != null) {
            for (File soundFile : soundFiles) {
                String soundName = soundFile.getName().replaceAll("\\.(ogg|mp3)", "");
                String soundId = CustomMobSounds.getInstance().getNamespacedId(soundName);

                // Register sound with default Minecraft sound as fallback
                SoundRegistry.registerSound(soundId, Sound.ENTITY_GENERIC_HURT.name());

                JsonObject soundEntry = new JsonObject();
                JsonArray soundsArray = new JsonArray();
                soundsArray.add(soundName);
                soundEntry.add("sounds", soundsArray);
                soundsJson.add(soundName, soundEntry);
            }
        }

        Path soundsJsonPath = Paths.get(SOUNDS_JSON_PATH);
        Files.createDirectories(soundsJsonPath.getParent());
        try (FileWriter writer = new FileWriter(soundsJsonPath.toFile())) {
            GSON.toJson(soundsJson, writer);
        }
    }
}