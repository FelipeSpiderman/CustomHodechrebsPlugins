package com.SkillCraft.GitHub.gui;

import com.SkillCraft.GitHub.managers.SkillsManager;
import com.SkillCraft.GitHub.model.Skill;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SkillsGUI {
    public static final String GUI_TITLE = "Your Skills";
    private final SkillsManager skillsManager;

    public SkillsGUI(SkillsManager skillsManager) {
        this.skillsManager = skillsManager;
    }

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 36, GUI_TITLE);

        for (Skill skill : Skill.values()) {
            ItemStack item = new ItemStack(skill.getIcon());
            ItemMeta meta = item.getItemMeta();
            if (meta == null) continue;

            meta.setDisplayName(skillsManager.getSkillColor(skill) + "" + ChatColor.BOLD + skill.getDisplayName());
            List<String> lore = new ArrayList<>();

            if (skill == Skill.MINING) {
                // Use the new, complex calculation for Mining
                Skill.SkillProgress progress = skillsManager.calculateMiningProgress(player);
                lore.add(ChatColor.GRAY + "Level: " + ChatColor.WHITE + progress.level());
                if (progress.xpToNextLevel() > 0) {
                    lore.add(ChatColor.GRAY + "XP: " + ChatColor.WHITE + progress.currentXp() + " / " + progress.xpToNextLevel());
                } else {
                    lore.add(ChatColor.GOLD + "MAX LEVEL");
                }
            } else {
                // Use the old, simple calculation for all other skills
                int level = skillsManager.getSimpleLevel(player, skill);
                int currentXp = skillsManager.getSimpleCurrentXP(player, skill);
                int xpToLevel = skillsManager.getSimpleXpPerLevel(skill);

                lore.add(ChatColor.GRAY + "Level: " + ChatColor.WHITE + level);
                lore.add(ChatColor.GRAY + "XP: " + ChatColor.WHITE + currentXp + " / " + xpToLevel);
            }

            meta.setLore(lore);
            item.setItemMeta(meta);
            gui.setItem(skill.getGuiSlot(), item);
        }
        player.openInventory(gui);
    }
}