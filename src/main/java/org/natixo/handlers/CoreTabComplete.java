package org.natixo.handlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.natixo.highboxcore.HighBoxCore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoreTabComplete implements TabCompleter {

    private static final String[] COMMANDS = { "configure", "inventory", "reset", "villagers" };
    private HighBoxCore plugin;

    public CoreTabComplete(HighBoxCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!((Player) sender).hasPermission("core.commands")) {
            return null;
        }
        if (args[0].equals("inventory")) {
            return null;
        }

        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], Arrays.asList(COMMANDS), completions);
        return completions;
    }
}
