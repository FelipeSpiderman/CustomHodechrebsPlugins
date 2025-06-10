package com.SkillCraft.GitHub.data;

import org.bukkit.entity.EntityType;
import java.util.HashMap;
import java.util.Map;

public class CombatData {
    private final Map<EntityType, Double> xpValues = new HashMap<>();
    private final long[] levelRequirements;

    public CombatData() {
        initializeXpValues();

        this.levelRequirements = new long[]{
                50, 75, 100, 150, 200, 275, 350, 450, 600, 800,
                1000, 1300, 1700, 2200, 2800, 3500, 4500, 6000,
                8000, 10000, 12500, 15000, 18500, 20000, 25000
        };
    }

    private void initializeXpValues() {
        xpValues.put(EntityType.ZOMBIE, 10.0);
        xpValues.put(EntityType.ZOMBIE_VILLAGER, 12.0);
        xpValues.put(EntityType.HUSK, 12.0);
        xpValues.put(EntityType.DROWNED, 10.0);
        xpValues.put(EntityType.SKELETON, 10.0);
        xpValues.put(EntityType.STRAY, 12.0);
        xpValues.put(EntityType.SPIDER, 10.0);
        xpValues.put(EntityType.CAVE_SPIDER, 15.0);
        xpValues.put(EntityType.CREEPER, 15.0);
        xpValues.put(EntityType.SLIME, 5.0);
        xpValues.put(EntityType.SILVERFISH, 3.0);
        xpValues.put(EntityType.PHANTOM, 20.0);

        xpValues.put(EntityType.ENDERMAN, 25.0);
        xpValues.put(EntityType.WITCH, 30.0);
        xpValues.put(EntityType.PILLAGER, 15.0);
        xpValues.put(EntityType.VINDICATOR, 20.0);
        xpValues.put(EntityType.EVOKER, 30.0);
        xpValues.put(EntityType.VEX, 5.0);
        xpValues.put(EntityType.RAVAGER, 50.0);

        xpValues.put(EntityType.GUARDIAN, 30.0);
        xpValues.put(EntityType.ELDER_GUARDIAN, 100.0);
        xpValues.put(EntityType.SHULKER, 20.0);
        xpValues.put(EntityType.ENDERMITE, 3.0);

        xpValues.put(EntityType.PIGLIN, 10.0);
        xpValues.put(EntityType.ZOMBIFIED_PIGLIN, 8.0);
        xpValues.put(EntityType.PIGLIN_BRUTE, 30.0);
        xpValues.put(EntityType.HOGLIN, 25.0);
        xpValues.put(EntityType.ZOGLIN, 25.0);
        xpValues.put(EntityType.GHAST, 40.0);
        xpValues.put(EntityType.MAGMA_CUBE, 5.0);
        xpValues.put(EntityType.BLAZE, 20.0);
        xpValues.put(EntityType.WITHER_SKELETON, 25.0);

        xpValues.put(EntityType.WARDEN, 4000.0);

        xpValues.put(EntityType.WITHER, 2000.0);
        xpValues.put(EntityType.ENDER_DRAGON, 3000.0);

        xpValues.put(EntityType.WOLF, 10.0);
        xpValues.put(EntityType.IRON_GOLEM, 50.0);
        xpValues.put(EntityType.BEE, 1.0);
        xpValues.put(EntityType.PANDA, 1.0);
        xpValues.put(EntityType.LLAMA, 2.0);
        xpValues.put(EntityType.TRADER_LLAMA, 2.0);
        xpValues.put(EntityType.DOLPHIN, 1.0);
        xpValues.put(EntityType.GOAT, 3.0);
        xpValues.put(EntityType.OCELOT, 1.0);
        xpValues.put(EntityType.CAT, 1.0);
        xpValues.put(EntityType.FOX, 1.0);
        xpValues.put(EntityType.RABBIT, 1.0);
        xpValues.put(EntityType.CHICKEN, 2.0);
        xpValues.put(EntityType.COW, 2.0);
        xpValues.put(EntityType.SHEEP, 2.0);
        xpValues.put(EntityType.PIG, 2.0);
        xpValues.put(EntityType.HORSE, 5.0);
        xpValues.put(EntityType.MULE, 5.0);
        xpValues.put(EntityType.DONKEY, 5.0);
        xpValues.put(EntityType.BAT, 1.0);
        xpValues.put(EntityType.PARROT, 1.0);
        xpValues.put(EntityType.TURTLE, 3.0);
        xpValues.put(EntityType.BOGGED, 15.0);
        xpValues.put(EntityType.BREEZE, 75.0);
    }

    public Map<EntityType, Double> getXpValues() {
        return this.xpValues;
    }

    public long[] getLevelRequirements() {
        return this.levelRequirements;
    }
}