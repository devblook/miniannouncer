package me.jonakls.miniannouncer.module;

import me.jonakls.miniannouncer.BukkitConfiguration;
import me.jonakls.miniannouncer.MiniAnnouncerPlugin;
import me.jonakls.miniannouncer.announce.AnnounceService;
import me.jonakls.miniannouncer.announce.AnnouncementManager;
import me.jonakls.miniannouncer.message.MessageHandler;
import me.jonakls.miniannouncer.module.submodules.CommandModule;
import me.jonakls.miniannouncer.service.CommandService;
import me.jonakls.miniannouncer.service.Service;
import org.slf4j.Logger;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;

import javax.inject.Singleton;

public class PluginModule extends AbstractModule {

  private final MiniAnnouncerPlugin plugin;

  public PluginModule(MiniAnnouncerPlugin plugin) {
    this.plugin = plugin;
  }

  @Singleton
  @Provides
  public Logger logger(MiniAnnouncerPlugin plugin) {
    return plugin.getSLF4JLogger();
  }

  @Singleton
  @Provides
  public BukkitConfiguration configurationProvides(MiniAnnouncerPlugin plugin) {
    return BukkitConfiguration.of(plugin, "config");
  }

  @Singleton
  @Provides
  public MessageHandler messageHandlerProvides(BukkitConfiguration config) {
    return MessageHandler.create(config);
  }

  @Override
  protected void configure() {
    super.bind(MiniAnnouncerPlugin.class)
      .toInstance(plugin);

    super.multibind(Service.class)
      .asSet()
      .to(AnnounceService.class)
      .to(CommandService.class)
      .singleton();

    super.bind(AnnouncementManager.class)
      .singleton();

    super.install(new CommandModule(plugin));
  }
}
