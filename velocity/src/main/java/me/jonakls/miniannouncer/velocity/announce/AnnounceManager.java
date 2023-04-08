package me.jonakls.miniannouncer.velocity.announce;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.ScheduledTask;
import me.jonakls.miniannouncer.core.announce.Announcement;
import me.jonakls.miniannouncer.core.announce.stack.AnnouncementStack;
import me.jonakls.miniannouncer.core.announce.stack.AnnouncementStackCreator;
import me.jonakls.miniannouncer.core.configuration.YamlPluginConfiguration;
import me.jonakls.miniannouncer.core.configuration.sections.Announcements;
import me.jonakls.miniannouncer.core.configuration.sections.Announcer;
import me.jonakls.miniannouncer.core.configuration.sections.Configuration;
import me.jonakls.miniannouncer.velocity.MiniAnnouncerVelocity;
import me.jonakls.miniannouncer.velocity.announce.schedule.AnnounceTask;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Singleton
public class AnnounceManager {

    @Inject
    private ProxyServer server;
    @Inject
    private MiniAnnouncerVelocity plugin;
    @Inject
    private YamlPluginConfiguration<Configuration> config;

    private ScheduledTask task;

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
            return null;
        }

        return AnnouncementStackCreator.createStack(section, announcements);
    }

    public void startSchedule(AnnouncementStack stack) {
        Announcer section = config.get().announcer();

        this.task = server.getScheduler()
                .buildTask(
                        plugin,
                        new AnnounceTask(stack, server)
                )
                .repeat(section.interval(), TimeUnit.SECONDS)
                .schedule();
    }

    public void stopSchedule() {
        if (this.task != null) {
            this.task.cancel();
        }
    }

}
