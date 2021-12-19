package me.jonakls.miniannouncer.stack;

import java.util.List;

public class SimpleAnnouncementStack implements AnnouncementStack {

    private final List<String> frames;

    protected int cursor;
    protected String current;

    public SimpleAnnouncementStack(List<String> frames) {
        this.frames = frames;
    }

    @Override
    public String current() {
        return current;
    }

    @Override
    public String next() {
        if (cursor >= getSize()) {
            cursor = 0;
        }

        current = frames.get(cursor++);

        return current;
    }

    @Override
    public boolean hasNext() {
        return cursor >= 0 && cursor <= getSize();
    }

    @Override
    public int getSize() {
        return frames.size();
    }

}
