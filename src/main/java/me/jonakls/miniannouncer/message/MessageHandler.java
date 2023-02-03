package me.jonakls.miniannouncer.message;

import me.jonakls.miniannouncer.BukkitConfiguration;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessageHandler {

    private static final String BASE_PATH = "messages.";

    private final Set<MessageInterceptor> interceptors;
    private final BukkitConfiguration config;

    public MessageHandler(BukkitConfiguration config) {
        this.config = config;
        this.interceptors = new HashSet<>();
    }

    public String applyInterceptors(CommandSender sender, String message) {
        for (MessageInterceptor interceptor : interceptors) {
            message = interceptor.intercept(sender, message);
        }

        return message;
    }

    public String getMessage(CommandSender sender, String path,
                             Object... replacements) {
        FileConfiguration configuration = config.get();
        String message = configuration.getString(BASE_PATH + path);

        if (message == null) {
            return path;
        }

        return String.format(
                applyInterceptors(sender, message),
                replacements
        );
    }

    public List<String> getMessages(CommandSender sender, String path,
                                    Object... replacements) {
        FileConfiguration configuration = config.get();
        List<String> messages = configuration.getStringList(BASE_PATH + path);
        messages.replaceAll(
                line -> String.format(
                        applyInterceptors(sender, line),
                        replacements
                ));
        return messages;
    }

    public void sendMessage(CommandSender sender, String path,
                            Object... replacements) {
        sender.sendMessage(getMessage(sender, path, replacements));
    }

    public void sendListMessage(CommandSender sender, String path,
                             Object... replacements) {
        sender.sendMessage(String.join("\n", getMessages(sender, path, replacements)));
    }

    public void addInterceptor(MessageInterceptor interceptor) {
        interceptors.add(interceptor);
    }
}
