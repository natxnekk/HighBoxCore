package org.natixo.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.natixo.highboxcore.HighBoxCore;

public class Gamemode implements CommandExecutor {

    private HighBoxCore plugin;
    public Gamemode(HighBoxCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission("core.commands")) {
            player.sendMessage(plugin.getNoPermissionMessage());
            return true;
        }
        if (args.length > 0) {
            int mode;
            try {
                mode = Integer.parseInt(args[0]);
            } catch (Exception e) {
                //
                mode = -1;
            }
            switch (mode) {
                case 0:
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(ChatColor.GREEN + "CHANGED GAMEMODE TO SURVIVAL");
                    return true;
                case 1:
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(ChatColor.GREEN + "CHANGED GAMEMODE TO CREATIVE");
                    return true;
                case 2:
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(ChatColor.GREEN + "CHANGED GAMEMODE TO ADVENTURE");
                    return true;
                case 3:
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(ChatColor.GREEN + "CHANGED GAMEMODE TO SPECTATOR");
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }
}
