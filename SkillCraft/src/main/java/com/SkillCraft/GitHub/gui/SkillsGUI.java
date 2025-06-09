package com.SkillCraft.GitHub.gui;

import com.SkillCraft.GitHub.managers.SkillsManager;
import com.SkillCraft.GitHub.model.SkillProgress;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;
import java.util.List;

public class SkillsGUI {
    public static final String GUI_TITLE = "Your Skills";
    private final SkillsManager skillsManager;
    private final List<String> skills = Arrays.asList("mining", "foraging", "farming", "combat", "brewing", "enchanting");

    public SkillsGUI(SkillsManager skillsManager) {
        this.skillsManager = skillsManager;
    }

    public void openInventory(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, GUI_TITLE);

        gui.setItem(10, createSkillItem("mining", player));
        gui.setItem(11, createSkillItem("foraging", player));
        gui.setItem(12, createSkillItem("farming", player));
        gui.setItem(13, createSkillItem("combat", player));
        gui.setItem(14, createSkillItem("brewing", player));
        gui.setItem(15, createSkillItem("enchanting", player));

        player.openInventory(gui);
    }

    private ItemStack createSkillItem(String skill, Player player) {
        SkillProgress progress = skillsManager.calculateSkillProgress(player, skill);

        Material icon = getSkillIcon(skill);
        ChatColor color = getSkillColor(skill);

        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(color + "" + ChatColor.BOLD + skill.substring(0, 1).toUpperCase() + skill.substring(1));

            List<String> lore = new java.util.ArrayList<>();
            lore.add(ChatColor.GRAY + "Level: " + ChatColor.WHITE + progress.level());

            if (progress.xpToNextLevel() > 0) {
                lore.add(ChatColor.GRAY + "XP: " + ChatColor.WHITE + progress.currentXp() + " / " + progress.xpToNextLevel());
            } else {
                lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "MAX LEVEL");
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private Material getSkillIcon(String skill) {
        return switch (skill.toLowerCase()) {
            case "mining" -> Material.DIAMOND_PICKAXE;
            case "foraging" -> Material.OAK_LOG;
            case "farming" -> Material.WHEAT;
            case "combat" -> Material.DIAMOND_SWORD;
            case "brewing" -> Material.BREWING_STAND;
            case "enchanting" -> Material.ENCHANTING_TABLE;
            default -> Material.BARRIER;
        };
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
}