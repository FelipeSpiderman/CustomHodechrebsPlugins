package com.SkillCraft.GitHub.commands;

import com.SkillCraft.GitHub.gui.SkillsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SkillsCommand implements CommandExecutor {
    private final SkillsGUI skillsGUI;

    public SkillsCommand(SkillsGUI skillsGUI) {
        this.skillsGUI = skillsGUI;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        try {
            skillsGUI.openInventory(player);
        } catch (Exception e) {
            player.sendMessage("§cAn error occurred while opening the skills menu. See console for details.");
            e.printStackTrace();
        }
        return true;
    }
}