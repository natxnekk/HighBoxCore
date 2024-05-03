package org.natixo.commands;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.natixo.highboxcore.HighBoxCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CoreCommand implements CommandExecutor {

    private HighBoxCore plugin;

    public CoreCommand(HighBoxCore plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!((Player) sender).hasPermission("core.commands")) {
            ((Player) sender).sendMessage(plugin.getNoPermissionMessage());
            return true;
        }
        if (args.length >= 1) {
            Player player = (Player) sender;
            switch (args[0]) {
                case "configure":
                    player.getInventory().clear();
                    ItemStack coalItem = new ItemStack(Material.COAL_ORE, 1);
                    ItemMeta configMeta = coalItem.getItemMeta();
                    configMeta.setUnbreakable(true);
                    configMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "config_item"), PersistentDataType.BOOLEAN, true);
                    configMeta.addEnchant(Enchantment.LUCK, 10, true);

                    coalItem.setItemMeta(configMeta);
                    ItemMeta coalMeta = coalItem.getItemMeta();
                    coalMeta.setDisplayName(ChatColor.DARK_GRAY + "SET COAL SPAWNER");
                    coalMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "configKey"), PersistentDataType.STRING, "core.blocks.coal");
                    coalItem.setItemMeta(coalMeta);

                    ItemStack woodItem = new ItemStack(Material.OAK_LOG, 1);
                    ItemMeta woodMeta = coalItem.getItemMeta();
                    woodMeta.setDisplayName(ChatColor.GOLD + "SET WOOD SPAWNER");
                    woodMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "configKey"), PersistentDataType.STRING, "core.blocks.wood");
                    woodItem.setItemMeta(woodMeta);

                    ItemStack ironItem = new ItemStack(Material.IRON_ORE, 1);
                    ItemMeta ironMeta = coalItem.getItemMeta();
                    ironMeta.setDisplayName(ChatColor.WHITE + "SET IRON SPAWNER");
                    ironMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "configKey"), PersistentDataType.STRING, "core.blocks.iron");
                    ironItem.setItemMeta(ironMeta);

                    ItemStack goldItem = new ItemStack(Material.GOLD_ORE, 1);
                    ItemMeta goldMeta = coalItem.getItemMeta();
                    goldMeta.setDisplayName(ChatColor.GOLD + "SET GOLD SPAWNER");
                    goldMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "configKey"), PersistentDataType.STRING, "core.blocks.gold");
                    goldItem.setItemMeta(goldMeta);

                    ItemStack emeraldItem = new ItemStack(Material.EMERALD_ORE, 1);
                    ItemMeta emeraldMeta = coalItem.getItemMeta();
                    emeraldMeta.setDisplayName(ChatColor.GREEN + "SET EMERALD SPAWNER");
                    emeraldMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "configKey"), PersistentDataType.STRING, "core.blocks.emerald");
                    emeraldItem.setItemMeta(emeraldMeta);

                    ItemStack diamondItem = new ItemStack(Material.DIAMOND_ORE, 1);
                    ItemMeta diamondMeta = coalItem.getItemMeta();
                    diamondMeta.setDisplayName(ChatColor.BLUE + "SET DIAMOND SPAWNER");
                    diamondMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "configKey"), PersistentDataType.STRING, "core.blocks.diamond");
                    diamondItem.setItemMeta(diamondMeta);

                    ItemStack netheriteItem = new ItemStack(Material.NETHERITE_BLOCK, 1);
                    ItemMeta netheriteMeta = coalItem.getItemMeta();
                    netheriteMeta.setDisplayName(ChatColor.DARK_GRAY + "SET NETHERITE SPAWNER");
                    netheriteMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "configKey"), PersistentDataType.STRING, "core.blocks.netherite");
                    netheriteItem.setItemMeta(netheriteMeta);

                    ItemStack copperItem = new ItemStack(Material.COPPER_BLOCK, 1);
                    ItemMeta copperMeta = coalItem.getItemMeta();
                    copperMeta.setDisplayName(ChatColor.GOLD + "SET COPPER SPAWNER");
                    copperMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "configKey"), PersistentDataType.STRING, "core.blocks.copper");
                    copperItem.setItemMeta(copperMeta);

                    player.getInventory().setItem(0, coalItem);
                    player.getInventory().setItem(1, woodItem);
                    player.getInventory().setItem(2, ironItem);
                    player.getInventory().setItem(3, goldItem);
                    player.getInventory().setItem(4, emeraldItem);
                    player.getInventory().setItem(5, diamondItem);
                    player.getInventory().setItem(6, netheriteItem);
                    player.getInventory().setItem(7, copperItem);
                    return true;
                case "reset":
                    ConfigurationSection section = plugin.getConfig().getConfigurationSection("core.blocks");
                    if (section != null) {
                        Set<String> identifiers = section.getKeys(false);
                        for (String identifier : identifiers) {
                            if (plugin.getConfig().getLocation("core.blocks." + identifier + ".pos1") != null && plugin.getConfig().getLocation("core.blocks." + identifier + ".pos2") != null) {
                                Location loc1 = plugin.getConfig().getLocation("core.blocks." + identifier + ".pos1");
                                Location loc2 = plugin.getConfig().getLocation("core.blocks." + identifier + ".pos2");
                                List<Material> materials = new ArrayList<Material>();
                                switch (identifier) {
                                    case "coal":
                                        materials.add(Material.COAL_ORE);
                                        materials.add(Material.COAL_BLOCK);
                                        break;
                                    case "iron":
                                        materials.add(Material.IRON_ORE);
                                        materials.add(Material.IRON_BLOCK);
                                        break;
                                    case "wood":
                                        materials.add(Material.OAK_LOG);
                                        materials.add(Material.OAK_WOOD);
                                        materials.add(Material.SPRUCE_LOG);
                                        break;
                                    case "gold":
                                        materials.add(Material.GOLD_ORE);
                                        materials.add(Material.GOLD_BLOCK);
                                        break;
                                    case "emerald":
                                        materials.add(Material.EMERALD_ORE);
                                        materials.add(Material.EMERALD_BLOCK);
                                        break;
                                    case "diamond":
                                        materials.add(Material.DIAMOND_ORE);
                                        materials.add(Material.DIAMOND_BLOCK);
                                        break;
                                    case "netherite":
                                        materials.add(Material.NETHERITE_BLOCK);
                                        break;
                                    case "copper":
                                        materials.add(Material.COPPER_BLOCK);
                                        break;
                                    default:
                                        break;
                                }
                                if (!materials.isEmpty() && loc1 != null && loc2 != null) {
                                    int topBlockX = (Math.max(loc1.getBlockX(), loc2.getBlockX()));
                                    int bottomBlockX = (Math.min(loc1.getBlockX(), loc2.getBlockX()));

                                    int topBlockY = (Math.max(loc1.getBlockY(), loc2.getBlockY()));
                                    int bottomBlockY = (Math.min(loc1.getBlockY(), loc2.getBlockY()));

                                    int topBlockZ = (Math.max(loc1.getBlockZ(), loc2.getBlockZ()));
                                    int bottomBlockZ = (Math.min(loc1.getBlockZ(), loc2.getBlockZ()));

                                    //Loop through every block in 2 points and set to random blocks from materials array
                                    for(int x = bottomBlockX; x <= topBlockX; x++)
                                    {
                                        for(int z = bottomBlockZ; z <= topBlockZ; z++)
                                        {
                                            for(int y = bottomBlockY; y <= topBlockY; y++)
                                            {
                                                Block block = loc1.getWorld().getBlockAt(x, y, z);
                                                block.setType(materials.get(new Random().nextInt(materials.size())));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "inventory":
                    if (args.length >= 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            player.openInventory(target.getInventory());
                            return true;
                        }
                    }
                    player.sendMessage(plugin.getWrongUsageMessage() + "/core inventory <player>");
                    break;

                case "villagers":
                    player.getInventory().clear();
                    ItemStack villagerItem = new ItemStack(Material.VILLAGER_SPAWN_EGG, 1);
                    ItemMeta villagerMeta = villagerItem.getItemMeta();
                    villagerMeta.setUnbreakable(true);
                    villagerMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "villager_item"), PersistentDataType.BOOLEAN, true);
                    villagerMeta.addEnchant(Enchantment.LUCK, 10, true);
                    villagerMeta.setDisplayName("SPAWN VILLAGER (RightClick on AIR)");
                    villagerItem.setItemMeta(villagerMeta);

                    player.getInventory().setItem(0, villagerItem);

                    break;
                default:
                    player.sendMessage(plugin.getWrongUsageMessage() + "/core inventory/reset/configure");
                    break;
            }
        }

        return true;
    }
}
