package me.jonakls.miniannouncer.velocity.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.velocitypowered.api.command.CommandSource;
import me.jonakls.miniannouncer.core.configuration.YamlPluginConfiguration;
import me.jonakls.miniannouncer.core.configuration.sections.Configuration;
import me.jonakls.miniannouncer.velocity.announce.AnnounceManager;

@CommandAlias("miniannouncer|ma")
public class MainCommand extends BaseCommand {

    //private MessageHandler messageHandler;
    private AnnounceManager announceManager;
    private YamlPluginConfiguration<Configuration> paperConfig;


    @Default
    public void main(CommandSource sender) {
        //messageHandler.sendListMessage(sender, paperConfig.get().messages().help());

    }

    @CommandAlias("toggle")
    public void toggle(CommandSource sender) {
        //announceManager.toggleAnnouncements(sender);
    }

    @CommandAlias("reload")
    public void reload(CommandSource sender) {
        paperConfig.reload();
        //announcementManager.reloadAnnouncer();
        //messageHandler.sendMessage(sender, paperConfig.get().messages().configReloaded());
    }


}
