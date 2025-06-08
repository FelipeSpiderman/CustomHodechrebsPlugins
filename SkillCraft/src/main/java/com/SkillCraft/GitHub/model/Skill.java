package com.SkillCraft.GitHub.model;

import org.bukkit.Material;
import org.bukkit.Statistic;

public enum Skill {
    MINING("Mining", Statistic.MINE_BLOCK, Material.DIAMOND_PICKAXE, "mining", 10),
    FORAGING("Foraging", Statistic.MINE_BLOCK, Material.OAK_LOG, "foraging", 12), // Uses MINE_BLOCK, can be filtered by material type
    FARMING("Farming", Statistic.MINE_BLOCK, Material.WHEAT, "farming", 14), // Uses MINE_BLOCK, can be filtered by material type
    COMBAT("Combat", Statistic.MOB_KILLS, Material.DIAMOND_SWORD, "combat", 16),
    BREWING("Brewing", Statistic.USE_ITEM, Material.BREWING_STAND, "brewing", 28),
    ENCHANTING("Enchanting", Statistic.USE_ITEM, Material.ENCHANTING_TABLE, "enchanting", 30);

    private final String displayName;
    private final Statistic statistic;
    private final Material icon;
    private final String configKey;
    private final int guiSlot;

    Skill(String displayName, Statistic statistic, Material icon, String configKey, int guiSlot) {
        this.displayName = displayName;
        this.statistic = statistic;
        this.icon = icon;
        this.configKey = configKey;
        this.guiSlot = guiSlot;
    }

    // Note on Statistics:
    // Foraging & Farming don't have perfect high-level stats.
    // A simple approach uses Statistic.MINE_BLOCK and relies on players breaking the right blocks (logs, crops).
    // A more advanced plugin would need event listeners to precisely track these actions, but this fits the "simple" requirement.
    // For Combat, Statistic.MOB_KLLS is generally better than PLAYER_KILLS for PvE servers.

    public String getDisplayName() { return displayName; }
    public Statistic getStatistic() { return statistic; }
    public Material getIcon() { return icon; }
    public String getConfigKey() { return configKey; }
    public int getGuiSlot() { return guiSlot; }


    public record SkillProgress(int level, long currentXp, long xpToNextLevel) {
    }
}