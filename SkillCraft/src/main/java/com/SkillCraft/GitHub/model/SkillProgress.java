package com.SkillCraft.GitHub.model;

/**
 * A record to hold the calculated progress for a skill.
 * @param level The current level.
 * @param currentXp The amount of XP accumulated within the current level.
 * @param xpToNextLevel The total XP required to complete the current level.
 */
public record SkillProgress(int level, long currentXp, long xpToNextLevel) {
}