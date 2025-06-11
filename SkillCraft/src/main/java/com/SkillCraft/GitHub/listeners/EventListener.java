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

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(SkillsGUI.GUI_TITLE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Material material = event.getBlock().getType();
        String skill = skillsManager.getSkillForMaterial(material);

        if (!skill.isEmpty()) {
            double xpGained = skillsManager.getXpForMaterial(material);
            boolean grantXp = false;

            if (skill.equals("farming")) {
                if (event.getBlock().getBlockData() instanceof Ageable ageable) {
                    if (ageable.getAge() == ageable.getMaximumAge()) {
                        grantXp = true;
                    }
                } else {
                    grantXp = true;
                }
            } else {
                grantXp = true;
            }

            if (grantXp && xpGained > 0) {
                skillsManager.showXpGainNotification(event.getPlayer(), skill, xpGained);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null && event.getEntity() instanceof Monster) {
            double xpGained = skillsManager.getXpForEntity(event.getEntityType());
            if (xpGained > 0) {
                skillsManager.showXpGainNotification(event.getEntity().getKiller(), "combat", xpGained);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBrew(BrewEvent event) {
        if (event.getContents().getViewers().stream().anyMatch(v -> v instanceof Player)) {
            Player player = (Player) event.getContents().getViewers().stream().filter(v -> v instanceof Player).findFirst().get();
            skillsManager.showXpGainNotification(player, "brewing", 25.0);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEnchantItem(EnchantItemEvent event) {
        Player player = event.getEnchanter();
        double xpGained = event.getExpLevelCost() * 15.0;
        skillsManager.showXpGainNotification(player, "enchanting", xpGained);
    }
}