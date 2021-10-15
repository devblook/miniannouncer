package me.gardendev.simpleannouncer.commands;

import me.gardendev.simpleannouncer.SimpleAnnouncer;
import me.gardendev.simpleannouncer.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

    private final SimpleAnnouncer plugin;

    public MainCommand(SimpleAnnouncer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        FileConfiguration config = plugin.getConfig();

        if (!(sender instanceof Player)) {
            sender.sendMessage("No console commands please");
            return true;
        }
        Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage("Unknown Command");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "test":
                for (String path : config.getConfigurationSection("announcements").getKeys(false)) {
                    player.sendMessage(String.format("Announce: (%s)", path));
                    for (String line : config.getStringList("announcements." + path)) {
                        player.sendMessage(ChatUtil.toMiniMessage(line));
                    }
                }
                break;
            case "reload":
                plugin.reloadConfig();
                player.sendMessage("Plugin reloaded!");
                break;
            default:
                player.sendMessage("Unknown Command");
                break;
        }
        return false;
    }
}
