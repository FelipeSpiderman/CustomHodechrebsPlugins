package com.SkillCraft.GitHub.managers;

import com.SkillCraft.GitHub.data.MiningData;
import com.SkillCraft.GitHub.data.ForagingData;
import com.SkillCraft.GitHub.data.SkillSettings;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class SkillsManager {
    private final JavaPlugin plugin;
    private final Map<UUID, Map<String, Integer>> playerSkills = new HashMap<>();

    // Skill data objects
    private final SkillSettings skillSettings;
    private final MiningData miningData;
    private final ForagingData foragingData;

    public SkillsManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.skillSettings = new SkillSettings();
        this.miningData = new MiningData();
        this.foragingData = new ForagingData();
    }

    public void addXp(Player player, String skillName, int amount) {
        UUID playerId = player.getUniqueId();
        Map<String, Integer> skills = playerSkills.computeIfAbsent(playerId, k -> new HashMap<>());

        int currentXp = skills.getOrDefault(skillName, 0);
        int newXp = currentXp + amount;

        // Check for level up
        int oldLevel = getLevel(currentXp);
        int newLevel = getLevel(newXp);

        if (newLevel > oldLevel) {
            SkillSettings.SkillInfo info = skillSettings.getSkillInfo(skillName);
            player.sendMessage(
                info.getColor() + "Level Up! Your " + skillName +
                " skill is now level " + newLevel + "!"
            );
        }

        skills.put(skillName, newXp);
    }

    private int getLevel(int xp) {
        return xp / SkillSettings.BASE_XP_PER_LEVEL;
    }

    // Mining specific methods
    public double getMiningXp(Material material) {
        return miningData.getXpValue(material);
    }

    // Foraging specific methods
    public double getForagingXp(Material material) {
        return foragingData.getXpValue(material);
    }

    // General skill methods
    public int getSkillLevel(Player player, String skillName) {
        Map<String, Integer> skills = playerSkills.get(player.getUniqueId());
        if (skills == null) return 0;
        return getLevel(skills.getOrDefault(skillName, 0));
    }

    public int getCurrentXp(Player player, String skillName) {
        Map<String, Integer> skills = playerSkills.get(player.getUniqueId());
        if (skills == null) return 0;
        return skills.getOrDefault(skillName, 0) % SkillSettings.BASE_XP_PER_LEVEL;
    }

    public ChatColor getSkillColor(String skillName) {
        return skillSettings.getSkillInfo(skillName).getColor();
    }

    public Material getSkillIcon(String skillName) {
        return skillSettings.getSkillInfo(skillName).getIcon();
    }

    public int getSkillGuiSlot(String skillName) {
        return skillSettings.getSkillInfo(skillName).getGuiSlot();
    }
}