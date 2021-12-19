package me.jonakls.miniannouncer.stack;

import me.jonakls.miniannouncer.announce.Announcement;

public interface AnnouncementStack {

    Announcement current();

    Announcement next();

    boolean hasNext();

    int getSize();

}
