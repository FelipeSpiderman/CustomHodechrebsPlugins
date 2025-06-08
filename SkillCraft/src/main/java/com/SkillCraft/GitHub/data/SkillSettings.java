package com.SkillCraft.GitHub.data;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import java.util.HashMap;
import java.util.Map;

public class SkillSettings {
    private final Map<String, SkillInfo> skillInfo = new HashMap<>();
    public static final int BASE_XP_PER_LEVEL = 100;

    public SkillSettings() {
        initializeSkillInfo();
    }

    private void initializeSkillInfo() {
        // Mining skill
        skillInfo.put("mining", new SkillInfo(
            ChatColor.GRAY,
            Material.DIAMOND_PICKAXE,
            10,  // GUI slot
            "Breaking ores and stone"
        ));

        // Foraging skill
        skillInfo.put("foraging", new SkillInfo(
            ChatColor.DARK_GREEN,
            Material.OAK_LOG,
            12,
            "Cutting trees and gathering wood"
        ));

        // Farming skill
        skillInfo.put("farming", new SkillInfo(
            ChatColor.GREEN,
            Material.WHEAT,
            14,
            "Growing and harvesting crops"
        ));

        // Combat skill
        skillInfo.put("combat", new SkillInfo(
            ChatColor.RED,
            Material.DIAMOND_SWORD,
            16,
            "Fighting mobs and players"
        ));

        // Fishing skill
        skillInfo.put("fishing", new SkillInfo(
            ChatColor.AQUA,
            Material.FISHING_ROD,
            28,
            "Catching fish and treasure"
        ));

        // Brewing skill
        skillInfo.put("brewing", new SkillInfo(
            ChatColor.LIGHT_PURPLE,
            Material.BREWING_STAND,
            30,
            "Creating potions"
        ));

        // Enchanting skill
        skillInfo.put("enchanting", new SkillInfo(
            ChatColor.DARK_PURPLE,
            Material.ENCHANTING_TABLE,
            32,
            "Enchanting items"
        ));
    }

    public SkillInfo getSkillInfo(String skillName) {
        return skillInfo.getOrDefault(skillName.toLowerCase(),
            new SkillInfo(ChatColor.WHITE, Material.BARRIER, 0, "Unknown skill"));
    }

    public static class SkillInfo {
        private final ChatColor color;
        private final Material icon;
        private final int guiSlot;
        private final String description;

        public SkillInfo(ChatColor color, Material icon, int guiSlot, String description) {
            this.color = color;
            this.icon = icon;
            this.guiSlot = guiSlot;
            this.description = description;
        }

        public ChatColor getColor() { return color; }
        public Material getIcon() { return icon; }
        public int getGuiSlot() { return guiSlot; }
        public String getDescription() { return description; }
    }
}
