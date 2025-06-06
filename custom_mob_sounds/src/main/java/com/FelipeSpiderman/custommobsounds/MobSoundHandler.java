package com.FelipeSpiderman.custommobsounds;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobSoundHandler implements Listener {
    private final CustomMobSounds plugin;

    public MobSoundHandler(CustomMobSounds plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        LivingEntity entity = (LivingEntity) event.getEntity();
        String mobId = entity.getType().name().toLowerCase();
        // Handle hurt sound
        // You'll need to implement your sound playing logic here
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        String mobId = entity.getType().name().toLowerCase();
        // Handle death sound
        // You'll need to implement your sound playing logic here
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        LivingEntity entity = (LivingEntity) event.getEntity();
        String mobId = entity.getType().name().toLowerCase();
        // Handle ambient sound
        // You'll need to implement your sound playing logic here
    }
}