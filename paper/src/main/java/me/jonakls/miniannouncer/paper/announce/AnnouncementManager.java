package me.jonakls.miniannouncer.paper.announce;

import me.jonakls.miniannouncer.core.announce.Announcement;
import me.jonakls.miniannouncer.core.announce.stack.AnnouncementStack;
import me.jonakls.miniannouncer.core.announce.stack.AnnouncementStackCreator;
import me.jonakls.miniannouncer.core.configuration.YamlPluginConfiguration;
import me.jonakls.miniannouncer.core.configuration.sections.Announcements;
import me.jonakls.miniannouncer.core.configuration.sections.Announcer;
import me.jonakls.miniannouncer.core.configuration.sections.Configuration;
import me.jonakls.miniannouncer.core.configuration.sections.Messages;
import me.jonakls.miniannouncer.paper.MiniAnnouncerPaper;
import me.jonakls.miniannouncer.paper.announce.task.AnnouncementTask;
import me.jonakls.miniannouncer.paper.message.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.InjectIgnore;

import java.util.Collections;
import java.util.List;

@InjectAll
public class AnnouncementManager {

    private MiniAnnouncerPaper plugin;
    private MessageHandler messageHandler;
    private Logger logger;
    private YamlPluginConfiguration<Configuration> config;

    @InjectIgnore
    private int taskId;

    public List<Announcement> parseAnnouncements() {
        List<Announcements> section = config.get().announcements();

        if (section == null) {
            return Collections.emptyList();
        }

        return AnnouncementStackCreator.parse(section);
    }

    public @Nullable AnnouncementStack createStack() {
        Announcer section = config.get().announcer();

        if (section == null) {
            return null;
        }

        List<Announcement> announcements = parseAnnouncements();

        if (announcements.isEmpty()) {
            logger.warn("Announcements are empty");
            return null;
        }

        return AnnouncementStackCreator.createStack(section, announcements);
    }


    public void toggleAnnouncements(CommandSender sender) {
        CommentedConfigurationNode node = config.getNode();
        Messages messages = config.get().messages();
        boolean state = !config.get().announcer().enabled();

        if (state) {
            AnnouncementStack announcementStack = createStack();
            startTask(announcementStack);
        } else {
            stopTask();
        }

        try {
            node.node("announcer", "enabled").set(state);
            config.save(node);
            config.reload();
        } catch (ConfigurateException e) {
            logger.error("Failed to set state of announcements", e);
            return;
        }

        messageHandler.sendMessage(
                sender,
                state ? messages.toggleAnnouncements().enabled() : messages.toggleAnnouncements().disabled()
        );
    }

    public void startTask(AnnouncementStack announcementStack) {
        taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(
                plugin,
                new AnnouncementTask(announcementStack, messageHandler),
                0L, 20L * config.get().announcer().interval()
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
