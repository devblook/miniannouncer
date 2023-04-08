package me.jonakls.miniannouncer.velocity;


import com.google.inject.Inject;
import com.google.inject.Injector;
import me.jonakls.miniannouncer.api.Service;
import me.jonakls.miniannouncer.velocity.module.PluginModule;

import java.util.Set;

public class PluginManager {

    private Injector injector;

    @Inject
    private Set<Service> services;

    public PluginManager(Injector injector) {
        this.injector = injector;
    }

    public void initialize() {
        this.injector = injector.createChildInjector(new PluginModule());
        this.injector.injectMembers(this);

        services.forEach(Service::start);
    }

    public void shutdown() {
        services.forEach(Service::stop);
    }
}
