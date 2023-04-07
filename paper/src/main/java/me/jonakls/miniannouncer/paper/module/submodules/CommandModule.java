package me.jonakls.miniannouncer.paper.module.submodules;

import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.core.BaseCommand;
import me.jonakls.miniannouncer.paper.MiniAnnouncerPaper;
import me.jonakls.miniannouncer.paper.command.MainCommand;
import org.bukkit.command.CommandSender;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;

import javax.inject.Singleton;

public class CommandModule extends AbstractModule {

    @Singleton
    @Provides
    public BukkitCommandManager<CommandSender> commandManager(MiniAnnouncerPaper plugin) {
        return BukkitCommandManager.create(plugin);
    }

    @Override
    protected void configure() {
        multibind(BaseCommand.class)
                .asSet()
                .to(MainCommand.class)
                .singleton();
    }
}
