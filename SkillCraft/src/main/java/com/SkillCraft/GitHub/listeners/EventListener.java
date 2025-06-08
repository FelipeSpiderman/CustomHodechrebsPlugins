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

import java.util.EnumSet;
import java.util.Set;

public class EventListener implements Listener {
    private final SkillsManager skillsManager;

    private static final Set<Material> MINING_BLOCKS = EnumSet.noneOf(Material.class);
    private static final Set<Material> FORAGING_BLOCKS = EnumSet.noneOf(Material.class);
    private static final Set<Material> FARMING_BLOCKS = EnumSet.noneOf(Material.class);

    public EventListener(SkillsManager skillsManager) {
        this.skillsManager = skillsManager;
        initializeBlockSets();
    }

    private void initializeBlockSets() {
        // Mining Blocks
        MINING_BLOCKS.addAll(EnumSet.of(
            // Ores
            Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE,
            Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.LAPIS_ORE,
            Material.REDSTONE_ORE, Material.COPPER_ORE,
            // Deepslate Ores
            Material.DEEPSLATE_COAL_ORE, Material.DEEPSLATE_IRON_ORE,
            Material.DEEPSLATE_GOLD_ORE, Material.DEEPSLATE_DIAMOND_ORE,
            Material.DEEPSLATE_EMERALD_ORE, Material.DEEPSLATE_LAPIS_ORE,
            Material.DEEPSLATE_REDSTONE_ORE, Material.DEEPSLATE_COPPER_ORE,
            // Nether Ores
            Material.NETHER_GOLD_ORE, Material.NETHER_QUARTZ_ORE,
            Material.ANCIENT_DEBRIS
        ));

        // Foraging Blocks
        FORAGING_BLOCKS.addAll(EnumSet.of(
            // Logs
            Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG,
            Material.JUNGLE_LOG, Material.ACACIA_LOG, Material.DARK_OAK_LOG,
            Material.MANGROVE_LOG, Material.CHERRY_LOG,
            // Nether Stems
            Material.CRIMSON_STEM, Material.WARPED_STEM,
            // Leaves
            Material.OAK_LEAVES, Material.SPRUCE_LEAVES, Material.BIRCH_LEAVES,
            Material.JUNGLE_LEAVES, Material.ACACIA_LEAVES, Material.DARK_OAK_LEAVES,
            Material.MANGROVE_LEAVES, Material.CHERRY_LEAVES
        ));

        // Farming Blocks
        FARMING_BLOCKS.addAll(EnumSet.of(
            Material.WHEAT, Material.CARROTS, Material.POTATOES,
            Material.BEETROOTS, Material.MELON, Material.PUMPKIN,
            Material.BAMBOO, Material.SUGAR_CANE, Material.CACTUS,
            Material.COCOA, Material.NETHER_WART
        ));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();
        Player player = event.getPlayer();

        // Use the pre-defined sets for efficient block type checking
        if (MINING_BLOCKS.contains(blockType)) {
            skillsManager.addXp(player, "mining", calculateMiningXp(blockType));
        } else if (FORAGING_BLOCKS.contains(blockType)) {
            skillsManager.addXp(player, "foraging", calculateForagingXp(blockType));
        } else if (FARMING_BLOCKS.contains(blockType)) {
            skillsManager.addXp(player, "farming", calculateFarmingXp(blockType));
        }
    }

    private int calculateMiningXp(Material material) {
        if (material.name().contains("DIAMOND") || material.name().contains("EMERALD")) {
            return 20;
        } else if (material.name().contains("GOLD") || material.name().contains("REDSTONE")) {
            return 15;
        } else if (material.name().contains("IRON")) {
            return 10;
        }
        return 5;
    }

    private int calculateForagingXp(Material material) {
        if (material.name().contains("LOG") || material.name().contains("STEM")) {
            return 10;
        }
        return 5;
    }

    private int calculateFarmingXp(Material material) {
        if (material == Material.NETHER_WART || material == Material.COCOA) {
            return 15;
        }
        return 10;
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer != null) {
            skillsManager.addXp(killer, "combat", 10);
        }
    }

    @EventHandler
    public void onBrew(BrewEvent event) {
        if (event.getContents().getHolder() instanceof Player player) {
            skillsManager.addXp(player, "brewing", 15);
        }
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        skillsManager.addXp(event.getEnchanter(), "enchanting",
            event.getExpLevelCost() / 2);  // XP based on enchant cost
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(SkillsGUI.GUI_TITLE)) {
            event.setCancelled(true);
        }
    }
}