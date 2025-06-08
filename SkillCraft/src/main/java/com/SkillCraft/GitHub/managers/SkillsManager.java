package com.SkillCraft.GitHub.managers;

import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import com.SkillCraft.GitHub.data.PlayerData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillsManager {
    private final JavaPlugin plugin;
    private final Map<String, Integer> xpPerLevel = new HashMap<>();
    private final Map<String, String> skillColors = new HashMap<>();
    private final Map<UUID, PlayerData> playerData = new HashMap<>();

    public SkillsManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        FileConfiguration config = plugin.getConfig();
        for (String skill : config.getConfigurationSection("skills").getKeys(false)) {
            xpPerLevel.put(skill, config.getInt("skills." + skill + ".xp-per-level"));
            skillColors.put(skill, config.getString("skills." + skill + ".color"));
        }
    }

    public PlayerData getPlayerData(Player player) {
        if (player == null) return null;
        return playerData.computeIfAbsent(player.getUniqueId(), uuid -> new PlayerData());
    }

    private int calculateMiningXp(Player player) {
        int miningXp = 0;
        for (Material material : Material.values()) {
            if (material.name().contains("ORE") || material == Material.STONE) {
                int blocks = player.getStatistic(Statistic.MINE_BLOCK, material);
                miningXp += material.name().contains("ORE") ? blocks * 5 : blocks;
            }
        }
        return miningXp;
    }

    private int calculateForagingXp(Player player) {
        int foragingXp = 0;
        for (Material material : Material.values()) {
            if (material.name().contains("LOG")) {
                foragingXp += player.getStatistic(Statistic.MINE_BLOCK, material) * 2;
            }
        }
        return foragingXp;
    }

    public void addXp(Player player, String skill, int xp) {
        if (player == null || skill == null) return;

        PlayerData data = getPlayerData(player);
        if (data == null) return;

        int currentXp = data.getXp(skill);
        data.setXp(skill, currentXp + xp);

        updateLevel(player, skill, currentXp + xp);

        String progressBar = buildProgressBar(currentXp % 1000, 1000, 20);
        String color = "§" + getPlugin().getConfig().getString("skills." + skill + ".color", "WHITE");
        player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                net.md_5.bungee.api.chat.TextComponent.fromLegacyText(
                        color + skill.substring(0, 1).toUpperCase() + skill.substring(1) +
                                " §f" + progressBar + " §e" + currentXp % 1000 + "§7/§e1000 XP"
                ));
    }

    // Add method to get XP needed for a skill
    public int getXpNeeded(String skill) {
        return xpPerLevel.getOrDefault(skill, 1000);
    }

    public String buildProgressBar(int currentXp, int xpNeeded, int barLength) {
        if (xpNeeded <= 0) return "";

        final String ENCHANT_CHARS = "ᔑʖᓵ↸ᒷ⎓⊣⍑╎⋮ꖌꖎ𝙹ᒲリ𝙹!¡ᑑ∷ᓭℸ ̣ ⚍⍊∴";
        double percentage = Math.min(1.0, currentXp / (double) xpNeeded);
        int fullBlocks = (int) (percentage * barLength);

        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < barLength; i++) {
            char currentChar = ENCHANT_CHARS.charAt(i % ENCHANT_CHARS.length());
            bar.append(i < fullBlocks ? "§a" : "§7").append(currentChar);
        }

        return bar.toString();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    private void updateLevel(Player player, String skill, int totalXp) {
        if (player == null || skill == null) return;

        PlayerData data = getPlayerData(player);
        if (data == null) return;

        int level = 0;
        int xpNeeded = xpPerLevel.get(skill);
        int remainingXp = totalXp;
        while (remainingXp >= xpNeeded) {
            remainingXp -= xpNeeded;
            level++;
        }
        data.setLevel(skill, level);
        data.setXp(skill, remainingXp);
    }

    public String getSkillColor(String skill) {
        return skillColors.getOrDefault(skill, "WHITE");
    }
}