package com.FelipeSpiderman.custommobsounds;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class CustomMobSounds extends JavaPlugin {
    private static CustomMobSounds instance;

    @Override
    public void onEnable() {
        instance = this;
        ConfigHandler.loadConfig();
        getServer().getPluginManager().registerEvents(new MobSoundHandler(this), this);
        getLogger().info("CustomMobSounds has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CustomMobSounds has been disabled!");
    }

    public static CustomMobSounds getInstance() {
        return instance;
    }

    public String getNamespacedId(String name) {
        return "custommobsounds:" + name.toLowerCase();
    }
}