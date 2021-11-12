package me.jonakls.miniannouncer.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;

public class ChatUtil {

    public static Component toMiniMessage(String text) {
        return MiniMessage.miniMessage().parse(text);
    }

    public static String toLegacyColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
