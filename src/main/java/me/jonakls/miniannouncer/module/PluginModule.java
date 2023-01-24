package me.jonakls.miniannouncer.module;

import me.clip.placeholderapi.PlaceholderAPI;
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
    public Logger logger() {
        return plugin.getSLF4JLogger();
    }

    @Singleton
    @Provides
    public MessageHandler messageHandler() {
        MessageHandler.Builder messageHandlerBuilder = MessageHandler.builder(plugin)
                .addInterceptor(MessageInterceptor.CHAT_COLOR_INTERCEPTOR);

        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            messageHandlerBuilder.addInterceptor((sender, message) -> {
                if (sender instanceof Player) {
                    return PlaceholderAPI.setPlaceholders((Player) sender, message);
                }

                return message;
            });
            logger().info("PlaceholderAPI has been found, using it!");
        }

        return messageHandlerBuilder.build();
    }


    @Override
    protected void configure() {
        bind(MiniAnnouncer.class).toInstance(plugin);


        multibind(Service.class)
                .asSet()
                .to(AnnounceService.class)
                .to(CommandService.class)
                .singleton();

        bind(AnnouncementManager.class).singleton();

        install(new CommandModule(plugin));
    }
}
