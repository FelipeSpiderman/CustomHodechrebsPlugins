package com.SkillCraft.GitHub.data;

import org.bukkit.Material;
import java.util.*;

public class ForagingData {
    private final Map<Material, Double> xpValues = new HashMap<>();
    private final long[] levelRequirements;

    public ForagingData() {
        initializeXpValues();
        this.levelRequirements = new long[]{
                50, 100, 150, 200, 300, 450, 600, 800, 1000, 1250, 1500,
                2000, 2500, 3000, 4000, 5000, 6000, 7500, 9000, 11000, 13000, 15000, 17500
                , 20000, 22500, 25000, 27500, 30000, 35000, 40000, 45000, 50000
        };
    }

    public Map<Material, Double> getXpValues() {
        return this.xpValues;
    }

    public long[] getLevelRequirements() {
        return this.levelRequirements;
    }

    private void initializeXpValues() {
        List<Material> logs = Arrays.asList(
                Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG, Material.JUNGLE_LOG,
                Material.ACACIA_LOG, Material.DARK_OAK_LOG, Material.MANGROVE_LOG, Material.CHERRY_LOG,
                Material.CRIMSON_STEM, Material.WARPED_STEM
        );
        logs.forEach(log -> xpValues.put(log, 5.0));
    }
}