package me.jonakls.miniannouncer.announce.stack;

import me.jonakls.miniannouncer.announce.Announcement;

import java.util.List;

public class SimpleAnnouncementStack implements AnnouncementStack {

    private final List<Announcement> announcements;

    protected int cursor;
    protected Announcement current;

    public SimpleAnnouncementStack(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    @Override
    public Announcement current() {
        return current;
    }

    @Override
    public Announcement next() {
        if (cursor >= getSize()) {
            cursor = 0;
        }

        current = announcements.get(cursor++);

        return current;
    }

    @Override
    public boolean hasNext() {
        return cursor >= 0 && cursor <= getSize();
    }

    @Override
    public int getSize() {
        return announcements.size();
    }

}
