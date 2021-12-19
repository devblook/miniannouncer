package me.jonakls.miniannouncer.utils;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.intellij.lang.annotations.Subst;

import java.time.Duration;

public class MiniMessageUtil {

    public static Component toMiniMessage(String text) {
        return MiniMessage.miniMessage().parse(text);
    }

    public static void execute(Player player, String line) {
        String actionType = StringUtils.substringBetween(line, "[", "]").toUpperCase();

        switch (actionType) {
            default:
            case "MESSAGE":
                player.sendMessage(toMiniMessage(line.substring(9)));
                break;
            case "ACTIONBAR":
                player.sendActionBar(toMiniMessage(line.substring(11)));
                break;
            case "TITLE":
                String[] title = line.substring(7).split(";");
                player.showTitle(
                        Title.title(
                                toMiniMessage(title[0]),
                                toMiniMessage(title[1]),
                                Title.Times.of(
                                        Duration.ofSeconds(Long.parseLong(title[2])),
                                        Duration.ofSeconds(Long.parseLong(title[3])),
                                        Duration.ofSeconds(Long.parseLong(title[4]))
                                )
                        )
                );
                break;
            case "SOUND":
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
                break;
        }
    }

}
