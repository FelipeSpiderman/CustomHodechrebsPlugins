package com.SkillCraft.GitHub;

import com.SkillCraft.GitHub.data.PlayerData;
import com.SkillCraft.GitHub.managers.SkillsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillsManagerTest {
    private SkillsManager skillsManager;

    @Mock
    private JavaPlugin plugin;

    @Mock
    private Player player;

    @Mock
    private FileConfiguration config;

    @BeforeEach
    void setUp() {
        // Set up plugin config
        when(plugin.getConfig()).thenReturn(config);
        when(config.getConfigurationSection("skills")).thenReturn(config);
        when(config.getKeys(false)).thenReturn(Set.of("mining"));
        when(config.getInt(anyString())).thenReturn(100);
        when(config.getString(anyString())).thenReturn("DARK_AQUA");

        // Set up player
        when(player.getUniqueId()).thenReturn(UUID.randomUUID());
        doNothing().when(player).sendMessage(anyString());

        // Create manager
        skillsManager = new SkillsManager(plugin);
    }

    @Test
    void testAddXpLevelsUpPlayer() {
        // When
        skillsManager.addXp(player, "mining", 150);

        // Then
        PlayerData data = skillsManager.getPlayerData(player);
        assertEquals(1, data.getLevel("mining"), "Player should level up once");
        assertEquals(50, data.getXp("mining"), "Remaining XP should be 50");

        verify(player).sendMessage(contains("Level Up!"));
    }
}