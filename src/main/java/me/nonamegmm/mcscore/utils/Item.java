package me.nonamegmm.mcscore.utils;

import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class Item {
    public static void getItem(Player player) {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));

        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_glock");
            nbt.setString("GunFireMode", "SEMI");
            nbt.setInteger("GunCurrentAmmoCount", 20);
        });

        // 给予玩家物品
        player.getInventory().addItem(item);
    }
}
