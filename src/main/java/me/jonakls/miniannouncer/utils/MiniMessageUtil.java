package me.jonakls.miniannouncer.utils;

import me.jonakls.miniannouncer.message.MessageHandler;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.intellij.lang.annotations.Subst;

import java.time.Duration;
import java.util.Locale;

public class MiniMessageUtil {

    public static Component toMiniMessage(String text) {
        return MiniMessage.miniMessage().deserialize(text);
    }

    public static void execute(Player player, String line, MessageHandler messageHandler) {
        String actionType = line.substring(1, line.indexOf("]"))
                .toUpperCase(Locale.ROOT)
                .replace("[", "");

        switch (actionType) {
            case "MESSAGE" -> player.sendMessage(messageHandler.applyInterceptors(player, line.substring(9)));
            case "ACTIONBAR" -> player.sendActionBar(messageHandler.applyInterceptors(player, line.substring(11)));
            case "TITLE" -> {
                String[] titleArray = line.substring(7).split(";");

                Title.Times times = Title.Times.times(
                        Duration.ofSeconds(Long.parseLong(titleArray[2])),
                        Duration.ofSeconds(Long.parseLong(titleArray[3])),
                        Duration.ofSeconds(Long.parseLong(titleArray[4]))
                );

                Title title = Title.title(
                        messageHandler.applyInterceptors(player, titleArray[0]),
                        messageHandler.applyInterceptors(player, titleArray[1]),
                        times
                );

                player.showTitle(title);
            }
            case "SOUND" -> {
                @Subst("")
                String[] sound = line.substring(7).trim().split(";");
                player.playSound(
                        Sound.sound(
                                org.bukkit.Sound.valueOf(sound[0].trim()),
                                Sound.Source.NEUTRAL,
                                Float.parseFloat(sound[1]),
                                Float.parseFloat(sound[2])
                        )
                );
            }
            default -> {
                player.sendMessage(messageHandler.applyInterceptors(player, line));
            }
        }
    }

}
