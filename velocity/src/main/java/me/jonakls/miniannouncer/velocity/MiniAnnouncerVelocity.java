package me.jonakls.miniannouncer.velocity;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import me.jonakls.miniannouncer.api.Service;
import me.jonakls.miniannouncer.velocity.module.PluginModule;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Set;

@Plugin(
        id = "miniannouncer",
        name = "MiniAnnouncer",
        version = "3.0",
        description = "A simple plugin to announce messages to the server."
)
public class MiniAnnouncerVelocity {

    private final Injector injector;

    @Inject
    public MiniAnnouncerVelocity(@DataDirectory Path pluginPath, Injector injector) {
        this.injector = injector.createChildInjector(new PluginModule(pluginPath));
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Service service = injector.getInstance(Service.class);
        service.start();
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        Service service = injector.getInstance(Service.class);
        service.stop();
    }
}
