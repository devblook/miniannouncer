package me.jonakls.miniannouncer.stack;

import net.kyori.adventure.text.Component;

import java.util.List;

public class LoopingAnnouncementStack extends SimpleAnnouncementStack {

    private final int loops;
    private final boolean infiniteLoop;

    private int loopCounter;

    public LoopingAnnouncementStack(List<String> frames, int loops) {
        super(frames);
        this.loops = loops;
        this.infiniteLoop = loops <= 0;
    }

    @Override
    public boolean hasNext() {
        return super.hasNext() && (infiniteLoop || loopCounter <= loops);
    }

    @Override
    public Component next() {
        Component component = super.next();

        if (component == null) {
            loopCounter++;

            if (loopCounter >= loops && !infiniteLoop) {
                return null;
            }

            cursor = 0;
            return super.next();
        } else {
            return component;
        }
    }

}
