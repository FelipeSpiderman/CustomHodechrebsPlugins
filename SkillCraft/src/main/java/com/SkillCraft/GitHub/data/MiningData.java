package com.SkillCraft.GitHub.data;

import org.bukkit.Material;
import java.util.*;

public class MiningData {
    private final Map<Material, Double> xpValues = new HashMap<>();
    private final List<Long> levelXpRequirements = new ArrayList<>();

    public MiningData() {
        initializeXpValues();
        initializeLevelRequirements();
    }

    private void initializeXpValues() {
        // Ores
        addOreXpValues();
        addStoneVariantXpValues();
        addNetherXpValues();
    }

    private void addOreXpValues() {
        // Common ores
        xpValues.put(Material.COAL_ORE, 5.0);
        xpValues.put(Material.DEEPSLATE_COAL_ORE, 5.0);
        xpValues.put(Material.COPPER_ORE, 3.0);
        xpValues.put(Material.DEEPSLATE_COPPER_ORE, 3.0);

        // Mid-tier ores
        xpValues.put(Material.IRON_ORE, 10.0);
        xpValues.put(Material.DEEPSLATE_IRON_ORE, 10.0);
        xpValues.put(Material.GOLD_ORE, 15.0);
        xpValues.put(Material.DEEPSLATE_GOLD_ORE, 15.0);
        xpValues.put(Material.REDSTONE_ORE, 15.0);
        xpValues.put(Material.DEEPSLATE_REDSTONE_ORE, 15.0);

        // Valuable ores
        xpValues.put(Material.LAPIS_ORE, 20.0);
        xpValues.put(Material.DEEPSLATE_LAPIS_ORE, 20.0);
        xpValues.put(Material.DIAMOND_ORE, 50.0);
        xpValues.put(Material.DEEPSLATE_DIAMOND_ORE, 50.0);
        xpValues.put(Material.EMERALD_ORE, 75.0);
        xpValues.put(Material.DEEPSLATE_EMERALD_ORE, 75.0);
    }

    private void addStoneVariantXpValues() {
        List<Material> stoneMaterials = Arrays.asList(
            Material.STONE, Material.COBBLESTONE,
            Material.GRANITE, Material.DIORITE, Material.ANDESITE,
            Material.DEEPSLATE, Material.COBBLED_DEEPSLATE,
            Material.TUFF, Material.CALCITE,
            Material.SANDSTONE, Material.RED_SANDSTONE
        );
        stoneMaterials.forEach(material -> xpValues.put(material, 1.0));
    }

    private void addNetherXpValues() {
        xpValues.put(Material.NETHERRACK, 0.5);
        xpValues.put(Material.NETHER_GOLD_ORE, 3.0);
        xpValues.put(Material.NETHER_QUARTZ_ORE, 5.0);
        xpValues.put(Material.ANCIENT_DEBRIS, 75.0);
        xpValues.put(Material.BLACKSTONE, 1.0);
        xpValues.put(Material.BASALT, 1.0);
    }

    private void initializeLevelRequirements() {
        // Level 0 requires 0 XP
        levelXpRequirements.add(0L);

        // Hypixel-like leveling curve
        long[] xpPerLevel = {
            10, 20, 50, 74, 100,     // Levels 1-5
            150, 200, 300, 500, 750,  // Levels 6-10
            1000, 1500, 2000, 3000,   // Levels 11-14
            5000, 7500, 10000, 12500, // Levels 15-18
            15000, 17000, 18500,      // Levels 19-21
            20000, 25000              // Levels 22-23
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
