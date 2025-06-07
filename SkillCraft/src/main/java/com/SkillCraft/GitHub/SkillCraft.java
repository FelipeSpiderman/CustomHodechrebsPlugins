package com.SkillCraft.GitHub;

import com.SkillCraft.GitHub.commands.SkillsCommand;
import com.SkillCraft.GitHub.listeners.SkillsListener;
import com.SkillCraft.GitHub.managers.SkillsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SkillCraft extends JavaPlugin {
    private final SkillsManager skillsManager = new SkillsManager(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Register events
        getServer().getPluginManager().registerEvents(new SkillsListener(skillsManager), this);

        // Register command with null check
        if (getCommand("skills") != null) {
            getCommand("skills").setExecutor(new SkillsCommand(skillsManager));
        } else {
            getLogger().warning("Could not register 'skills' command - is it defined in plugin.yml?");
        }

        getLogger().info("SkillCraft enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SkillCraft disabled!");
    }
}