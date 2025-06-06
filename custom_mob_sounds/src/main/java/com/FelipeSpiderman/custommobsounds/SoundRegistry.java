package com.FelipeSpiderman.custommobsounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SoundRegistry {
    public static final Map<String, SoundEvent> SOUND_EVENTS = new HashMap<>();

    public static void registerSounds() {
        // Called by ConfigHandler to register sounds dynamically
    }

    public static void registerSound(String soundId, Identifier identifier) {
        SoundEvent soundEvent = SoundEvent.of(identifier);
        Registry.register(Registries.SOUND_EVENT, identifier, soundEvent);
        SOUND_EVENTS.put(soundId, soundEvent);
    }
}