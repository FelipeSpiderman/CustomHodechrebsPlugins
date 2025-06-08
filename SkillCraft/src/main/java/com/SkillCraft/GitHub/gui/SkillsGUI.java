package com.SkillCraft.GitHub.gui;

import com.SkillCraft.GitHub.managers.SkillsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillsGUI {
    public static final String GUI_TITLE = "Skills Menu";
    private final SkillsManager skillsManager;

    // GUI Layout Configuration
    private static final int GUI_SIZE = 36;
    private static final int[] SKILL_SLOTS = {10, 12, 14, 16, 28, 30, 32};

    // Skill Display Configuration
    private static final String LEVEL_FORMAT = ChatColor.GRAY + "Level: " + ChatColor.YELLOW + "%d";
    private static final String XP_FORMAT = ChatColor.GRAY + "XP: " + ChatColor.AQUA + "%d" + ChatColor.GRAY + " / " + ChatColor.GREEN + "%d";
    private static final String PROGRESS_FORMAT = ChatColor.GRAY + "Progress: " + ChatColor.GOLD + "%.1f%%";

    public SkillsGUI(SkillsManager skillsManager) {
        this.skillsManager = skillsManager;
    }

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, GUI_SIZE, GUI_TITLE);

        // Add skill items
        addSkillItem(gui, player, "mining", Material.DIAMOND_PICKAXE, 10);
        addSkillItem(gui, player, "foraging", Material.OAK_LOG, 12);
        addSkillItem(gui, player, "farming", Material.WHEAT, 14);
        addSkillItem(gui, player, "combat", Material.DIAMOND_SWORD, 16);
        addSkillItem(gui, player, "fishing", Material.FISHING_ROD, 28);
        addSkillItem(gui, player, "brewing", Material.BREWING_STAND, 30);
        addSkillItem(gui, player, "enchanting", Material.ENCHANTING_TABLE, 32);

        // Add decorative items
        addDecorations(gui);

        player.openInventory(gui);
    }

    private void addSkillItem(Inventory gui, Player player, String skillName, Material icon, int slot) {
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        // Get skill data
        int level = skillsManager.getSkillLevel(player, skillName);
        int currentXp = skillsManager.getCurrentXp(player, skillName);
        int xpToLevel = 100; // Fixed XP per level for now
        double progress = (currentXp * 100.0) / xpToLevel;

        // Set display name
        meta.setDisplayName(formatSkillName(skillName));

        // Set lore with skill info
        meta.setLore(createSkillLore(level, currentXp, xpToLevel, progress));

        item.setItemMeta(meta);
        gui.setItem(slot, item);
    }

    private String formatSkillName(String skillName) {
        ChatColor color = skillsManager.getSkillColor(skillName);
        return color + ChatColor.BOLD.toString() +
               skillName.substring(0, 1).toUpperCase() +
               skillName.substring(1);
    }

    private List<String> createSkillLore(int level, int currentXp, int xpToLevel, double progress) {
        return Arrays.asList(
            String.format(LEVEL_FORMAT, level),
            String.format(XP_FORMAT, currentXp, xpToLevel),
            String.format(PROGRESS_FORMAT, progress)
        );
    }

    private void addDecorations(Inventory gui) {
        ItemStack decoration = createDecorationItem();

        // Add glass pane border
        for (int i = 0; i < GUI_SIZE; i++) {
            if (!isSkillSlot(i) && (i < 9 || i > GUI_SIZE - 9 || i % 9 == 0 || i % 9 == 8)) {
                gui.setItem(i, decoration);
            }
        }
    }

    private boolean isSkillSlot(int slot) {
        for (int skillSlot : SKILL_SLOTS) {
            if (slot == skillSlot) return true;
        }
        return false;
    }

    private ItemStack createDecorationItem() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
            item.setItemMeta(meta);
        }
        return item;
    }
}