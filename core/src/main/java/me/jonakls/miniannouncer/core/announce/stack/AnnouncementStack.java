package me.jonakls.miniannouncer.core.announce.stack;


import me.jonakls.miniannouncer.core.announce.Announcement;

public interface AnnouncementStack {

    Announcement current();

    Announcement next();

    boolean hasNext();

    int getSize();

}
