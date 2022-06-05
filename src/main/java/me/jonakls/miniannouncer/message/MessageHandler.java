package me.jonakls.miniannouncer.message;

import me.jonakls.miniannouncer.MiniAnnouncer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;

public class MessageHandler {

    private static final String BASE_PATH = "messages.";

    private final MiniAnnouncer plugin;
    private final Set<MessageInterceptor> interceptors;

    public MessageHandler(MiniAnnouncer plugin,
                          Set<MessageInterceptor> interceptors) {
        this.plugin = plugin;
        this.interceptors = interceptors;
    }

    public String applyInterceptors(CommandSender sender, String message) {
        for (MessageInterceptor interceptor : interceptors) {
            message = interceptor.intercept(sender, message);
        }

        return message;
    }

    public String getMessage(CommandSender sender, String path,
                             Object... replacements) {
        FileConfiguration configuration = plugin.getConfig();
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
        FileConfiguration configuration = plugin.getConfig();
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

    public static MessageHandlerBuilder builder(MiniAnnouncer plugin) {
        return new MessageHandlerBuilder(plugin);
    }

}
