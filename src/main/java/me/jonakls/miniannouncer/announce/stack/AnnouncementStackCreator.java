package me.jonakls.miniannouncer.announce.stack;

import me.jonakls.miniannouncer.announce.Announcement;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementStackCreator {

    public static List<Announcement> parse(ConfigurationSection section) {
        List<Announcement> announcements = new ArrayList<>();

        for (String key : section.getKeys(false)) {
            List<String> lines = section.getStringList(key);
            announcements.add(new Announcement(lines));
        }
        return announcements;
    }

    public static @Nullable AnnouncementStack createStack(ConfigurationSection section,
                                                   List<Announcement> announcements) {
        String type = section.getString("type");

        if (type == null) {
            return null;
        }

        switch (type) {
            case "random":
                return new RandomLoopingAnnouncementStack(
                        announcements, section.getInt("loops")
                );
            case "single":
                return new SingleAnnouncementStack(announcements);
            case "simple":
                return new SimpleAnnouncementStack(announcements);
            case "looping":
            default:
                return new LoopingAnnouncementStack(
                        announcements, section.getInt("loops")
                );
        }
    }

}
