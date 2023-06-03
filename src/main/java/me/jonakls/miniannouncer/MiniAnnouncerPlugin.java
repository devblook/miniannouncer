package me.jonakls.miniannouncer;

import me.jonakls.miniannouncer.module.PluginModule;
import me.jonakls.miniannouncer.service.Service;
import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.inject.Injector;

import javax.inject.Inject;
import java.util.Set;

public final class MiniAnnouncerPlugin extends JavaPlugin {

  @Inject
  private Set<Service> services;

  @Override
  public void onLoad() {
    Injector.create(new PluginModule(this))
      .injectMembers(this);
  }

  @Override
  public void onEnable() {
    services.forEach(Service::start);
  }

  @Override
  public void onDisable() {
    services.forEach(Service::stop);
  }
}
