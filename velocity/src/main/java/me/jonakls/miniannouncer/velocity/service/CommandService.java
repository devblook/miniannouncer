package me.jonakls.miniannouncer.velocity.service;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.VelocityCommandManager;
import com.google.inject.Inject;
import me.jonakls.miniannouncer.api.Service;

import java.util.Set;

public class CommandService implements Service {

    @Inject
    private Set<BaseCommand> commands;

    @Inject
    private VelocityCommandManager commandManager;

    @Override
    public void start() {
        commands.forEach(commandManager::registerCommand);
    }

    @Override
    public void stop() {
        commands.forEach(commandManager::unregisterCommand);
    }
}
