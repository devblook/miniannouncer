package me.jonakls.miniannouncer.message;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageHandler {

    private final FileConfiguration configuration;

    public MessageHandler(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public void sendMessage(CommandSender sender, String path) {

    }

}
