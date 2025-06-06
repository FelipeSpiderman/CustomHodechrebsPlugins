package com.FelipeSpiderman.custommobsounds;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.Identifier;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigHandler {
    private static final String CONFIG_DIR = "config/custommobsounds/";
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
            CustomMobSounds.LOGGER.error("Failed to load config: ", e);
        }
    }

    private static void createDefaultConfig(File configFile) throws IOException {
        JsonObject defaultConfig = new JsonObject();
        JsonObject creeperSounds = new JsonObject();
        creeperSounds.addProperty("ambient", "custommobsounds:creeper_ambient");
        creeperSounds.addProperty("hurt", "custommobsounds:creeper_hurt");
        creeperSounds.addProperty("death", "custommobsounds:creeper_death");
        defaultConfig.add("minecraft:creeper", creeperSounds);

        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(defaultConfig, writer);
        }
    }

    private static void generateSoundsJson() throws IOException {
        JsonObject soundsJson = new JsonObject();
        Path soundsDir = Paths.get(SOUNDS_DIR);

        // Scan sounds directory for .ogg and .mp3 files
        File[] soundFiles = soundsDir.toFile().listFiles((dir, name) -> name.endsWith(".ogg") || name.endsWith(".mp3"));
        if (soundFiles != null) {
            for (File soundFile : soundFiles) {
                String soundName = soundFile.getName().replaceAll("\\.(ogg|mp3)", "");
                String soundId = "custommobsounds:" + soundName;
                JsonObject soundEntry = new JsonObject();
                JsonArray soundsArray = new JsonArray();
                soundsArray.add("custommobsounds:" + soundName);
                soundEntry.add("sounds", soundsArray);
                soundsJson.add(soundName, soundEntry);

                // Register sound event
                SoundRegistry.registerSound(soundId, CustomMobSounds.id(soundName));
            }
        }

        // Write sounds.json to config directory
        Path soundsJsonPath = Paths.get(SOUNDS_JSON_PATH);
        Files.createDirectories(soundsJsonPath.getParent());
        try (FileWriter writer = new FileWriter(soundsJsonPath.toFile())) {
            GSON.toJson(soundsJson, writer);
        }
    }
}