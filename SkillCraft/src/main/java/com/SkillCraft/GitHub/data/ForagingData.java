package com.SkillCraft.GitHub.data;

import org.bukkit.Material;
import java.util.*;

public class ForagingData {
    private final Map<Material, Double> xpValues = new HashMap<>();
    private final List<Long> levelXpRequirements = new ArrayList<>();

    public ForagingData() {
        initializeXpValues();
        initializeLevelRequirements();
    }

    private void initializeXpValues() {
        // Wood types
        addWoodXpValues();
        addLeafXpValues();
        addMushroomValues();
    }

    private void addWoodXpValues() {
        // Regular logs
        List<Material> regularLogs = Arrays.asList(
            Material.OAK_LOG, Material.SPRUCE_LOG,
            Material.BIRCH_LOG, Material.JUNGLE_LOG,
            Material.ACACIA_LOG, Material.DARK_OAK_LOG,
            Material.MANGROVE_LOG, Material.CHERRY_LOG
        );
        regularLogs.forEach(log -> xpValues.put(log, 8.0));

        // Stripped logs
        List<Material> strippedLogs = Arrays.asList(
            Material.STRIPPED_OAK_LOG, Material.STRIPPED_SPRUCE_LOG,
            Material.STRIPPED_BIRCH_LOG, Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_ACACIA_LOG, Material.STRIPPED_DARK_OAK_LOG,
            Material.STRIPPED_MANGROVE_LOG, Material.STRIPPED_CHERRY_LOG
        );
        strippedLogs.forEach(log -> xpValues.put(log, 6.0));

        // Special wood types
        xpValues.put(Material.CRIMSON_STEM, 12.0);
        xpValues.put(Material.WARPED_STEM, 12.0);
        xpValues.put(Material.STRIPPED_CRIMSON_STEM, 10.0);
        xpValues.put(Material.STRIPPED_WARPED_STEM, 10.0);
    }

    private void addLeafXpValues() {
        List<Material> leaves = Arrays.asList(
            Material.OAK_LEAVES, Material.SPRUCE_LEAVES,
            Material.BIRCH_LEAVES, Material.JUNGLE_LEAVES,
            Material.ACACIA_LEAVES, Material.DARK_OAK_LEAVES,
            Material.MANGROVE_LEAVES, Material.CHERRY_LEAVES,
            Material.AZALEA_LEAVES, Material.FLOWERING_AZALEA_LEAVES
        );
        leaves.forEach(leaf -> xpValues.put(leaf, 2.0));
    }

    private void addMushroomValues() {
        xpValues.put(Material.MUSHROOM_STEM, 15.0);
        xpValues.put(Material.RED_MUSHROOM_BLOCK, 10.0);
        xpValues.put(Material.BROWN_MUSHROOM_BLOCK, 10.0);
    }

    private void initializeLevelRequirements() {
        // Level 0 requires 0 XP
        levelXpRequirements.add(0L);

        // Progressive leveling curve
        long[] xpPerLevel = {
            50, 100, 150, 200, 300,    // Levels 1-5
            450, 600, 800, 1000, 1250, // Levels 6-10
            1500, 2000, 2500, 3000,    // Levels 11-14
            4000, 5000, 6000, 7500,    // Levels 15-18
            9000, 11000, 13000,        // Levels 19-21
            15000, 17500               // Levels 22-23
        };

        long cumulativeXp = 0;
        for (long xpNeeded : xpPerLevel) {
            cumulativeXp += xpNeeded;
            levelXpRequirements.add(cumulativeXp);
        }
    }

    public double getXpValue(Material material) {
        return xpValues.getOrDefault(material, 0.0);
    }

    public long getLevelRequirement(int level) {
        if (level < 0 || level >= levelXpRequirements.size()) {
            return Long.MAX_VALUE;
        }
        return levelXpRequirements.get(level);
    }

    public int getMaxLevel() {
        return levelXpRequirements.size() - 1;
    }
}
