package com.SkillCraft.GitHub;

import com.SkillCraft.GitHub.commands.SkillsCommand;
import com.SkillCraft.GitHub.gui.SkillsGUI;
import com.SkillCraft.GitHub.listeners.EventListener;
import com.SkillCraft.GitHub.managers.SkillsManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;

public class MainPlugin extends JavaPlugin {

    private SkillsManager skillsManager;

    @Override
    public void onEnable() {
        this.skillsManager = new SkillsManager(this);
        SkillsGUI skillsGUI = new SkillsGUI(skillsManager);
        Objects.requireNonNull(getCommand("skills")).setExecutor(new SkillsCommand(skillsGUI));
        getServer().getPluginManager().registerEvents(new EventListener(skillsManager), this);
        getLogger().info("SkillCraft has been enabled!");
    }

    @Override
    public void onDisable() {
        if (skillsManager != null) {
            skillsManager.cleanup();
        }
        getLogger().info("SkillCraft has been disabled!");
    }
}