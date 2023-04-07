package me.jonakls.miniannouncer.core.configuration.sections;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.List;

@ConfigSerializable
public class Configuration {

    @Comment("Announcer settings for the plugin")
    private Announcer announcer = new Announcer();
    @Comment("Announcements to be broadcasted")
    private List<Announcements> announcements = List.of(new Announcements());
    @Comment("Messages of the plugin")
    private Messages messages = new Messages();

    public Announcer announcer() {
        return announcer;
    }

    public List<Announcements> announcements() {
        return announcements;
    }

    public Messages messages() {
        return messages;
    }
}
