package me.jonakls.miniannouncer.paper.service;

import me.jonakls.miniannouncer.api.Service;
import me.jonakls.miniannouncer.core.announce.stack.AnnouncementStack;
import me.jonakls.miniannouncer.paper.announce.AnnouncementManager;
import org.slf4j.Logger;

import javax.inject.Inject;

public class AnnounceService implements Service {

    @Inject
    private AnnouncementManager manager;

    @Inject
    private Logger logger;

    @Override
    public void start() {
        AnnouncementStack stack = manager.createStack();

        if (stack == null) {
            logger.error("Announcement creator is null, stopping service");
            return;
        }

        manager.startTask(stack);
    }

    @Override
    public void stop() {
        manager.stopTask();
    }
}
