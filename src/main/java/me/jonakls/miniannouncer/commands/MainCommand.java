package me.jonakls.miniannouncer.commands;

import me.jonakls.miniannouncer.MiniAnnouncer;
import me.jonakls.miniannouncer.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

    private final MiniAnnouncer plugin;

    public MainCommand(MiniAnnouncer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission("miniannouncer.commands")) {
            sender.sendMessage(ChatUtil.toLegacyColors("&cNo permissions!"));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatUtil.toLegacyColors("&cUnknown Command"));
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "enable":
                plugin.getConfig().set("announcer.enabled", true);
                plugin.reloadConfig();
                plugin.getAnnouncerManager().initTask();
                sender.sendMessage(ChatUtil.toLegacyColors("&aStart all announcements"));
                break;
            case "disable":
                plugin.getConfig().set("announcer.enabled", false);
                plugin.reloadConfig();
                plugin.getAnnouncerManager().stopTask();
                sender.sendMessage(ChatUtil.toLegacyColors("&aStop all announcements"));
                break;
            case "reload":
                plugin.getAnnouncerManager().stopTask();
                plugin.reloadConfig();
                sender.sendMessage(ChatUtil.toLegacyColors("&aPlugin has been reloaded"));
                plugin.getAnnouncerManager().initTask();
                break;
            case "info":
                sender.sendMessage(
                        ChatUtil.toLegacyColors("&6MiniAnnouncer &8- &cv" + plugin.getDescription().getVersion()),
                        ChatUtil.toLegacyColors("&r"),
                        ChatUtil.toLegacyColors("&eMade by: &c" + plugin.getDescription().getAuthors())
                );
                break;
            default:
                sender.sendMessage(
                        ChatUtil.toLegacyColors("&6MiniAnnouncer &8- &cv" + plugin.getDescription().getVersion()),
                        ChatUtil.toLegacyColors("&r"),
                        ChatUtil.toLegacyColors("&e/ma - /minia - /announcer - /miniannouncer"),
                        ChatUtil.toLegacyColors("&r"),
                        ChatUtil.toLegacyColors("&a/ma reload &8| &eReload plugin"),
                        ChatUtil.toLegacyColors("&a/ma info &8| &eShow plugin info"),
                        ChatUtil.toLegacyColors("&a/ma help &8| &eShow this message")
                );
                break;
        }
        return true;
    }
}
