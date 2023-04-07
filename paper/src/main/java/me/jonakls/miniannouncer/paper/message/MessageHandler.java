package me.jonakls.miniannouncer.paper.message;

import me.jonakls.miniannouncer.paper.utils.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessageHandler {

    private final Set<MessageInterceptor> interceptors;

    public MessageHandler() {
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

    public Component getMessage(CommandSender sender, String message,
                             Object... replacements) {

        if (message == null) {
            return Component.empty();
        }

        return applyInterceptors(
                sender,
                String.format(message, replacements)
        );
    }

    public List<Component> getMessages(CommandSender sender, List<String> collection,
                                    Object... replacements) {
        List<Component> components = new ArrayList<>(List.of(Component.empty()));

        if (collection.isEmpty()) {
            return Collections.singletonList(Component.text(Arrays.toString(collection.toArray())));
        }

        for (String message : collection) {
            components.add(applyInterceptors(
                    sender,
                    String.format(message, replacements)
            ));
        }

        return components;
    }

    public void sendMessage(CommandSender sender, String message,
                            Object... replacements) {
        sender.sendMessage(getMessage(sender, message, replacements));
    }

    public void sendListMessage(CommandSender sender, List<String> path,
                             Object... replacements) {
        getMessages(sender, path, replacements)
                .forEach(sender::sendMessage);
    }

    public void addInterceptor(MessageInterceptor interceptor) {
        interceptors.add(interceptor);
    }
}
