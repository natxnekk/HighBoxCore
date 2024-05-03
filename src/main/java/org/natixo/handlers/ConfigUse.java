package org.natixo.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.natixo.highboxcore.HighBoxCore;

import java.util.ArrayList;
import java.util.List;

public class ConfigUse implements Listener {
    private HighBoxCore plugin;

    public ConfigUse(HighBoxCore plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getEquipment().getItemInMainHand();
        if (item.hasItemMeta()) {
            if (item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "configKey")) && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "config_item"))) {
                event.setCancelled(true);
                String configKey = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "configKey"), PersistentDataType.STRING);
                plugin.getConfig().set(configKey + ".pos1", event.getBlock().getLocation());
                Bukkit.getLogger().info(event.getBlock().getLocation().toString());
                plugin.saveConfig();
            }
        }
    }

    public void OpenVillagerInventory(Player player) {
        if (!player.hasPermission("core.villagers")) {
            return;
        }
        Inventory villagerInventory = Bukkit.createInventory(null, 9, ChatColor.RED + "Choose Villager");
        ItemStack pvpItem = new ItemStack(Material.ZOMBIE_SPAWN_EGG, 1);
        pvpItem.getItemMeta().setDisplayName("PVP Villager");

        ItemStack gearItem = new ItemStack(Material.ZOMBIE_SPAWN_EGG, 1);
        gearItem.getItemMeta().setDisplayName("GEAR Villager");

        villagerInventory.setItem(0, pvpItem);
        villagerInventory.setItem(0, gearItem);

        player.openInventory(villagerInventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!player.hasPermission("core.villagers")) {
            return;
        }
        if (event.getView().getTitle().equals(ChatColor.RED + "Choose Villager") && event.getClickedInventory() != null) {
            ItemStack clickedItem = event.getClickedInventory().getItem(event.getSlot());
            if (clickedItem != null) {
                if (clickedItem.hasItemMeta()) {
                    if (!clickedItem.getType().equals(Material.AIR)) {
                        event.setCancelled(true);
                        String villagerName = "";
                        List<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>();
                        switch (event.getSlot()) {
                            case 0:
                                villagerName = "PVP";
                                MerchantRecipe recipe1 = new MerchantRecipe(new ItemStack(Material.DIAMOND_PICKAXE), 999999999);
                                recipe1.addIngredient(plugin.getCoinItem(10));
                                recipes.add(recipe1);
                                break;
                            case 1:
                                villagerName = "GEAR";
                                break;
                        }

                        if (!villagerName.isEmpty() && !recipes.isEmpty()) {
                             Villager villager = player.getLocation().getWorld().spawn(player.getLocation(), Villager.class);
                             villager.setAI(false);
                             villager.setSilent(true);
                             villager.setInvulnerable(true);
                             villager.setCollidable(false);
                             villager.setCanPickupItems(false);
                             villager.setGravity(false);
                             villager.setCustomNameVisible(true);
                             villager.setCustomName(villagerName);
                             villager.teleport(player.getLocation());
                             villager.lookAt(player);
                             villager.setRecipes(recipes);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getEquipment().getItemInMainHand();
        if (item.getItemMeta() != null) {
            if (item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "villager_item"))) {
                event.setCancelled(true);
                OpenVillagerInventory(player);
                return;
            }
            if (event.getClickedBlock() != null) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "configKey")) && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "config_item"))) {
                        event.setCancelled(true);
                        String configKey = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "configKey"), PersistentDataType.STRING);
                        plugin.getConfig().set(configKey + ".pos2", event.getClickedBlock().getLocation());
                        plugin.saveConfig();
                    }
                }
            }
        }
    }
}
