package me.jonakls.miniannouncer.module.submodules;

import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.core.BaseCommand;
import me.jonakls.miniannouncer.MiniAnnouncer;
import me.jonakls.miniannouncer.command.MainCommand;
import org.bukkit.command.CommandSender;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;

import javax.inject.Singleton;

public class CommandModule extends AbstractModule {

    private final MiniAnnouncer plugin;

    public CommandModule(MiniAnnouncer plugin) {
        this.plugin = plugin;
    }

    @Singleton
    @Provides
    public BukkitCommandManager<CommandSender> commandManager() {
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
