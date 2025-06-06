package com.FelipeSpiderman.custommobsounds;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;

import java.util.HashMap;
import java.util.Map;

public class SoundRegistry {
    private static final Map<String, Sound> SOUND_EVENTS = new HashMap<>();

    public static void registerSounds() {
        // Called by ConfigHandler to register sounds dynamically
    }

    public static void registerSound(String soundId, String soundName) {
        try {
            Sound sound = Sound.valueOf(soundName.toUpperCase());
            SOUND_EVENTS.put(soundId, sound);
        } catch (IllegalArgumentException e) {
            // Handle invalid sound names
            CustomMobSounds.getInstance().getLogger().warning("Invalid sound name: " + soundName);
        }
    }

    public static void playSound(String soundId, org.bukkit.Location location) {
        Sound sound = SOUND_EVENTS.get(soundId);
        if (sound != null) {
            location.getWorld().playSound(location, sound, SoundCategory.HOSTILE, 1.0f, 1.0f);
        }
    }
}