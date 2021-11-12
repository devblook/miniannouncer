package me.jonakls.miniannouncer.managers;

import me.jonakls.miniannouncer.MiniAnnouncer;
import me.jonakls.miniannouncer.utils.ChatUtil;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.title.Title;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.intellij.lang.annotations.Subst;

import java.time.Duration;

public class ActionsManager {

    // TODO FIX-THIS, config var is not being used!
    public FileConfiguration config;

    public ActionsManager(MiniAnnouncer plugin) {
        this.config = plugin.getConfig();
    }

    public void execute(Player player, String line) {
        String actionType = StringUtils.substringBetween(line, "[", "]").toUpperCase();

        switch (actionType) {
            default:
            case "MESSAGE":
                player.sendMessage(ChatUtil.toMiniMessage(line.substring(9)));
                break;
            case "ACTIONBAR":
                player.sendActionBar(ChatUtil.toMiniMessage(line.substring(11)));
                break;
            case "TITLE":
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
