package com.SkillCraft.GitHub.data;

public class BrewingData {
    private final long[] levelRequirements;
    public BrewingData() {
        this.levelRequirements = new long[]{
                50, 100, 150, 250, 400, 600, 850, 1100, 1500,
                2000, 2500, 3000, 4000, 5000, 6500, 8000, 10000,
        };
    }
    public long[] getLevelRequirements() { return this.levelRequirements; }
}