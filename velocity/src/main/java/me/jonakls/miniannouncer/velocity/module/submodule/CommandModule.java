package me.jonakls.miniannouncer.velocity.module.submodule;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.VelocityCommandManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import com.velocitypowered.api.proxy.ProxyServer;
import me.jonakls.miniannouncer.velocity.MiniAnnouncerVelocity;
import me.jonakls.miniannouncer.velocity.command.MainCommand;

public class CommandModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<BaseCommand> multibinder = Multibinder.newSetBinder(binder(), BaseCommand.class);

        multibinder.addBinding().to(MainCommand.class);
    }

    @Provides
    @Singleton
    public VelocityCommandManager provideMainCommand(ProxyServer server, MiniAnnouncerVelocity plugin) {
        return new VelocityCommandManager(server, plugin);
    }
}
