package me.jonakls.miniannouncer.message;

import me.jonakls.miniannouncer.BukkitConfiguration;
import me.jonakls.miniannouncer.utils.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageHandler {

  private static final String BASE_PATH = "messages.";
  private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

  private final BukkitConfiguration config;

  private MessageHandler(BukkitConfiguration config) {
    this.config = config;
  }

  public static MessageHandler create(BukkitConfiguration config) {
    return new MessageHandler(config);
  }

  public Component intercept(CommandSender sender, String message) {
    final var pluginManager = Bukkit.getServer()
                                .getPluginManager();

    if (pluginManager.getPlugin("PlaceholderAPI") != null) {
      return MINI_MESSAGE.deserialize(message, MiniMessageUtil.papiResolver((Player) sender));
    }

    return MINI_MESSAGE.deserialize(message);
  }

  public Component getMessage(
    CommandSender sender, String path,
    Object... replacements
  ) {
    FileConfiguration configuration = config.get();
    String message = configuration.getString(BASE_PATH + path);

    if (message == null) {
      return Component.text(path);
    }

    return intercept(
      sender,
      String.format(message, replacements)
    );
  }

  public List<Component> getMessages(
    CommandSender sender, String path,
    Object... replacements
  ) {
    FileConfiguration configuration = config.get();
    List<String> messages = configuration.getStringList(BASE_PATH + path);
    List<Component> components = new ArrayList<>(List.of(Component.empty()));

    if (messages.isEmpty()) {
      return Collections.singletonList(Component.text(path));
    }

    for (String message : messages) {
      components.add(intercept(
        sender,
        String.format(message, replacements)
      ));
    }

    return components;
  }

  public void sendMessage(
    CommandSender sender, String path,
    Object... replacements
  ) {
    sender.sendMessage(getMessage(sender, path, replacements));
  }

  public void sendListMessage(
    CommandSender sender, String path,
    Object... replacements
  ) {
    getMessages(sender, path, replacements)
      .forEach(sender::sendMessage);
  }
}
