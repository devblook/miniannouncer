package me.jonakls.miniannouncer.stack;

import net.kyori.adventure.text.Component;

public interface AnnouncementStack {

    Component current();

    Component next();

    boolean hasNext();

    int getSize();

}
