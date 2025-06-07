package com.SkillCraft.GitHub.data;

import java.util.HashMap;
import java.util.Map;

public class PlayerData {
    private final Map<String, Integer> xp = new HashMap<>();
    private final Map<String, Integer> levels = new HashMap<>();

    public int getXp(String skill) {
        return xp.getOrDefault(skill, 0);
    }

    public void setXp(String skill, int amount) {
        xp.put(skill, amount);
    }

    public int getLevel(String skill) {
        return levels.getOrDefault(skill, 0);
    }

    public void setLevel(String skill, int level) {
        levels.put(skill, level);
    }
}