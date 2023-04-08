package me.jonakls.miniannouncer.velocity.announce.schedule;

import com.velocitypowered.api.proxy.ProxyServer;
import me.jonakls.miniannouncer.core.announce.Announcement;
import me.jonakls.miniannouncer.core.announce.stack.AnnouncementStack;
import net.kyori.adventure.text.Component;

public class AnnounceTask implements Runnable {

    private final AnnouncementStack stack;
    private final ProxyServer server;

    public AnnounceTask(AnnouncementStack stack, ProxyServer server) {
        this.stack = stack;
        this.server = server;
    }

    @Override
    public void run() {
        if (stack.hasNext()) {
            Announcement announcement = stack.next();
            for (String line : announcement.getLines()) {
                server.getAllPlayers().forEach(player -> player.sendMessage(
                        Component.text(line)
                ));
            }
        }

    }
}
