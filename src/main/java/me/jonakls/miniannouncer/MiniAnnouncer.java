package me.jonakls.miniannouncer;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jonakls.miniannouncer.announce.AnnouncementManager;
import me.jonakls.miniannouncer.commands.MainCommand;
import me.jonakls.miniannouncer.commands.MainCommandTabCompleter;
import me.jonakls.miniannouncer.message.MessageHandler;
import me.jonakls.miniannouncer.message.MessageHandlerBuilder;
import me.jonakls.miniannouncer.message.MessageInterceptor;
import me.jonakls.miniannouncer.stack.AnnouncementStackCreator;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class MiniAnnouncer extends JavaPlugin {

    private MessageHandler messageHandler;
    private AnnouncementStackCreator stackCreator;
    private AnnouncementManager announcementManager;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        MessageHandlerBuilder messageHandlerBuilder = MessageHandler
                .builder(this)
                .addInterceptor(MessageInterceptor.CHAT_COLOR_INTERCEPTOR);

        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            messageHandlerBuilder.addInterceptor(
                    (sender, message) -> {
                        if (sender instanceof Player) {
                            return PlaceholderAPI.setPlaceholders(
                                    (Player) sender, message
                            );
                        }

                        return message;
                    }
            );
            getLogger().info("PlaceholderAPI has been found, using it!");
        }

        messageHandler = messageHandlerBuilder.build();
        stackCreator = new AnnouncementStackCreator();
        announcementManager = new AnnouncementManager(this, messageHandler, stackCreator);

        announcementManager.startTask(this, announcementManager.createStack());

        PluginCommand command = getCommand("miniannouncer");

        command.setExecutor(new MainCommand(
                this, announcementManager, messageHandler
        ));

        command.setTabCompleter(new MainCommandTabCompleter());
    }

    @Override
    public void onDisable() {
        announcementManager.stopTask();
    }

    public void reloadAnnouncer() {
        announcementManager.stopTask();
        getLogger().info("Announcements were restarted!!");
        announcementManager.startTask(this, announcementManager.createStack());
    }

}
