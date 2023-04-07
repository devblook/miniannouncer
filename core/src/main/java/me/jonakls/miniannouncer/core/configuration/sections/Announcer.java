package me.jonakls.miniannouncer.core.configuration.sections;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class Announcer {

    @Comment("Enable the announcer")
    private boolean enabled = true;
    @Comment("Type of announcer to use. Options: looping, random, simple, single")
    private String type = "looping";
    @Comment("Number of times to loop the announcements. Only used for looping and random announcers")
    private int loops = 1;
    @Comment("Interval between announcements in seconds. Only used for looping and random announcers")
    private int interval = 10;

    public boolean enabled() {
        return enabled;
    }

    public String type() {
        return type;
    }

    public int loops() {
        return loops;
    }

    public int interval() {
        return interval;
    }
}
