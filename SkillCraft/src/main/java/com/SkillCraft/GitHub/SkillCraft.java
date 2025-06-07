package com.SkillCraft.GitHub;

import com.SkillCraft.GitHub.managers.SkillsManager;
import com.SkillCraft.GitHub.listeners.SkillsListener;
import com.SkillCraft.GitHub.commands.SkillsCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class SkillCraft extends JavaPlugin {
    private SkillsManager skillsManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        skillsManager = new SkillsManager(this);
        getServer().getPluginManager().registerEvents(new SkillsListener(skillsManager), this);
        getCommand("skills").setExecutor(new SkillsCommand(skillsManager));
        getLogger().info("SkillCraft enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SkillCraft disabled!");
    }
}