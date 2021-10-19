package me.gardendev.fenixannouncer.managers;

import me.gardendev.fenixannouncer.FenixAnnouncer;
import me.gardendev.fenixannouncer.utils.PlaceholderUtil;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Random;

public class AnnouncerManager {

    private final ActionsManager actions;

    private final FileConfiguration config;
    private final FenixAnnouncer plugin;
    private int i = 0;
    private BukkitTask task;

    public AnnouncerManager(FenixAnnouncer plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.actions = new ActionsManager(plugin);
    }

    public void announce() {
        List<String> announcements = config.getStringList("interval-announcement");
        Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
        Random number = new Random();

        if (config.getBoolean("announcer.random")) {
            List<String> announcement = config.getStringList(
                    "announcements." + announcements.get(number.nextInt(announcements.size()))
            );
            for (String line : announcement) {
                Bukkit.getOnlinePlayers()
                        .forEach(player -> actions.execute(audience, PlaceholderUtil.placeholder(player ,line)));
            }
            return;
        }

        if (i != announcements.size()) {
            i++;
            for (String line : config.getStringList("announcements." + announcements.get(i - 1))) {
                Bukkit.getOnlinePlayers()
                        .forEach(player -> actions.execute(audience, PlaceholderUtil.placeholder(player ,line)));
            }
            return;
        }
        i = 0;
    }

    public void initTask() {
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(
                plugin,
                () -> {
                    if (!config.getBoolean("announcer.enabled")) return;
                    this.announce();
                },
                0,
                (long) 20 * config.getInt("announcer.interval")
        );

    }

    public void stopTask() {
        task.cancel();
    }
}
