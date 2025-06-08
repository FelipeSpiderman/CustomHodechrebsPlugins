package com.SkillCraft.GitHub.data;

import java.util.HashMap;
import java.util.Map;

public class PlayerData {
    private final Map<String, Integer> levels = new HashMap<>();
    private final Map<String, Integer> xp = new HashMap<>();

    public int getLevel(String skillName) {
        return levels.getOrDefault(skillName.toLowerCase(), 0);
    }

    public int getXp(String skillName) {
        return xp.getOrDefault(skillName.toLowerCase(), 0);
    }

    public void setLevel(String skillName, int level) {
        levels.put(skillName.toLowerCase(), level);
    }

    public void setXp(String skillName, int xpAmount) {
        xp.put(skillName.toLowerCase(), xpAmount);
    }
}
