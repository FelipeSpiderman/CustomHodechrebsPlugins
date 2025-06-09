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
        String skill = "";
        double xpGained = 0.0;
        Material material = event.getBlock().getType();

        if (isMiningBlock(material)) {
            skill = "mining";
            xpGained = getMiningXp(material);
        } else if (isForagingBlock(material)) {
            skill = "foraging";
            xpGained = 10.0;
        } else if (isFarmingBlock(material)) {
            if (event.getBlock().getBlockData() instanceof Ageable ageable && ageable.getAge() == ageable.getMaximumAge()) {
                skill = "farming";
                xpGained = getFarmingXp(material);
            }
        }

        if (!skill.isEmpty() && xpGained > 0) {
            skillsManager.showXpGainNotification(event.getPlayer(), skill, xpGained);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null && event.getEntity() instanceof Monster) {
            double xpGained = getCombatXp(event.getEntityType().name());
            if (xpGained > 0) {
                skillsManager.showXpGainNotification(event.getEntity().getKiller(), "combat", xpGained);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBrew(BrewEvent event) {
        if (event.getContents().getViewers().stream().anyMatch(viewer -> viewer instanceof Player)) {
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

    private boolean isMiningBlock(Material m) { return m.name().endsWith("_ORE") || m.name().contains("STONE") || m.name().equals("NETHERRACK") || m.name().equals("ANCIENT_DEBRIS"); }
    private boolean isForagingBlock(Material m) { return m.name().endsWith("_LOG") || m.name().endsWith("_STEM"); }
    private boolean isFarmingBlock(Material m) { return m.name().equals("WHEAT") || m.name().equals("CARROTS") || m.name().equals("POTATOES") || m.name().equals("BEETROOTS") || m.name().equals("NETHER_WART"); }

    private double getMiningXp(Material m) {
        String n = m.name();
        if (n.contains("DIAMOND") || n.contains("EMERALD") || n.equals("ANCIENT_DEBRIS")) return 50.0;
        if (n.contains("GOLD")) return 15.0;
        if (n.contains("LAPIS")) return 20.0;
        if (n.contains("IRON")) return 10.0;
        if (n.contains("COAL")) return 5.0;
        if (n.contains("COPPER")) return 3.0;
        if (n.contains("QUARTZ")) return 5.0;
        if (n.contains("NETHERRACK")) return 0.5;
        return 1.0;
    }

    private double getFarmingXp(Material m) {
        return switch (m) {
            case WHEAT, CARROTS, POTATOES -> 15.0;
            case BEETROOTS -> 12.0;
            case NETHER_WART -> 20.0;
            default -> 0.0;
        };
    }

    private double getCombatXp(String entityName) {
        return switch (entityName) {
            case "ZOMBIE", "SKELETON", "SPIDER", "CREEPER" -> 10.0;
            case "ENDERMAN", "BLAZE", "PIGLIN_BRUTE" -> 25.0;
            case "WITHER_SKELETON" -> 40.0;
            case "WITHER" -> 1000.0;
            case "ENDER_DRAGON" -> 2000.0;
            default -> 5.0;
        };
    }
}