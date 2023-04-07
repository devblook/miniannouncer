package me.jonakls.miniannouncer.velocity.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import me.jonakls.miniannouncer.core.configuration.YamlPluginConfiguration;
import me.jonakls.miniannouncer.core.configuration.sections.Configuration;

import java.nio.file.Path;

public class PluginModule extends AbstractModule {

    private final Path pluginDirectory;

    public PluginModule(Path pluginDirectory) {
        this.pluginDirectory = pluginDirectory;
    }

    @Singleton
    @Provides
    public YamlPluginConfiguration<Configuration> provideConfiguration(@DataDirectory Path pluginDirectory) {
        return YamlPluginConfiguration.create(pluginDirectory, Configuration.class, "config");
    }

    @Override
    protected void configure() {
        bind(PluginModule.class).toInstance(this);


    }
}
