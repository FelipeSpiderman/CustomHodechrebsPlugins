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
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        skillsGUI.open(player);
        return true;
    }
}