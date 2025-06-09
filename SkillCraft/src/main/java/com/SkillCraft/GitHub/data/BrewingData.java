package com.SkillCraft.GitHub.data;

public class BrewingData {
    private final long[] levelRequirements;
    public BrewingData() {
        this.levelRequirements = new long[]{ 50, 100, 150, 250, 400, 600, 850, 1100, 1500 };
    }
    public long[] getLevelRequirements() { return this.levelRequirements; }
}