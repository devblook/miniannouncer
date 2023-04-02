package me.jonakls.miniannouncer.message;

import me.jonakls.miniannouncer.BukkitConfiguration;
import me.jonakls.miniannouncer.utils.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    public Component applyInterceptors(CommandSender sender, String message) {
        for (MessageInterceptor interceptor : interceptors) {
            message = interceptor.intercept(sender, message);
        }

        return MiniMessageUtil.toMiniMessage(message);
    }

    public Component applyInterceptors(CommandSender sender, Collection<String> strings) {
        Component message = Component.empty();
        for (String string : strings) {
            message = message.append(applyInterceptors(sender, string));
        }
        return message;
    }

    public Component getMessage(CommandSender sender, String path,
                             Object... replacements) {
        FileConfiguration configuration = config.get();
        String message = configuration.getString(BASE_PATH + path);

        if (message == null) {
            return Component.text(path);
        }

        return applyInterceptors(
                sender,
                String.format(message, replacements)
        );
    }

    public List<Component> getMessages(CommandSender sender, String path,
                                    Object... replacements) {
        FileConfiguration configuration = config.get();
        List<String> messages = configuration.getStringList(BASE_PATH + path);
        List<Component> components = new ArrayList<>(List.of(Component.empty()));

        if (messages.isEmpty()) {
            return Collections.singletonList(Component.text(path));
        }

        for (String message : messages) {
            components.add(applyInterceptors(
                    sender,
                    String.format(message, replacements)
            ));
        }

        return components;
    }

    public void sendMessage(CommandSender sender, String path,
                            Object... replacements) {
        sender.sendMessage(getMessage(sender, path, replacements));
    }

    public void sendListMessage(CommandSender sender, String path,
                             Object... replacements) {
        getMessages(sender, path, replacements)
                .forEach(sender::sendMessage);
    }

    public void addInterceptor(MessageInterceptor interceptor) {
        interceptors.add(interceptor);
    }
}
