package me.jonakls.miniannouncer.core.announce.stack;

import me.jonakls.miniannouncer.core.announce.Announcement;

import java.util.List;

public class SingleAnnouncementStack implements AnnouncementStack {

    private final Announcement announcement;

    public SingleAnnouncementStack(List<Announcement> frames) {
        this.announcement = frames.get(0);
    }

    @Override
    public Announcement current() {
        return announcement;
    }

    @Override
    public Announcement next() {
        return announcement;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public int getSize() {
        return 0;
    }

}
