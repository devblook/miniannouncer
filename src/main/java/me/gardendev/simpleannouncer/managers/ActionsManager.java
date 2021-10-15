package me.gardendev.simpleannouncer.managers;

import me.gardendev.simpleannouncer.SimpleAnnouncer;
import me.gardendev.simpleannouncer.utils.ChatUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.title.Title;
import org.bukkit.configuration.file.FileConfiguration;
import org.intellij.lang.annotations.Subst;

import java.time.Duration;

public class ActionsManager {

    public FileConfiguration config;

    public ActionsManager(SimpleAnnouncer plugin) {
        this.config = plugin.getConfig();
    }

    public void execute(Audience audience, String line) {
        if (line.startsWith("[MESSAGE]")) {
            audience.sendMessage(ChatUtil.toMiniMessage(line.substring(9)));

        } else {
            if (line.startsWith("[TITLE]")) {
                String[] title = line.substring(7).split(";");
                audience.showTitle(
                        Title.title(
                                ChatUtil.toMiniMessage(title[0]),
                                ChatUtil.toMiniMessage(title[1]),
                                Title.Times.of(
                                        Duration.ofMillis(Long.parseLong(title[2])),
                                        Duration.ofMillis(Long.parseLong(title[3])),
                                        Duration.ofMillis(Long.parseLong(title[4]))
                                )
                        )
                );
                audience.clearTitle();

            } else if (line.startsWith("[ACTIONBAR]")) {
                audience.sendActionBar(ChatUtil.toMiniMessage(line.substring(11)));

            } else if (line.startsWith("[SOUND]")) {
                @Subst("")
                String[] sound = line.substring(7).trim().split(";");
                audience.playSound(
                        Sound.sound(
                                org.bukkit.Sound.valueOf(sound[0].trim()),
                                Sound.Source.NEUTRAL,
                                Integer.parseInt(sound[1]),
                                Integer.parseInt(sound[2])
                        )
                );

            } else {
                audience.sendMessage(ChatUtil.toMiniMessage(line));
            }
        }
    }

}
