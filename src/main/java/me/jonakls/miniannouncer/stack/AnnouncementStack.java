package me.jonakls.miniannouncer.stack;

public interface AnnouncementStack {

    String current();

    String next();

    boolean hasNext();

    int getSize();

}
