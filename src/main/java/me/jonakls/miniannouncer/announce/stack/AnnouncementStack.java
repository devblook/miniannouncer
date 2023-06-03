package me.jonakls.miniannouncer.announce.stack;

import me.jonakls.miniannouncer.announce.Announcement;

public interface AnnouncementStack {

  Announcement current();

  Announcement next();

  boolean hasNext();

  int getSize();
}
