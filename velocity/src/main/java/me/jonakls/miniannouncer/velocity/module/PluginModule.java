package me.jonakls.miniannouncer.velocity.module;

import com.google.inject.AbstractModule;

import java.nio.file.Path;

public class PluginModule extends AbstractModule {

    private final Path pluginDirectory;

    public PluginModule(Path pluginDirectory) {
        this.pluginDirectory = pluginDirectory;
    }

    @Override
    protected void configure() {
        bind(PluginModule.class).toInstance(this);
    }
}
