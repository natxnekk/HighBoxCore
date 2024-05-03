package org.natixo.highboxcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.natixo.commands.CoreCommand;
import org.natixo.commands.Gamemode;
import org.natixo.handlers.ConfigUse;
import org.natixo.handlers.CoreTabComplete;
import org.natixo.handlers.PlayerChat;

import java.util.ArrayList;
import java.util.List;

public final class HighBoxCore extends JavaPlugin {

    private final String wrongUsageMessage = ChatColor.RED + "Uzycie: ";
    private final String noPermissionMessage = ChatColor.RED + "Nie ma takiej komendy!";

    public String getNoPermissionMessage() {
        return noPermissionMessage;
    }

    public String getWrongUsageMessage() {
        return wrongUsageMessage;
    }

    public ItemStack getCoinItem(int amount) {
        ItemStack coin = new ItemStack(Material.SUNFLOWER, amount);
        ItemMeta coinMeta = coin.getItemMeta();
        coinMeta.setDisplayName(ChatColor.GOLD + "COIN");
        coinMeta.setUnbreakable(true);
        coinMeta.getPersistentDataContainer().set(new NamespacedKey(this, "coinItem"), PersistentDataType.BOOLEAN, true);
        List<String> lore = new ArrayList<>();
        lore.add("Coin used to buy items from villagers");
        coinMeta.setLore(lore);
        coin.setItemMeta(coinMeta);
        return coin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("HighBoxCore Plugin Started");
        this.getCommand("gm").setExecutor(new Gamemode(this));
        this.getCommand("core").setExecutor(new CoreCommand(this));
        this.getCommand("core").setTabCompleter(new CoreTabComplete(this));

        new ConfigUse(this);
        new PlayerChat(this);
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.saveConfig();
    }
}
