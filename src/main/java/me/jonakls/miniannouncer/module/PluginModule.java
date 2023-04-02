package me.jonakls.miniannouncer.module;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jonakls.miniannouncer.BukkitConfiguration;
import me.jonakls.miniannouncer.MiniAnnouncer;
import me.jonakls.miniannouncer.announce.AnnounceService;
import me.jonakls.miniannouncer.announce.AnnouncementManager;
import me.jonakls.miniannouncer.message.MessageHandler;
import me.jonakls.miniannouncer.message.MessageInterceptor;
import me.jonakls.miniannouncer.module.submodules.CommandModule;
import me.jonakls.miniannouncer.service.CommandService;
import me.jonakls.miniannouncer.service.Service;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;

import javax.inject.Singleton;

public class PluginModule extends AbstractModule {

    private final MiniAnnouncer plugin;

    public PluginModule(MiniAnnouncer plugin) {
        this.plugin = plugin;
    }

    @Singleton
    @Provides
    public Logger logger(MiniAnnouncer plugin) {
        return plugin.getSLF4JLogger();
    }

    @Override
    protected void configure() {
        bind(MiniAnnouncer.class).toInstance(plugin);

        BukkitConfiguration config = new BukkitConfiguration(plugin, "config");
        bind(BukkitConfiguration.class)
                .toInstance(config);

        MessageHandler messageHandler = new MessageHandler(config);

        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {

            messageHandler.addInterceptor((sender, message) -> {
                if (sender instanceof Player) {
                    return PlaceholderAPI.setPlaceholders((Player) sender, message);
                }
                return message;
            });
        }

        messageHandler.addInterceptor(MessageInterceptor.CHAT_COLOR_INTERCEPTOR);

        bind(MessageHandler.class)
                .toInstance(messageHandler);

        multibind(Service.class)
                .asSet()
                .to(AnnounceService.class)
                .to(CommandService.class)
                .singleton();

        bind(AnnouncementManager.class).singleton();

        install(new CommandModule(plugin));
    }
}
