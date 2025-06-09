package com.SkillCraft.GitHub.managers;

import org.bukkit.Statistic;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SkillStatManager {
    public int calculateMiningLevel(Player player) {
        int mineCount = 0;
        for (Material material : Material.values()) {
            if (material.name().endsWith("_ORE") || material.name().contains("STONE")) {
                mineCount += player.getStatistic(Statistic.MINE_BLOCK, material);
            }
        }
        return convertToLevel(mineCount, 100);
    }

    public int calculateForagingLevel(Player player) {
        int woodCount = 0;
        for (Material material : Material.values()) {
            if (material.name().endsWith("_LOG") || material.name().endsWith("_WOOD")) {
                woodCount += player.getStatistic(Statistic.MINE_BLOCK, material);
            }
        }
        return convertToLevel(woodCount, 50);
    }

    public int calculateFarmingLevel(Player player) {
        int farmCount = 0;
        for (Material material : Material.values()) {
            if (material.name().endsWith("_SEEDS") ||
                    material.name().contains("CROP") ||
                    material.name().contains("WHEAT") ||
                    material.name().contains("CARROT") ||
                    material.name().contains("POTATO")) {
                farmCount += player.getStatistic(Statistic.USE_ITEM, material);
                farmCount += player.getStatistic(Statistic.MINE_BLOCK, material);
            }
        }
        return convertToLevel(farmCount, 75);
    }

    public int calculateCombatLevel(Player player) {
        int killCount = 0;
        for (EntityType entity : EntityType.values()) {
            if (entity.isAlive()) {
                killCount += player.getStatistic(Statistic.KILL_ENTITY, entity);
            }
        }
        return convertToLevel(killCount, 25);
    }

    public int calculateBrewingLevel(Player player) {
        return convertToLevel(player.getStatistic(Statistic.CRAFT_ITEM), 20);
    }

    public int calculateEnchantingLevel(Player player) {
        return convertToLevel(player.getStatistic(Statistic.ITEM_ENCHANTED), 15);
    }

    private int convertToLevel(int value, int baseXP) {
        return (int) Math.floor(Math.sqrt(value / baseXP));
    }

    public int getSkillXP(Player player, String skill) {
        switch (skill.toLowerCase()) {
            case "mining" -> {
                int mineCount = 0;
                for (Material material : Material.values()) {
                    if (material.name().endsWith("_ORE") || material.name().contains("STONE")) {
                        mineCount += player.getStatistic(Statistic.MINE_BLOCK, material);
                    }
                }
                return mineCount;
            }
            case "foraging" -> {
                int woodCount = 0;
                for (Material material : Material.values()) {
                    if (material.name().endsWith("_LOG") || material.name().endsWith("_WOOD")) {
                        woodCount += player.getStatistic(Statistic.MINE_BLOCK, material);
                    }
                }
                return woodCount;
            }
            case "farming" -> {
                int farmCount = 0;
                for (Material material : Material.values()) {
                    if (material.name().endsWith("_SEEDS") ||
                            material.name().contains("CROP") ||
                            material.name().contains("WHEAT") ||
                            material.name().contains("CARROT") ||
                            material.name().contains("POTATO")) {
                        farmCount += player.getStatistic(Statistic.USE_ITEM, material);
                        farmCount += player.getStatistic(Statistic.MINE_BLOCK, material);
                    }
                }
                return farmCount;
            }
            case "combat" -> {
                int killCount = 0;
                for (EntityType entity : EntityType.values()) {
                    if (entity.isAlive()) {
                        killCount += player.getStatistic(Statistic.KILL_ENTITY, entity);
                    }
                }
                return killCount;
            }
            case "brewing" -> {
                return player.getStatistic(Statistic.CRAFT_ITEM);
            }
            case "enchanting" -> {
                return player.getStatistic(Statistic.ITEM_ENCHANTED);
            }
            default -> {
                return 0;
            }
        }
    }
}