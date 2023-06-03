package me.jonakls.miniannouncer.service;

import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.bukkit.message.BukkitMessageKey;
import dev.triumphteam.cmd.core.BaseCommand;
import me.jonakls.miniannouncer.message.MessageHandler;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.util.Set;

public class CommandService implements Service {

  @Inject
  private BukkitCommandManager<CommandSender> commandManager;

  @Inject
  private Set<BaseCommand> commands;

  @Inject
  private MessageHandler messageHandler;

  @Override
  public void start() {
    commandManager.registerMessage(BukkitMessageKey.NO_PERMISSION, (sender, context) ->
                                                                     messageHandler.sendMessage(sender, "no-permission")
    );

    commands.forEach(commandManager::registerCommand);
  }

  @Override
  public void stop() {
    commands.forEach(commandManager::unregisterCommand);
  }
}
