package me.jonakls.fenixannouncer.managers;

import me.jonakls.fenixannouncer.FenixAnnouncer;
import me.jonakls.fenixannouncer.utils.ChatUtil;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.title.Title;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.intellij.lang.annotations.Subst;

import java.time.Duration;

public class ActionsManager {

    public FileConfiguration config;

    public ActionsManager(FenixAnnouncer plugin) {
        this.config = plugin.getConfig();
    }

    public void execute(Player player, String line) {
        if (line.startsWith("[MESSAGE]")) {
            player.sendMessage(ChatUtil.toMiniMessage(line.substring(9)));

        } else if (line.startsWith("[TITLE]")) {
            String[] title = line.substring(7).split(";");
            player.showTitle(
                    Title.title(
                            ChatUtil.toMiniMessage(title[0]),
                            ChatUtil.toMiniMessage(title[1]),
                            Title.Times.of(
                                    Duration.ofSeconds(Long.parseLong(title[2])),
                                    Duration.ofSeconds(Long.parseLong(title[3])),
                                    Duration.ofSeconds(Long.parseLong(title[4]))
                            )
                    )
            );
        } else if (line.startsWith("[ACTIONBAR]")) {
            player.sendActionBar(ChatUtil.toMiniMessage(line.substring(11)));

        } else if (line.startsWith("[SOUND]")) {
            @Subst("")
            String[] sound = line.substring(7).trim().split(";");
            player.playSound(
                    Sound.sound(
                            org.bukkit.Sound.valueOf(sound[0].trim()),
                            Sound.Source.NEUTRAL,
                            Integer.parseInt(sound[1]),
                            Integer.parseInt(sound[2])
                    )
            );

        } else {
            player.sendMessage(ChatUtil.toMiniMessage(line));
        }
    }

}
