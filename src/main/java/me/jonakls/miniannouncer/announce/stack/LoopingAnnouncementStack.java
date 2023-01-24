package me.jonakls.miniannouncer.announce.stack;

import me.jonakls.miniannouncer.announce.Announcement;

import java.util.List;

public class LoopingAnnouncementStack extends SimpleAnnouncementStack {

    private final int loops;
    private final boolean infiniteLoop;

    private int loopCounter;

    public LoopingAnnouncementStack(List<Announcement> announcements, int loops) {
        super(announcements);
        this.loops = loops;
        this.infiniteLoop = loops <= 0;
    }

    @Override
    public boolean hasNext() {
        return super.hasNext() && (infiniteLoop || loopCounter <= loops);
    }

    @Override
    public Announcement next() {
        Announcement next = super.next();

        if (next == null) {
            loopCounter++;

            if (loopCounter >= loops && !infiniteLoop) {
                return null;
            }

            cursor = 0;
            return super.next();
        } else {
            return next;
        }
    }

}
