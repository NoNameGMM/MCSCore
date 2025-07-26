package me.nonamegmm.mcscore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class PickItem {
    public static void pickupWithAnimation(Player player, Item drop) {
        if (drop == null || drop.isDead()) return;
        ItemStack stack = drop.getItemStack();
        Log.info("尝试拾取物品");
        HashMap<Integer, ItemStack> left = player.getInventory().addItem(stack.clone());
        if (!left.isEmpty()) {
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.4f, 1.0f);
            return;
        }
        Bukkit.getScheduler().runTaskLater(JavaPlugin.getProvidingPlugin(PickItem.class),
                drop::remove, 1L);
    }
}
