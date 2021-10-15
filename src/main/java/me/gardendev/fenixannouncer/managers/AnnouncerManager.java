package me.gardendev.fenixannouncer.managers;

import me.gardendev.fenixannouncer.FenixAnnouncer;
import me.gardendev.fenixannouncer.utils.PlaceholderUtil;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class AnnouncerManager {

    private final ActionsManager actions;

    private final FileConfiguration config;
    private final List<String> announcements;
    private final FenixAnnouncer plugin;
    private int i = 0;

    public AnnouncerManager(FenixAnnouncer plugin) {
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
                actions.execute(audience, PlaceholderUtil.placeholder((Player) audience,line));
            }
            return;
        }

        if (i != announcements.size()) {
            i++;
            for (String line : config.getStringList("announcements." + announcements.get(i))) {
                actions.execute(audience, PlaceholderUtil.placeholder((Player) audience,line));
            }
            return;
        }
        i = 0;
    }
}
