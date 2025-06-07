package com.SkillCraft.GitHub.listeners;

import com.SkillCraft.GitHub.managers.SkillsManager;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SkillsListener implements Listener {
    private final SkillsManager skillsManager;

    public SkillsListener(SkillsManager skillsManager) {
        this.skillsManager = skillsManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material block = event.getBlock().getType();
        int blocksBroken = player.getStatistic(Statistic.MINE_BLOCK, block);

        if (block.name().contains("ORE") || block == Material.STONE) {
            int xp = block.name().contains("ORE") ? 5 : 1;
            skillsManager.addXp(player, "mining", xp);
        } else if (block.name().contains("LOG")) {
            skillsManager.addXp(player, "foraging", 2);
        }
    }

}