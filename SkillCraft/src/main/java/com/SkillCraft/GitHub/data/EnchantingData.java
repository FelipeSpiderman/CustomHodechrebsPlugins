package com.SkillCraft.GitHub.data;

public class EnchantingData {
    private final long[] levelRequirements;
    public EnchantingData() {
        this.levelRequirements = new long[]{
                100, 200, 350, 500, 700, 900, 1200, 1600, 2000,
                2500, 3000, 4000, 5000, 6500, 8000, 10000, 13000,
        };
    }
    public long[] getLevelRequirements() { return this.levelRequirements; }
}