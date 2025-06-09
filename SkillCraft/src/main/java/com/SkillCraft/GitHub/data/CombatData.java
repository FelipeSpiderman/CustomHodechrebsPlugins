package com.SkillCraft.GitHub.data;

import org.bukkit.entity.EntityType;
import java.util.HashMap;
import java.util.Map;

public class CombatData {
    private final Map<EntityType, Double> xpValues = new HashMap<>();
    private final long[] levelRequirements;

    public CombatData() {
        xpValues.put(EntityType.ZOMBIE, 10.0);
        xpValues.put(EntityType.SKELETON, 10.0);
        xpValues.put(EntityType.SPIDER, 10.0);
        xpValues.put(EntityType.CREEPER, 12.0);
        xpValues.put(EntityType.ENDERMAN, 25.0);
        xpValues.put(EntityType.BLAZE, 25.0);
        xpValues.put(EntityType.PIGLIN_BRUTE, 40.0);
        xpValues.put(EntityType.WITHER_SKELETON, 40.0);
        xpValues.put(EntityType.WITHER, 1000.0);
        xpValues.put(EntityType.ENDER_DRAGON, 2000.0);

        this.levelRequirements = new long[]{
                50, 75, 100, 150, 200, 275, 350, 450, 600, 800,
                1000, 1300, 1700, 2200, 2800, 3500, 4500, 6000
        };
    }
    public Map<EntityType, Double> getXpValues() { return this.xpValues; }
    public long[] getLevelRequirements() { return this.levelRequirements; }
}