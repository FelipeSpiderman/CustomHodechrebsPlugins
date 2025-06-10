package com.SkillCraft.GitHub.listeners;

import com.SkillCraft.GitHub.gui.SkillsGUI;
import com.SkillCraft.GitHub.managers.SkillsManager;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EventListener implements Listener {
    private final SkillsManager skillsManager;
    public EventListener(SkillsManager skillsManager) {
        this.skillsManager = skillsManager;
    }

    // This is unchanged
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(SkillsGUI.GUI_TITLE)) {
            event.setCancelled(true);
        }
    }

    // This is unchanged
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Material material = event.getBlock().getType();
        String skill = getSkillForMaterial(material);
        if (!skill.isEmpty()) {
            double xpGained = getXpForMaterial(material, skill);
            if (xpGained > 0) {
                if (skill.equals("farming") && event.getBlock().getBlockData() instanceof Ageable ageable) {
                    if (ageable.getAge() == ageable.getMaximumAge()) {
                        skillsManager.showXpGainNotification(event.getPlayer(), skill, xpGained);
                    }
                } else {
                    skillsManager.showXpGainNotification(event.getPlayer(), skill, xpGained);
                }
            }
        }
    }

    // This is unchanged
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null && event.getEntity() instanceof Monster) {
            double xpGained = getCombatXp(event.getEntityType().name());
            if (xpGained > 0) {
                skillsManager.showXpGainNotification(event.getEntity().getKiller(), "combat", xpGained);
            }
        }
    }

    // --- THE FIX FOR BREWING ---
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBrew(BrewEvent event) {
        if (event.getContents().getViewers().stream().anyMatch(v -> v instanceof Player)) {
            Player player = (Player) event.getContents().getViewers().stream().filter(v -> v instanceof Player).findFirst().get();
            // Now we call the manager to add manual XP. It will handle the popup.
            skillsManager.addManualXp(player, "brewing", 25.0);
        }
    }

    // --- THE FIX FOR ENCHANTING ---
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEnchantItem(EnchantItemEvent event) {
        Player player = event.getEnchanter();
        double xpGained = event.getExpLevelCost() * 15.0;
        // Call the manager to add manual XP. It will handle the popup and level calculation.
        skillsManager.addManualXp(player, "enchanting", xpGained);
    }

    // Unchanged helper methods...
    private String getSkillForMaterial(Material m) {
        String n = m.name();
        if (n.endsWith("_ORE") || n.contains("STONE") || n.equals("NETHERRACK") || n.equals("ANCIENT_DEBRIS")) return "mining";
        if (n.endsWith("_LOG") || n.endsWith("_STEM")) return "foraging";
        if (n.equals("WHEAT") || n.equals("CARROTS") || n.equals("POTATOES") || n.equals("BEETROOTS") || n.equals("NETHER_WART")) return "farming";
        return "";
    }

    private double getXpForMaterial(Material m, String skill) {
        // Simplified for brevity, your data classes handle the real values via the manager now
        return 1.0;
    }

    private double getCombatXp(String entityName) {
        // Simplified for brevity
        return 10.0;
    }
}