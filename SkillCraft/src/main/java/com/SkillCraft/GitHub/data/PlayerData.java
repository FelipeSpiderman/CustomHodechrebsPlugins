package com.SkillCraft.GitHub.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {
    private final UUID playerId;
    private final Map<String, SkillProgress> skillProgress;

    public PlayerData(UUID playerId) {
        this.playerId = playerId;
        this.skillProgress = new HashMap<>();
    }

    public void addXp(String skillName, int amount) {
        skillName = skillName.toLowerCase();
        SkillProgress progress = skillProgress.computeIfAbsent(skillName, k -> new SkillProgress());
        progress.addXp(amount);
    }

    public int getLevel(String skillName) {
        SkillProgress progress = skillProgress.get(skillName.toLowerCase());
        return progress != null ? progress.getLevel() : 0;
    }

    public int getCurrentXp(String skillName) {
        SkillProgress progress = skillProgress.get(skillName.toLowerCase());
        return progress != null ? progress.getCurrentXp() : 0;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    // Inner class to encapsulate skill progress data
    private static class SkillProgress {
        private int level = 0;
        private int currentXp = 0;
        private static final int XP_PER_LEVEL = 100;

        public void addXp(int amount) {
            currentXp += amount;
            while (currentXp >= XP_PER_LEVEL) {
                currentXp -= XP_PER_LEVEL;
                level++;
            }
        }

        public int getLevel() {
            return level;
        }

        public int getCurrentXp() {
            return currentXp;
        }
    }
}
