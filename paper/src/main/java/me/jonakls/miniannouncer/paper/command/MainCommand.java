package me.jonakls.miniannouncer.paper.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import me.jonakls.miniannouncer.core.configuration.YamlPluginConfiguration;
import me.jonakls.miniannouncer.core.configuration.sections.Configuration;
import me.jonakls.miniannouncer.paper.announce.AnnouncementManager;
import me.jonakls.miniannouncer.paper.message.MessageHandler;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;

@Command(value = "miniannouncer", alias = "ma")
@Permission("miniannouncer.commands")
public class MainCommand extends BaseCommand {

    @Inject
    private MessageHandler messageHandler;

    @Inject
    private AnnouncementManager announcementManager;

    @Inject
    private Configuration configuration;

    @Inject
    YamlPluginConfiguration<Configuration> paperConfig;


    @Default
    public void main(CommandSender sender) {
        messageHandler.sendListMessage(sender, configuration.messages().help());
    }

    @SubCommand("toggle")
    public void toggle(CommandSender sender) {
        announcementManager.toggleAnnouncements(sender);
    }

    @SubCommand("reload")
    public void reload(CommandSender sender) {
        paperConfig.reload();
        announcementManager.reloadAnnouncer();
        messageHandler.sendMessage(sender, configuration.messages().configReloaded());
    }
}