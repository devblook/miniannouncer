package me.jonakls.miniannouncer.paper.announce;

import me.jonakls.miniannouncer.core.announce.Announcement;
import me.jonakls.miniannouncer.core.announce.stack.AnnouncementStack;
import me.jonakls.miniannouncer.core.announce.stack.AnnouncementStackCreator;
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

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class AnnouncementManager {

    @Inject
    private MiniAnnouncerPaper plugin;

    @Inject
    private MessageHandler messageHandler;

    @Inject
    private Logger logger;

    @Inject
    private Configuration config;

    private int taskId;

    public List<Announcement> parseAnnouncements() {
        List<Announcements> section = config.announcements();

        if (section == null) {
            return Collections.emptyList();
        }

        return AnnouncementStackCreator.parse(section);
    }

    public @Nullable AnnouncementStack createStack() {
        Announcer section = config.announcer();

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

    @Deprecated
    public void toggleAnnouncements(CommandSender sender) {
        Messages messages = config.messages();
        boolean state = !config.announcer().enabled();

        if (state) {
            AnnouncementStack announcementStack = createStack();
            startTask(announcementStack);
        } else {
            stopTask();
        }

        //Todo: add logic for this method
        messageHandler.sendMessage(
                sender,
                state ? messages.toggleAnnouncements().enabled() : messages.toggleAnnouncements().disabled()
        );
    }

    public void startTask(AnnouncementStack announcementStack) {
        taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(
                plugin,
                new AnnouncementTask(announcementStack, messageHandler),
                0L, 20L * config.announcer().interval()
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
