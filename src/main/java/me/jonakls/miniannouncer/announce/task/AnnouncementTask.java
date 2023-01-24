package me.jonakls.miniannouncer.announce.task;

import me.jonakls.miniannouncer.announce.Announcement;
import me.jonakls.miniannouncer.message.MessageHandler;
import me.jonakls.miniannouncer.announce.stack.AnnouncementStack;
import me.jonakls.miniannouncer.utils.MiniMessageUtil;
import org.bukkit.Bukkit;

public class AnnouncementTask implements Runnable {

    private final AnnouncementStack announcementStack;
    private final MessageHandler messageHandler;

    public AnnouncementTask(AnnouncementStack announcementStack,
                            MessageHandler messageHandler) {
        this.announcementStack = announcementStack;
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        if (announcementStack.hasNext()) {
            Announcement announcement = announcementStack.next();
            for (String line : announcement.getLines()) {
                Bukkit.getOnlinePlayers()
                        .forEach(player -> MiniMessageUtil.execute(
                                player,
                                messageHandler.applyInterceptors(player, line)
                        ));
            }
        }
    }

}
