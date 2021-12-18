package me.jonakls.miniannouncer.stack;

import me.jonakls.miniannouncer.utils.MiniMessageUtil;
import net.kyori.adventure.text.Component;

import java.util.List;

public class SingleAnnouncementStack implements AnnouncementStack {

    private final Component component;

    public SingleAnnouncementStack(List<String> frames) {
        this.component = MiniMessageUtil.toMiniMessage(frames.get(0));
    }

    @Override
    public Component current() {
        return component;
    }

    @Override
    public Component next() {
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
