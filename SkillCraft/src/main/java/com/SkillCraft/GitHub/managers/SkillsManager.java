package com.SkillCraft.GitHub.managers;

import com.SkillCraft.GitHub.MainPlugin;
import com.SkillCraft.GitHub.data.*;
import com.SkillCraft.GitHub.model.SkillProgress;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillsManager {
    private final MainPlugin plugin;
    private final MiningData miningData = new MiningData();
    private final ForagingData foragingData = new ForagingData();
    private final FarmingData farmingData = new FarmingData();
    private final CombatData combatData = new CombatData();
    private final BrewingData brewingData = new BrewingData();
    private final EnchantingData enchantingData = new EnchantingData();

    private final Map<UUID, BossBar> activeBossBars = new HashMap<>();
    private final Map<UUID, BukkitTask> hideBarTasks = new HashMap<>();

    public SkillsManager(MainPlugin plugin) {
        this.plugin = plugin;
    }

    // --- HELPER METHODS FOR THE EVENT LISTENER ---

    public String getSkillForMaterial(Material material) {
        if (miningData.getXpValues().containsKey(material)) return "mining";
        if (foragingData.getXpValues().containsKey(material)) return "foraging";
        if (farmingData.getXpValues().containsKey(material)) return "farming";
        return "";
    }

    public double getXpForMaterial(Material material) {
        // This looks in all three data classes to find the correct XP value.
        return miningData.getXpValues().getOrDefault(material,
                foragingData.getXpValues().getOrDefault(material,
                        farmingData.getXpValues().getOrDefault(material, 0.0)));
    }

    public double getXpForEntity(EntityType entityType) {
        return combatData.getXpValues().getOrDefault(entityType, 0.0);
    }

    // --- CORE LOGIC ---

    public void showXpGainNotification(Player player, String skillName, double xpGained) {
        SkillProgress progress = calculateSkillProgress(player, skillName);
        double barProgress;
        String titleText;
        ChatColor skillColor = getSkillColor(skillName);

        if (progress.xpToNextLevel() <= 0) {
            barProgress = 1.0;
            titleText = skillColor + "" + ChatColor.BOLD + skillName.toUpperCase() + " " + ChatColor.GOLD + "(MAX LEVEL)";
        } else {
            barProgress = Math.min(1.0, (progress.currentXp() + xpGained) / progress.xpToNextLevel());
            titleText = skillColor + "" + ChatColor.BOLD + skillName.toUpperCase() + "  " + ChatColor.WHITE + "+ " + String.format("%.1f", xpGained) + " XP";
        }

        if (hideBarTasks.containsKey(player.getUniqueId())) hideBarTasks.get(player.getUniqueId()).cancel();

        BossBar bossBar = activeBossBars.computeIfAbsent(player.getUniqueId(), uuid -> {
            BossBar newBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
            newBar.addPlayer(player);
            return newBar;
        });

        bossBar.setTitle(titleText);
        bossBar.setColor(getBarColor(skillColor));
        bossBar.setProgress(barProgress);
        bossBar.setVisible(true);

        BukkitTask hideTask = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            bossBar.setVisible(false);
            activeBossBars.remove(player.getUniqueId());
        }, 60L);

        hideBarTasks.put(player.getUniqueId(), hideTask);
    }

    public SkillProgress calculateSkillProgress(Player player, String skillName) {
        return switch (skillName.toLowerCase()) {
            case "mining" -> calculateBlockProgress(player, miningData.getXpValues(), miningData.getLevelRequirements());
            case "foraging" -> calculateBlockProgress(player, foragingData.getXpValues(), foragingData.getLevelRequirements());
            case "farming" -> calculateBlockProgress(player, farmingData.getXpValues(), farmingData.getLevelRequirements());
            case "combat" -> calculateCombatProgress(player, combatData.getXpValues(), combatData.getLevelRequirements());
            case "brewing" -> calculateMaterialStatisticProgress(player, Statistic.USE_ITEM, Material.BREWING_STAND, brewingData.getLevelRequirements(), 5.0);
            case "enchanting" -> calculateSimpleStatisticProgress(player, Statistic.ITEM_ENCHANTED, enchantingData.getLevelRequirements(), 45.0);
            default -> new SkillProgress(0, 0, 100);
        };
    }

    private SkillProgress calculateBlockProgress(Player player, Map<Material, Double> xpMap, long[] levelRequirements) {
        double totalXpDouble = 0;
        for (Map.Entry<Material, Double> entry : xpMap.entrySet()) {
            totalXpDouble += (player.getStatistic(Statistic.MINE_BLOCK, entry.getKey()) * entry.getValue());
        }
        return processXp(totalXpDouble, levelRequirements);
    }

    private SkillProgress calculateCombatProgress(Player player, Map<EntityType, Double> xpMap, long[] levelRequirements) {
        double totalXpDouble = 0;
        for (Map.Entry<EntityType, Double> entry : xpMap.entrySet()) {
            totalXpDouble += (player.getStatistic(Statistic.KILL_ENTITY, entry.getKey()) * entry.getValue());
        }
        return processXp(totalXpDouble, levelRequirements);
    }

    private SkillProgress calculateSimpleStatisticProgress(Player player, Statistic stat, long[] levelRequirements, double multiplier) {
        double totalXpDouble = player.getStatistic(stat) * multiplier;
        return processXp(totalXpDouble, levelRequirements);
    }

    private SkillProgress calculateMaterialStatisticProgress(Player player, Statistic stat, Material material, long[] levelRequirements, double multiplier) {
        double totalXpDouble = player.getStatistic(stat, material) * multiplier;
        return processXp(totalXpDouble, levelRequirements);
    }

    private SkillProgress processXp(double totalXpDouble, long[] levelRequirements) {
        long totalXp = (long) totalXpDouble;
        int currentLevel = 0;
        long xpForNextLevel = levelRequirements[0];

        for (int i = 0; i < levelRequirements.length; i++) {
            xpForNextLevel = levelRequirements[i];
            if (totalXp >= xpForNextLevel) {
                totalXp -= xpForNextLevel;
                currentLevel++;
            } else {
                break;
            }
        }

        if (currentLevel >= levelRequirements.length) {
            return new SkillProgress(currentLevel, 0, 0);
        }
        return new SkillProgress(currentLevel, totalXp, xpForNextLevel);
    }

    public void cleanup() {
        for (BossBar bar : activeBossBars.values()) bar.removeAll();
        activeBossBars.clear();
        for (BukkitTask task : hideBarTasks.values()) task.cancel();
        hideBarTasks.clear();
    }

    private ChatColor getSkillColor(String skill) {
        return switch (skill.toLowerCase()) {
            case "mining" -> ChatColor.AQUA;
            case "foraging" -> ChatColor.DARK_GREEN;
            case "farming" -> ChatColor.GOLD;
            case "combat" -> ChatColor.RED;
            case "brewing" -> ChatColor.LIGHT_PURPLE;
            case "enchanting" -> ChatColor.DARK_PURPLE;
            default -> ChatColor.GRAY;
        };
    }

    private BarColor getBarColor(ChatColor chatColor) {
        return switch (chatColor) {
            case AQUA, DARK_AQUA, BLUE -> BarColor.BLUE;
            case DARK_GREEN, GREEN -> BarColor.GREEN;
            case GOLD, YELLOW -> BarColor.YELLOW;
            case RED, DARK_RED -> BarColor.RED;
            case LIGHT_PURPLE, DARK_PURPLE -> BarColor.PURPLE;
            default -> BarColor.WHITE;
        };
    }
}