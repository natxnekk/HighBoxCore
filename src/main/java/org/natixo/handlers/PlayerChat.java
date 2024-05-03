package org.natixo.handlers;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.natixo.highboxcore.HighBoxCore;

import java.util.Collection;

public class PlayerChat implements Listener {

    private HighBoxCore plugin;
    private LuckPerms luckPerms;

    public PlayerChat(HighBoxCore plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
    }

    public static boolean isPlayerInGroup(Player player, String group) {
        return player.hasPermission("group." + group);
    }

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (luckPerms != null) {
            User user = luckPerms.getUserManager().getUser(player.getUniqueId());
            if (user != null) {
                String prefix = user.getCachedData().getMetaData().getPrefix();
                String playerName = PlainTextComponentSerializer.plainText().serialize(player.displayName());
                String message = event.getMessage();
                if (prefix != null) {
                    if (isPlayerInGroup(player, "gracz")) {
                        event.setFormat(ChatColor.translateAlternateColorCodes('&', prefix) + playerName + ChatColor.GRAY + " » " + ChatColor.RESET + message);
                    } else {
                        event.setFormat(ChatColor.translateAlternateColorCodes('&', prefix) + playerName + ChatColor.GRAY + " » " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', message));
                    }

                } else {
                    event.setFormat(ChatColor.DARK_GRAY + "[GRACZ] " + playerName + ChatColor.DARK_GRAY + " » " + ChatColor.RESET + message);
                }

            }
        }
    }

}
