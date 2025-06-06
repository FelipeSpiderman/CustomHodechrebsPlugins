package com.FelipeSpiderman.custommobsounds;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomMobSounds implements ModInitializer {
    public static final String MOD_ID = "custommobsounds";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Custom Mob Sounds initialized!");
        SoundRegistry.registerSounds();
        ConfigHandler.loadConfig();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}