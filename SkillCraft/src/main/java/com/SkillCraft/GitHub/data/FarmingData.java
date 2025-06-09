package com.SkillCraft.GitHub.data;

import org.bukkit.Material;
import java.util.HashMap;
import java.util.Map;

public class FarmingData {
    private final Map<Material, Double> xpValues = new HashMap<>();
    private final long[] levelRequirements;

    public FarmingData() {
        xpValues.put(Material.WHEAT, 15.0);
        xpValues.put(Material.CARROTS, 15.0);
        xpValues.put(Material.POTATOES, 15.0);
        xpValues.put(Material.BEETROOTS, 12.0);
        xpValues.put(Material.NETHER_WART, 20.0);

        this.levelRequirements = new long[]{
                100, 150, 220, 300, 400, 550, 700, 900, 1200, 1500,
                2000, 2700, 3500, 4500, 6000, 8000, 10000
        };
    }
    public Map<Material, Double> getXpValues() { return this.xpValues; }
    public long[] getLevelRequirements() { return this.levelRequirements; }
}