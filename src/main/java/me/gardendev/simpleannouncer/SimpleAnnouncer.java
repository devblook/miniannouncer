package me.gardendev.simpleannouncer;

import me.gardendev.simpleannouncer.commands.MainCommand;
import me.gardendev.simpleannouncer.managers.ActionsManager;
import me.gardendev.simpleannouncer.managers.AnnouncerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SimpleAnnouncer extends JavaPlugin {

    private final FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        this.registerConfig();
        // Command
        getCommand("simpleannouncer").setExecutor(new MainCommand(this));
        AnnouncerManager announcerManager = new AnnouncerManager(this);
        announcerManager.task();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerConfig() {
        File configFile = new File(this.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            config.options().copyDefaults(true);
            saveConfig();
        }
    }
}
