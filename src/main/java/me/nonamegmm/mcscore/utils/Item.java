package me.nonamegmm.mcscore.utils;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class Item {
    public static void getItem(Player player) {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("GunId", "mcs2:cs_glock");
        nbtItem.setString("GunFireMode", "SEMI");
        nbtItem.setInteger("GunCurrentAmmoCount", 20);

        // 给予玩家物品
        player.getInventory().addItem(item);
    }
}
