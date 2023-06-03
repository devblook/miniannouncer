package me.jonakls.miniannouncer.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jonakls.miniannouncer.message.MessageHandler;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Locale;

public class MiniMessageUtil {

  public static TagResolver papiResolver(final @NotNull Player player) {
    return TagResolver.resolver("papi", (argumentQueue, context) -> {
      // Get the string placeholder that they want to use.
      final String papiPlaceholder = argumentQueue.popOr("papi tag requires an argument").value();

      // Then get PAPI to parse the placeholder for the given player.
      final String parsedPlaceholder = PlaceholderAPI.setPlaceholders(player, '%' + papiPlaceholder + '%');

      // We need to turn this ugly legacy string into a nice component.
      final Component componentPlaceholder = LegacyComponentSerializer.legacyAmpersand().deserialize(parsedPlaceholder);

      // Finally, return the tag instance to insert the placeholder!
      return Tag.selfClosingInserting(componentPlaceholder);
    });
  }

  public static void execute(Player player, String line, MessageHandler messageHandler) {
    String actionType = line.substring(1, line.indexOf("]"))
                          .toUpperCase(Locale.ROOT)
                          .replace("[", "");

    switch (actionType) {
      case "MESSAGE" -> player.sendMessage(messageHandler.intercept(player, line.substring(9)));
      case "ACTIONBAR" -> player.sendActionBar(messageHandler.intercept(player, line.substring(11)));
      case "TITLE" -> {
        String[] titleArray = line.substring(7)
                                .split(";");

        Title.Times times = Title.Times.times(
          Duration.ofSeconds(Long.parseLong(titleArray[2])),
          Duration.ofSeconds(Long.parseLong(titleArray[3])),
          Duration.ofSeconds(Long.parseLong(titleArray[4])));

        Title title = Title.title(
          messageHandler.intercept(player, titleArray[0]),
          messageHandler.intercept(player, titleArray[1]),
          times);

        player.showTitle(title);
      }
      case "SOUND" -> {
        @Subst("") String[] sound = line.substring(7)
                                      .trim()
                                      .split(";");
        player.playSound(Sound.sound(
          org.bukkit.Sound.valueOf(sound[0].trim()),
          Sound.Source.NEUTRAL,
          Float.parseFloat(sound[1]),
          Float.parseFloat(sound[2])));
      }
      default -> {
        player.sendMessage(messageHandler.intercept(player, line));
      }
    }
  }
}
