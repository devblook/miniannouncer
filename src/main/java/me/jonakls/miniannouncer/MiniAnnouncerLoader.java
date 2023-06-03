package me.jonakls.miniannouncer;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public class MiniAnnouncerLoader implements PluginLoader {

  @Override
  public void classloader(@NotNull final PluginClasspathBuilder classpathBuilder) {
    final var resolver = new MavenLibraryResolver();

    final var unnamedRepository = new RemoteRepository.Builder(
      "unnamed-public",
      "default",
      "https://repo.unnamed.team/repository/unnamed-public/").build();

    final var triumphRepository = new RemoteRepository.Builder(
      "reposilite-repository-snapshots",
      "default",
      "https://repo.triumphteam.dev/snapshots/").build();

    final var inject = new Dependency(new DefaultArtifact("team.unnamed:inject:1.0.1"), null);
    final var command = new Dependency(new DefaultArtifact("dev.triumphteam:triumph-cmd-bukkit:2.0.0-SNAPSHOT"), null);

    resolver.addRepository(unnamedRepository);
    resolver.addRepository(triumphRepository);
    resolver.addDependency(inject);
    resolver.addDependency(command);
    classpathBuilder.addLibrary(resolver);
  }
}
