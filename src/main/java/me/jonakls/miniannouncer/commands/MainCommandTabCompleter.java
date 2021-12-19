package me.jonakls.miniannouncer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainCommandTabCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command,
                                                String s, String[] strings) {
        if (sender.hasPermission("miniannouncer.commands")) {
            List<String> list = new ArrayList<>();
            if (strings.length == 1) {
                list.add("reload");
            }

            return list;
        }

        return Collections.emptyList();
    }

}
