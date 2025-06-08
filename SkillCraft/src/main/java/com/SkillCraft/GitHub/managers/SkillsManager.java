package com.SkillCraft.GitHub.managers;

import com.SkillCraft.GitHub.MainPlugin;
import com.SkillCraft.GitHub.model.Skill;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.*;

public class SkillsManager {

    private final MainPlugin plugin;
    private final Map<Skill, Integer> xpPerLevelMap = new EnumMap<>(Skill.class);
    private final Map<Skill, ChatColor> colorMap = new EnumMap<>(Skill.class);
    private final Map<UUID, Map<String, Integer>> playerSkills = new HashMap<>();

    // --- New Mining Skill Data ---
    private final Map<Material, Double> miningXpValues = new HashMap<>();
    private final List<Long> miningLevelCumulativeXp = new ArrayList<>();

    public SkillsManager(MainPlugin plugin) {
        this.plugin = plugin;
        loadConfigValues();
        initializeMiningData();
    }

    private void initializeMiningData() {
        // Define XP values for each block
        // Ores
        miningXpValues.put(Material.COAL_ORE, 5.0);
        miningXpValues.put(Material.DEEPSLATE_COAL_ORE, 5.0);
        miningXpValues.put(Material.IRON_ORE, 10.0);
        miningXpValues.put(Material.DEEPSLATE_IRON_ORE, 10.0);
        miningXpValues.put(Material.COPPER_ORE, 3.0);
        miningXpValues.put(Material.DEEPSLATE_COPPER_ORE, 3.0);
        miningXpValues.put(Material.GOLD_ORE, 15.0);
        miningXpValues.put(Material.DEEPSLATE_GOLD_ORE, 15.0);
        miningXpValues.put(Material.REDSTONE_ORE, 15.0);
        miningXpValues.put(Material.DEEPSLATE_REDSTONE_ORE, 15.0);
        miningXpValues.put(Material.LAPIS_ORE, 20.0);
        miningXpValues.put(Material.DEEPSLATE_LAPIS_ORE, 20.0);
        miningXpValues.put(Material.DIAMOND_ORE, 50.0);
        miningXpValues.put(Material.DEEPSLATE_DIAMOND_ORE, 50.0);
        miningXpValues.put(Material.EMERALD_ORE, 75.0);
        miningXpValues.put(Material.DEEPSLATE_EMERALD_ORE, 75.0);
        miningXpValues.put(Material.ANCIENT_DEBRIS, 75.0);
        miningXpValues.put(Material.NETHER_GOLD_ORE, 3.0);
        miningXpValues.put(Material.NETHER_QUARTZ_ORE, 5.0);

        // Stone Variants
        List<Material> stoneMaterials = Arrays.asList(
                Material.STONE, Material.COBBLESTONE, Material.GRANITE, Material.DIORITE, Material.ANDESITE,
                Material.DEEPSLATE, Material.COBBLED_DEEPSLATE, Material.TUFF, Material.CALCITE,
                Material.SANDSTONE, Material.RED_SANDSTONE, Material.BLACKSTONE, Material.BASALT
        );
        stoneMaterials.forEach(mat -> miningXpValues.put(mat, 1.0));

        // Nether Stone
        miningXpValues.put(Material.NETHERRACK, 0.5);

        // --- Define the Hypixel-like leveling curve ---
        long[] xpPerLevel = {
                10, 20, 50, 74, 100, 150, 200, 300, 500, 750,       // Levels 1-10
                1000, 1500, 2000, 3000, 5000, 7500, 10000, 12500,   // Levels 11-18
                0, 15000, 17000, 18500, 20000                      // Levels 19-23 (index 18 is 0 for placeholder)
        };

        long cumulativeXp = 0;
        miningLevelCumulativeXp.add(0L); // Level 0 requires 0 XP
        for (long xpNeeded : xpPerLevel) {
            cumulativeXp += xpNeeded;
            miningLevelCumulativeXp.add(cumulativeXp);
        }
    }

    public Skill.SkillProgress calculateMiningProgress(Player player) {
        // 1. Calculate total XP from statistics
        double totalXp = 0;
        for (Map.Entry<Material, Double> entry : miningXpValues.entrySet()) {
            totalXp += (player.getStatistic(Statistic.MINE_BLOCK, entry.getKey()) * entry.getValue());
        }
        long totalXpLong = (long) totalXp;

        // 2. Determine current level
        int level = 0;
        for (int i = 1; i < miningLevelCumulativeXp.size(); i++) {
            if (totalXpLong >= miningLevelCumulativeXp.get(i)) {
                level = i;
            } else {
                break;
            }
        }

        // 3. Calculate XP progress for the current level
        if (level >= 23) { // Max level
            return new Skill.SkillProgress(23, 0, 0);
        }

        long xpForCurrentLevel = miningLevelCumulativeXp.get(level);
        long xpForNextLevel = miningLevelCumulativeXp.get(level + 1);
        long xpInCurrentLevel = totalXpLong - xpForCurrentLevel;
        long xpNeededForNextLevel = xpForNextLevel - xpForCurrentLevel;

        return new Skill.SkillProgress(level, xpInCurrentLevel, xpNeededForNextLevel);
    }

    // --- Methods for GUI to call ---

    private void loadConfigValues() {
        // (This method remains the same, loading colors and simple xp values for other skills)
        for (Skill skill : Skill.values()) {
            String path = "skills." + skill.getConfigKey();
            xpPerLevelMap.put(skill, plugin.getConfig().getInt(path + ".xp-per-level", 100));
            try {
                colorMap.put(skill, ChatColor.valueOf(plugin.getConfig().getString(path + ".color", "WHITE").toUpperCase()));
            } catch (IllegalArgumentException e) {
                colorMap.put(skill, ChatColor.WHITE);
            }
        }
    }

    public int getSimpleLevel(Player player, Skill skill) {
        int xpPerLevel = xpPerLevelMap.getOrDefault(skill, 100);
        if (xpPerLevel <= 0) return 0;
        return player.getStatistic(skill.getStatistic()) / xpPerLevel;
    }

    public int getSimpleCurrentXP(Player player, Skill skill) {
        int xpPerLevel = xpPerLevelMap.getOrDefault(skill, 100);
        if (xpPerLevel <= 0) return 0;
        return player.getStatistic(skill.getStatistic()) % xpPerLevel;
    }

    public int getSimpleXpPerLevel(Skill skill) {
        return xpPerLevelMap.getOrDefault(skill, 100);
    }

    public ChatColor getSkillColor(Skill skill) {
        return colorMap.getOrDefault(skill, ChatColor.WHITE);
    }

    public void addXp(Player player, String skillName, int amount) {
        UUID playerId = player.getUniqueId();
        Map<String, Integer> skills = playerSkills.computeIfAbsent(playerId, k -> new HashMap<>());

        int currentXp = skills.getOrDefault(skillName, 0);
        currentXp += amount;

        // Every 100 XP is a level up
        if (currentXp >= 100) {
            player.sendMessage(ChatColor.GREEN + "Level Up!");
            currentXp = currentXp % 100;
        }

        skills.put(skillName, currentXp);
    }

    public int getLevel(Player player, String skillName) {
        Map<String, Integer> skills = playerSkills.get(player.getUniqueId());
        if (skills == null) return 0;
        return skills.getOrDefault(skillName, 0) / 100;
    }

    public int getCurrentXp(Player player, String skillName) {
        Map<String, Integer> skills = playerSkills.get(player.getUniqueId());
        if (skills == null) return 0;
        return skills.getOrDefault(skillName, 0) % 100;
    }
}