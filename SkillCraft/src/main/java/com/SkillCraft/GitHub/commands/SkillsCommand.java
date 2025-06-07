package com.SkillCraft.GitHub.commands;

import com.SkillCraft.GitHub.managers.SkillsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.SkillCraft.GitHub.data.PlayerData;

public class SkillsCommand implements CommandExecutor {
    private final SkillsManager skillsManager;

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

        player.sendMessage("§7Mining");
        player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                net.md_5.bungee.api.chat.TextComponent.fromLegacyText(
                        "§e" + data.getLevel("mining") + " ➜ " +
                                skillsManager.buildProgressBar(data.getXp("mining"), skillsManager.getXpNeeded("mining"), 16)
                )
        );

        player.sendMessage("§7Foraging");
        player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                net.md_5.bungee.api.chat.TextComponent.fromLegacyText(
                        "§e" + data.getLevel("foraging") + " ➜ " +
                                skillsManager.buildProgressBar(data.getXp("foraging"), skillsManager.getXpNeeded("foraging"), 16)
                )
        );

        return true;
    }
}