package me.jonakls.miniannouncer.stack;

import me.jonakls.miniannouncer.utils.MiniMessageUtil;
import net.kyori.adventure.text.Component;

import java.util.List;

public class SimpleAnnouncementStack implements AnnouncementStack {

    private final List<String> frames;

    protected int cursor;
    protected Component current;

    public SimpleAnnouncementStack(List<String> frames) {
        this.frames = frames;
    }

    @Override
    public Component current() {
        return current;
    }

    @Override
    public Component next() {
        if (cursor >= getSize()) {
            cursor = 0;
        }

        current = MiniMessageUtil.toMiniMessage(frames.get(cursor++));

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
