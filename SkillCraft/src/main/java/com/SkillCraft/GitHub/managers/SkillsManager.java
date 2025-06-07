package com.SkillCraft.GitHub.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.SkillCraft.GitHub.data.PlayerData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillsManager {
    private final JavaPlugin plugin;
    private final Map<UUID, PlayerData> playerData = new HashMap<>();
    private final Map<String, Integer> xpPerLevel = new HashMap<>();
    private final Map<String, String> skillColors = new HashMap<>();

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
        return playerData.computeIfAbsent(player.getUniqueId(), k -> new PlayerData());
    }

    public void addXp(Player player, String skill, int xp) {
        PlayerData data = getPlayerData(player);
        int currentXp = data.getXp(skill) + xp;
        int level = data.getLevel(skill);
        int xpNeeded = xpPerLevel.get(skill);

        while (currentXp >= xpNeeded) {
            currentXp -= xpNeeded;
            level++;
            player.sendMessage("§a" + "Level Up! §" + skillColors.get(skill) + skill.toUpperCase() + " §e" + level);
        }

        data.setXp(skill, currentXp);
        data.setLevel(skill, level);

        player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                net.md_5.bungee.api.chat.TextComponent.fromLegacyText("§" + skillColors.get(skill) + "+" + xp + " " +
                        skill.toUpperCase() + " XP §7(" + currentXp + "/" + xpNeeded + ")"));
    }

    public String getSkillColor(String skill) {
        return skillColors.getOrDefault(skill, "WHITE");
    }
}