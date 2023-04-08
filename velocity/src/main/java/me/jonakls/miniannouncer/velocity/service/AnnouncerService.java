package me.jonakls.miniannouncer.velocity.service;

import com.google.inject.Inject;
import me.jonakls.miniannouncer.api.Service;
import me.jonakls.miniannouncer.core.announce.stack.AnnouncementStack;
import me.jonakls.miniannouncer.velocity.announce.AnnounceManager;

public class AnnouncerService implements Service {

    @Inject
    private AnnounceManager manager;

    @Override
    public void start() {
        AnnouncementStack stack = manager.createStack();

        if (stack == null) {
            return;
        }

        manager.startSchedule(stack);
    }

    @Override
    public void stop() {
        manager.stopSchedule();
    }
}
