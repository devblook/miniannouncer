package me.jonakls.miniannouncer.commands;

import me.jonakls.miniannouncer.MiniAnnouncer;
import me.jonakls.miniannouncer.announce.AnnouncementManager;
import me.jonakls.miniannouncer.message.MessageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

    private final MiniAnnouncer plugin;
    private final AnnouncementManager announcementManager;
    private final MessageHandler messageHandler;

    public MainCommand(MiniAnnouncer plugin,
                       AnnouncementManager announcementManager,
                       MessageHandler messageHandler) {
        this.plugin = plugin;
        this.announcementManager = announcementManager;
        this.messageHandler = messageHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command,
                             @NotNull String label, String[] args) {
        if (!sender.hasPermission("miniannouncer.commands")) {
            messageHandler.sendMessage(sender, "no-permission");
            return true;
        }

        if (args.length < 1) {
            messageHandler.sendMessage(sender, "help");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "toggle" -> {
                announcementManager.toggleAnnouncements(plugin, sender);
            }
            case "reload" -> {
                plugin.reloadConfig();
                plugin.reloadAnnouncer();

                messageHandler.sendMessage(sender, "config-reloaded");


            }
            default -> {
                messageHandler.sendListMessage(sender, "help");
            }
        }
        return true;
    }
}
