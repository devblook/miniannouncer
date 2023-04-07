package me.jonakls.miniannouncer.core.configuration;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

public class YamlPluginConfiguration<T> {

    private final AtomicReference<T> configuration;
    private final Class<T> clazz;
    private final YamlConfigurationLoader loader;

    private YamlPluginConfiguration(
            Class<T> clazz,
            YamlConfigurationLoader loader,
            T configuration
    ) {
        this.configuration = new AtomicReference<>(configuration);
        this.clazz = clazz;
        this.loader = loader;
    }

    public static <T> YamlPluginConfiguration<T> create(
            Path path,
            Class<T> clazz,
            String fileName
    ) {
        YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .defaultOptions(options -> options
                        .shouldCopyDefaults(true)
                        .header("MiniAnnouncer Configuration\nBy Jonakls\nVersion: 3.0.0")
                )
                .path(path.resolve(fileName + ".yml"))
                .nodeStyle(NodeStyle.BLOCK)
                .build();
        try {
            CommentedConfigurationNode node = loader.load();
            T defaultConfig = node.get(clazz);
            node.set(clazz, defaultConfig);
            loader.save(node);
            return new YamlPluginConfiguration<>(clazz, loader, defaultConfig);
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void reload() {
        try {
            CommentedConfigurationNode node = loader.load();
            T newConfig = node.get(clazz);
            node.set(clazz, newConfig);
            loader.save(node);
            configuration.set(newConfig);
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public T get() {
        return configuration.get();
    }

    @Nullable
    public CommentedConfigurationNode getNode() {
        try {
            return loader.load();
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return null;
        }
    }
}
