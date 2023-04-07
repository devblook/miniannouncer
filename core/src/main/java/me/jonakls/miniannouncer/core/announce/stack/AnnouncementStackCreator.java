package me.jonakls.miniannouncer.core.announce.stack;

import me.jonakls.miniannouncer.core.announce.Announcement;
import me.jonakls.miniannouncer.core.configuration.sections.Announcements;
import me.jonakls.miniannouncer.core.configuration.sections.Announcer;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementStackCreator {

    public static List<Announcement> parse(List<Announcements> sections) {
        List<Announcement> announcements = new ArrayList<>();

        sections.forEach(section -> {
            if (section.lines() != null || section.lines().size() > 0) {
                announcements.add(new Announcement(section.lines()));
            }
        });

        return announcements;
    }

    public static @Nullable AnnouncementStack createStack(Announcer announcerSection,
                                                          List<Announcement> announcements) {
        String type = announcerSection.type();

        if (type == null) {
            return null;
        }

        return switch (type) {
            case "random" -> new RandomLoopingAnnouncementStack(
                    announcements, announcerSection.loops()
            );
            case "single" -> new SingleAnnouncementStack(announcements);
            case "simple" -> new SimpleAnnouncementStack(announcements);
            default -> new LoopingAnnouncementStack(
                    announcements, announcerSection.loops()
            );
        };
    }

}
