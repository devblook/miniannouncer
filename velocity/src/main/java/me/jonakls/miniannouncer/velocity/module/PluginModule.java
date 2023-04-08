package me.jonakls.miniannouncer.velocity.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import me.jonakls.miniannouncer.api.Service;
import me.jonakls.miniannouncer.core.configuration.YamlPluginConfiguration;
import me.jonakls.miniannouncer.core.configuration.sections.Configuration;
import me.jonakls.miniannouncer.velocity.service.AnnouncerService;

import java.nio.file.Path;

public class PluginModule extends AbstractModule {

    @Singleton
    @Provides
    public YamlPluginConfiguration<Configuration> provideConfiguration(@DataDirectory Path pluginDirectory) {
        return YamlPluginConfiguration.create(pluginDirectory, Configuration.class, "config");
    }

    @Override
    protected void configure() {
        Multibinder<Service> services = Multibinder.newSetBinder(binder(), Service.class);
        services.addBinding().to(AnnouncerService.class);
    }
}
