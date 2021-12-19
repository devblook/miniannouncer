package me.jonakls.miniannouncer.stack;

import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Random;

public class RandomLoopingAnnouncementStack extends LoopingAnnouncementStack {

    private static final Random RANDOM = new Random();

    private int lastCursor;

    public RandomLoopingAnnouncementStack(List<String> frames, int loops) {
        super(frames, loops);
    }

    @Override
    public String next() {
        do {
            cursor = RANDOM.nextInt(getSize());
        } while (cursor == lastCursor);

        lastCursor = cursor;

        return super.next();
    }

}
