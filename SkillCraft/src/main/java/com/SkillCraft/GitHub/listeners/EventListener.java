package com.SkillCraft.GitHub.listeners;

import com.SkillCraft.GitHub.gui.SkillsGUI;
import com.SkillCraft.GitHub.managers.SkillsManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EnchantingInventory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EventListener implements Listener {
    private final SkillsManager skillsManager;

    // Define block categories
    private static final Set<String> MINING_BLOCKS = new HashSet<>(Arrays.asList(
        "ore", "stone", "deepslate", "blackstone", "netherrack"
    ));

    private static final Set<String> FORAGING_BLOCKS = new HashSet<>(Arrays.asList(
        "log", "leaves"
    ));

    private static final Set<String> FARMING_BLOCKS = new HashSet<>(Arrays.asList(
        "wheat", "carrot", "potato", "melon", "pumpkin", "beetroot", "bamboo"
    ));

    public EventListener(SkillsManager skillsManager) {
        this.skillsManager = skillsManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        String blockType = event.getBlock().getType().name().toLowerCase();
        Player player = event.getPlayer();

        // Check block type against our predefined categories
        if (MINING_BLOCKS.stream().anyMatch(blockType::contains)) {
            skillsManager.addXp(player, "mining", 10);
        } else if (FORAGING_BLOCKS.stream().anyMatch(blockType::contains)) {
            skillsManager.addXp(player, "foraging", 10);
        } else if (FARMING_BLOCKS.stream().anyMatch(blockType::contains)) {
            skillsManager.addXp(player, "farming", 10);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer != null) {
            skillsManager.addXp(killer, "combat", 10);
        }
    }

    @EventHandler
    public void onBrew(BrewEvent event) {
        if (event.getContents().getHolder() instanceof Player brewer) {
            skillsManager.addXp(brewer, "brewing", 10);
        }
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        skillsManager.addXp(event.getEnchanter(), "enchanting", 10);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(SkillsGUI.GUI_TITLE)) {
            event.setCancelled(true);
        }
    }
}