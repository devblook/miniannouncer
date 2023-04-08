package me.jonakls.miniannouncer.velocity;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;

@Plugin(
        id = "miniannouncer",
        name = "MiniAnnouncer",
        version = "3.0",
        description = "A simple plugin to announce messages to the server."
)
public class MiniAnnouncerVelocity {

    private final PluginManager pluginManager;

    @Inject
    public MiniAnnouncerVelocity(Injector injector) {
        this.pluginManager = new PluginManager(injector);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        pluginManager.initialize();
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        pluginManager.shutdown();
    }
}
