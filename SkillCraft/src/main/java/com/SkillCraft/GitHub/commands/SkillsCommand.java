package com.SkillCraft.GitHub.commands;

import com.SkillCraft.GitHub.managers.SkillsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.SkillCraft.GitHub.data.PlayerData;

public class SkillsCommand implements CommandExecutor { private final SkillsManager skillsManager;

    public SkillsCommand(SkillsManager skillsManager) {
        this.skillsManager = skillsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        PlayerData data = skillsManager.getPlayerData(player);
        player.sendMessage("§e--- Skill Levels ---");
        player.sendMessage("§" + skillsManager.getSkillColor("mining") + "Mining: §e" + data.getLevel("mining") + " (§7" + data.getXp("mining") + " XP)");
        player.sendMessage("§" + skillsManager.getSkillColor("foraging") + "Foraging: §e" + data.getLevel("foraging") + " (§7" + data.getXp("foraging") + " XP)");
        return true;
    }

}