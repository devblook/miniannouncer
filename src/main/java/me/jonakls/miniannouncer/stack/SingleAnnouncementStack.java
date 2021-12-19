package me.jonakls.miniannouncer.stack;

import java.util.List;

public class SingleAnnouncementStack implements AnnouncementStack {

    private final String component;

    public SingleAnnouncementStack(List<String> frames) {
        this.component = frames.get(0);
    }

    @Override
    public String current() {
        return component;
    }

    @Override
    public String next() {
        return component;
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
