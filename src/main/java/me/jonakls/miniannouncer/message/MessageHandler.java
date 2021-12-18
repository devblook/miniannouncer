package me.jonakls.miniannouncer.message;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;

public class MessageHandler {

    private static final String BASE_PATH = "messages.";

    private final FileConfiguration configuration;
    private final Set<MessageInterceptor> interceptors;

    public MessageHandler(FileConfiguration configuration,
                          Set<MessageInterceptor> interceptors) {
        this.configuration = configuration;
        this.interceptors = interceptors;
    }

    public String applyInterceptors(CommandSender sender, String message) {
        for (MessageInterceptor interceptor : interceptors) {
            message = interceptor.intercept(sender, message);
        }

        return message;
    }

    public String getMessage(CommandSender sender, String path) {
        String message = configuration.getString(BASE_PATH + path);

        if (message == null) {
            return path;
        }

        return applyInterceptors(sender, message);
    }

    public List<String> getMessages(CommandSender sender, String path) {
        List<String> messages = configuration.getStringList(BASE_PATH + path);
        messages.replaceAll(line -> applyInterceptors(sender, line));
        return messages;
    }

    public void sendMessage(CommandSender sender, String path) {
        sender.sendMessage(getMessage(sender, path));
    }

    public void sendMessages(CommandSender sender, String path) {
        sender.sendMessage(String.join("\n", getMessages(sender, path)));
    }

    public static MessageHandlerBuilder builder(FileConfiguration configuration) {
        return new MessageHandlerBuilder(configuration);
    }

}
