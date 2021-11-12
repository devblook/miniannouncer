package me.jonakls.fenixannouncer;

import me.jonakls.fenixannouncer.commands.MainCommand;
import me.jonakls.fenixannouncer.managers.AnnouncerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

@SuppressWarnings("all")
public final class FenixAnnouncer extends JavaPlugin {

    private AnnouncerManager announcerManager;

    @Override
    public void onEnable() {
        this.registerConfig();

        this.announcerManager = new AnnouncerManager(this);
        this.announcerManager.initTask();
        getCommand("fenixannouncer").setExecutor(new MainCommand(this));

        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            getLogger().info("Hook PlaceholderAPI");
            return;
        }
        getLogger().log(Level.WARNING, "The PlaceholderAPI plugin is missing, this plugin cannot function properly without this dependency");

    }

    @Override
    public void onDisable() {
        this.announcerManager.stopTask();
    }

    private void registerConfig() {
        File configFile = new File(this.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            this.getConfig().options().copyDefaults();
            this.saveDefaultConfig();
        }
    }

    public AnnouncerManager getAnnouncerManager() {
        return announcerManager;
    }
}
