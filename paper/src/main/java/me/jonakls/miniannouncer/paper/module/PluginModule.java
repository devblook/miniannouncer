package me.jonakls.miniannouncer.paper.module;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jonakls.miniannouncer.paper.MiniAnnouncerPaper;
import me.jonakls.miniannouncer.paper.announce.AnnounceService;
import me.jonakls.miniannouncer.paper.announce.AnnouncementManager;
import me.jonakls.miniannouncer.paper.message.MessageHandler;
import me.jonakls.miniannouncer.paper.message.MessageInterceptor;
import me.jonakls.miniannouncer.paper.module.submodules.CommandModule;
import me.jonakls.miniannouncer.paper.module.submodules.FileModule;
import me.jonakls.miniannouncer.paper.service.CommandService;
import me.jonakls.miniannouncer.paper.service.Service;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;

import javax.inject.Singleton;

public class PluginModule extends AbstractModule {

    private final MiniAnnouncerPaper plugin;

    public PluginModule(MiniAnnouncerPaper plugin) {
        this.plugin = plugin;
    }

    @Singleton
    @Provides
    public Logger logger(MiniAnnouncerPaper plugin) {
        return plugin.getSLF4JLogger();
    }

    @Singleton
    @Provides
    public MessageHandler messageHandler() {
        MessageHandler messageHandler = new MessageHandler();

        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {

            messageHandler.addInterceptor((sender, message) -> {
                if (sender instanceof Player) {
                    return PlaceholderAPI.setPlaceholders((Player) sender, message);
                }
                return message;
            });
        }

        messageHandler.addInterceptor(MessageInterceptor.CHAT_COLOR_INTERCEPTOR);

        return messageHandler;
    }

    @Override
    protected void configure() {
        bind(MiniAnnouncerPaper.class).toInstance(plugin);

        multibind(Service.class)
                .asSet()
                .to(AnnounceService.class)
                .to(CommandService.class)
                .singleton();

        bind(AnnouncementManager.class).singleton();

        install(new CommandModule());
        install(new FileModule());
    }
}
