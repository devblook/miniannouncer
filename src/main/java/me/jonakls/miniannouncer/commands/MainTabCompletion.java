package me.jonakls.miniannouncer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainTabCompletion implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command,
                                                @NotNull String s, @NotNull String[] strings) {

        if (strings.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("enable");
            list.add("disable");
            list.add("info");
            list.add("reload");
            list.add("info");
            list.add("help");

            return list;
        }

        return null;
    }
}
