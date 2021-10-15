package me.gardendev.simpleannouncer.managers;

import me.gardendev.simpleannouncer.SimpleAnnouncer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Random;

public class AnnouncerManager {

    private final ActionsManager actions;

    private final FileConfiguration config;
    private final List<String> announcements;
    private final SimpleAnnouncer plugin;

    public AnnouncerManager(SimpleAnnouncer plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.announcements = config.getStringList("interval-announcement");
        this.actions = new ActionsManager(plugin);
    }

    public void task() {
        Bukkit.getServer().getScheduler().runTaskTimer(
                plugin,
                this::announce,
                0,
                (long) 20 * config.getInt("announcer.interval")
        );
    }

    public void announce() {
        Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
        Random number = new Random();

        if (config.getBoolean("announcer.random")) {
            List<String> announcement = config.getStringList(
                    "announcements." + announcements.get(number.nextInt(announcements.size()))
            );
            for (String line : announcement) {
                actions.execute(audience, line);
            }
            return;
        }
        int i = announcements.size();
        if (i != 0) {
            i--;
            for (String line : config.getStringList("announcements." + announcements.get(i))) {
                actions.execute(audience, line);
            }
        }
    }
}
