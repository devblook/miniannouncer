package me.jonakls.miniannouncer.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import me.jonakls.miniannouncer.BukkitConfiguration;
import me.jonakls.miniannouncer.announce.AnnouncementManager;
import me.jonakls.miniannouncer.message.MessageHandler;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;

@Command("miniannouncer")
@Permission("miniannouncer.commands")
public class MainCommand extends BaseCommand {

    @Inject
    private MessageHandler messageHandler;

    @Inject
    private AnnouncementManager announcementManager;

    @Inject
    private BukkitConfiguration configuration;


    @Default
    public void main(CommandSender sender) {
        messageHandler.sendListMessage(sender, "help");
    }

    @SubCommand("toggle")
    public void toggle(CommandSender sender) {
        announcementManager.toggleAnnouncements(sender);
    }

    @SubCommand("reload")
    public void reload(CommandSender sender) {
        configuration.reload();
        announcementManager.reloadAnnouncer();
        messageHandler.sendMessage(sender, "config-reloaded");
    }
}
