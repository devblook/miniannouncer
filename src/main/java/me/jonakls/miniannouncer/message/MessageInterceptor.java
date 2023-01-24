package me.jonakls.miniannouncer.message;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface MessageInterceptor {

    MessageInterceptor CHAT_COLOR_INTERCEPTOR = (sender, message) ->
            ChatColor.translateAlternateColorCodes('&', message);

    // MessageInterceptor MINI_MESSAGE_INTERCEPTOR = (sender, message) ->
    //         MiniMessageUtil.toMiniMessage(message);

    String intercept(CommandSender sender, String message);

}
