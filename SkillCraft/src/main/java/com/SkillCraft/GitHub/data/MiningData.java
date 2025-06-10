package com.SkillCraft.GitHub.data;

import org.bukkit.Material;
import java.util.*;

public class MiningData {
    private final Map<Material, Double> xpValues = new HashMap<>();
    private final long[] levelRequirements;

    public MiningData() {
        initializeXpValues();
        this.levelRequirements = new long[]{
                10, 20, 50, 74, 100, 150, 200, 300, 500, 750,
                1000, 1500, 2000, 3000, 5000, 7500, 10000, 12500,
                15000, 17000, 18500, 20000, 25000,
                27500, 30000, 32500, 35000, 37500, 40000, 42500,
                45000, 47500, 50000,
        };
    }

    public Map<Material, Double> getXpValues() {
        return this.xpValues;
    }

    public long[] getLevelRequirements() {
        return this.levelRequirements;
    }

    private void initializeXpValues() {
        // --- Ores (High Value) ---
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
        xpValues.put(Material.OBSIDIAN, 30.0);

        // --- THE NEW, COMPREHENSIVE LIST OF NATURAL STONE BLOCKS ---
        List<Material> stoneMaterials = new ArrayList<>();

        // Overworld Natural Stone
        stoneMaterials.addAll(Arrays.asList(
                Material.STONE, Material.COBBLESTONE, Material.MOSSY_COBBLESTONE,
                Material.GRANITE, Material.DIORITE, Material.ANDESITE,
                Material.DEEPSLATE, Material.COBBLED_DEEPSLATE, Material.TUFF, Material.CALCITE,
                Material.SANDSTONE, Material.RED_SANDSTONE, Material.AMETHYST_BLOCK
        ));

        // Nether Natural Stone
        stoneMaterials.addAll(Arrays.asList(
                Material.NETHERRACK, Material.BLACKSTONE, Material.BASALT, Material.MAGMA_BLOCK
        ));

        // End Natural Stone
        stoneMaterials.addAll(Arrays.asList(
                Material.END_STONE
        ));

        // Infested Blocks (from strongholds and mountains)
        stoneMaterials.addAll(Arrays.asList(
                Material.INFESTED_STONE, Material.INFESTED_COBBLESTONE, Material.INFESTED_STONE_BRICKS,
                Material.INFESTED_MOSSY_STONE_BRICKS, Material.INFESTED_CRACKED_STONE_BRICKS,
                Material.INFESTED_CHISELED_STONE_BRICKS, Material.INFESTED_DEEPSLATE
        ));
        for(Material mat : stoneMaterials) {
            if (mat == Material.NETHERRACK) {
                xpValues.put(mat, 0.5);
            } else {
                xpValues.put(mat, 1.0);
            }
        }
    }
}