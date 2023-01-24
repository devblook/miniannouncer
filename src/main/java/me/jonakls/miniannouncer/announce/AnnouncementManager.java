package me.jonakls.miniannouncer.announce;

import me.jonakls.miniannouncer.MiniAnnouncer;
import me.jonakls.miniannouncer.announce.task.AnnouncementTask;
import me.jonakls.miniannouncer.message.MessageHandler;
import me.jonakls.miniannouncer.announce.stack.AnnouncementStack;
import me.jonakls.miniannouncer.announce.stack.AnnouncementStackCreator;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class AnnouncementManager {

    private final MiniAnnouncer plugin;
    private final MessageHandler messageHandler;
    private final Logger logger;

    private int taskId;

    @Inject
    public AnnouncementManager(MiniAnnouncer plugin, MessageHandler messageHandler,
                               Logger logger) {
        this.plugin = plugin;
        this.messageHandler = messageHandler;
        this.logger = logger;
    }

    public List<Announcement> parseAnnouncements() {
        FileConfiguration configuration = plugin.getConfig();
        ConfigurationSection section = configuration
                .getConfigurationSection("announcements");

        if (section == null) {
            return Collections.emptyList();
        }

        return AnnouncementStackCreator.parse(section);
    }

    public @Nullable AnnouncementStack createStack() {
        FileConfiguration configuration = plugin.getConfig();
        ConfigurationSection section = configuration.getConfigurationSection("announcer");

        if (section == null) {
            return null;
        }

        List<Announcement> announcements = parseAnnouncements();

        if (announcements.isEmpty()) {
            logger.warn("Announcements are empty");
            return null;
        }

        logger.info("DEBUG: announcements size " + announcements.size());

        return AnnouncementStackCreator.createStack(section, announcements);
    }

    public void toggleAnnouncements(CommandSender sender) {
        FileConfiguration configuration = plugin.getConfig();
        boolean state = !configuration.getBoolean("announcer.enabled");

        if (state) {
            AnnouncementStack announcementStack = createStack();
            startTask(announcementStack);
        } else {
            stopTask();
        }

        configuration.set("announcer.enabled", state);
        messageHandler.sendMessage(sender, "toggle-announcements", state);
    }

    public void startTask(AnnouncementStack announcementStack) {
        FileConfiguration configuration = plugin.getConfig();
        taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(
                plugin,
                new AnnouncementTask(announcementStack, messageHandler),
                0L, 20L * configuration.getInt("announcer.interval")
        ).getTaskId();
    }

    public void stopTask() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public void reloadAnnouncer() {
        stopTask();
        logger.info("Announcements were restarted!!");
        startTask(createStack());
    }

}
