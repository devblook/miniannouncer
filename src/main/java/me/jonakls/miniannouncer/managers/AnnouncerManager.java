package me.jonakls.miniannouncer.managers;

import me.jonakls.miniannouncer.MiniAnnouncer;
import me.jonakls.miniannouncer.utils.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Random;

public class AnnouncerManager {

    private final ActionsManager actions;

    private final MiniAnnouncer plugin;
    private int i = 0;
    private BukkitTask task;

    public AnnouncerManager(MiniAnnouncer plugin) {
        this.plugin = plugin;
        this.actions = new ActionsManager(this.plugin);
    }

    public void announce() {
        FileConfiguration config = plugin.getConfig();
        List<String> announcements = config.getStringList("interval-announcement");
        Random number = new Random();

        if (config.getBoolean("announcer.random")) {
            List<String> announcement = config.getStringList(
                    "announcements." + announcements.get(number.nextInt(announcements.size()))
            );
            for (String line : announcement) {
                Bukkit.getOnlinePlayers()
                        .forEach(player -> actions.execute(player, PlaceholderUtil.placeholder(player ,line)));
            }
            return;
        }

        if (i != announcements.size()) {
            for (String line : config.getStringList("announcements." + announcements.get(i))) {
                Bukkit.getOnlinePlayers()
                        .forEach(player -> actions.execute(player, PlaceholderUtil.placeholder(player ,line)));
            }
            i++;
            return;
        }
        i = 0;
    }

    public void initTask() {
        FileConfiguration config = plugin.getConfig();
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
        this.task.cancel();
    }
}
