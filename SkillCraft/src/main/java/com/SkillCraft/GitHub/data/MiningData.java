package com.SkillCraft.GitHub.data;

import org.bukkit.Material;
import java.util.*;

public class MiningData {
    private final Map<Material, Double> xpValues = new HashMap<>();
    private final long[] levelRequirements;

    public MiningData() {
        initializeXpValues();
        // This array now holds the XP needed FOR EACH level.
        this.levelRequirements = new long[]{
                10, 20, 50, 74, 100, 150, 200, 300, 500, 750,       // Levels 1-10
                1000, 1500, 2000, 3000, 5000, 7500, 10000, 12500,   // Levels 11-18
                15000, 17000, 18500, 20000, 25000                  // Levels 19-23
        };
    }

    public Map<Material, Double> getXpValues() {
        return this.xpValues;
    }

    public long[] getLevelRequirements() {
        return this.levelRequirements;
    }

    private void initializeXpValues() {
        // Ores
        xpValues.put(Material.COAL_ORE, 5.0);
        xpValues.put(Material.DEEPSLATE_COAL_ORE, 5.0);
        xpValues.put(Material.IRON_ORE, 10.0);
        xpValues.put(Material.DEEPSLATE_IRON_ORE, 10.0);
        xpValues.put(Material.COPPER_ORE, 3.0);
        xpValues.put(Material.DEEPSLATE_COPPER_ORE, 3.0);
        xpValues.put(Material.GOLD_ORE, 15.0);
        xpValues.put(Material.DEEPSLATE_GOLD_ORE, 15.0);
        xpValues.put(Material.REDSTONE_ORE, 15.0);
        xpValues.put(Material.DEEPSLATE_REDSTONE_ORE, 15.0);
        xpValues.put(Material.LAPIS_ORE, 20.0);
        xpValues.put(Material.DEEPSLATE_LAPIS_ORE, 20.0);
        xpValues.put(Material.DIAMOND_ORE, 50.0);
        xpValues.put(Material.DEEPSLATE_DIAMOND_ORE, 50.0);
        xpValues.put(Material.EMERALD_ORE, 75.0);
        xpValues.put(Material.DEEPSLATE_EMERALD_ORE, 75.0);
        xpValues.put(Material.ANCIENT_DEBRIS, 75.0);
        xpValues.put(Material.NETHER_GOLD_ORE, 3.0);
        xpValues.put(Material.NETHER_QUARTZ_ORE, 5.0);
        // Stone
        List<Material> stoneMaterials = Arrays.asList(
                Material.STONE, Material.COBBLESTONE, Material.GRANITE, Material.DIORITE, Material.ANDESITE,
                Material.DEEPSLATE, Material.COBBLED_DEEPSLATE, Material.TUFF, Material.CALCITE,
                Material.SANDSTONE, Material.RED_SANDSTONE, Material.BLACKSTONE, Material.BASALT
        );
        stoneMaterials.forEach(mat -> xpValues.put(mat, 1.0));
        xpValues.put(Material.NETHERRACK, 0.5);
    }
}