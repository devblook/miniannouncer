package me.jonakls.miniannouncer.paper.module.submodules;

import me.jonakls.miniannouncer.core.configuration.YamlPluginConfiguration;
import me.jonakls.miniannouncer.core.configuration.sections.Configuration;
import me.jonakls.miniannouncer.paper.MiniAnnouncerPaper;
import org.spongepowered.configurate.CommentedConfigurationNode;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;

import javax.inject.Singleton;

public class FileModule extends AbstractModule {

    @Singleton
    @Provides
    public YamlPluginConfiguration<Configuration> configuration(MiniAnnouncerPaper plugin) {
        return YamlPluginConfiguration.create(
                plugin.getDataFolder().toPath(),
                Configuration.class,
                "config"
        );
    }

    @Singleton
    @Provides
    public Configuration configuration(YamlPluginConfiguration<Configuration> config) {
        return config.get();
    }

    @Singleton
    @Provides
    public CommentedConfigurationNode configurationNode(YamlPluginConfiguration<Configuration> config) {
        return config.getNode();
    }
}
