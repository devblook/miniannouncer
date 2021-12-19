package me.jonakls.miniannouncer.stack;

import me.jonakls.miniannouncer.announce.Announcement;

import java.util.List;
import java.util.Random;

public class RandomLoopingAnnouncementStack extends LoopingAnnouncementStack {

    private static final Random RANDOM = new Random();

    private int lastCursor;

    public RandomLoopingAnnouncementStack(List<Announcement> announcements, int loops) {
        super(announcements, loops);
    }

    @Override
    public Announcement next() {
        do {
            cursor = RANDOM.nextInt(getSize());
        } while (cursor == lastCursor);

        lastCursor = cursor;

        return super.next();
    }

}
