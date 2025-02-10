package me.nonamegmm.mcscore;

import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class Item {
    public static void getGlock(Player player) {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_glock");
            nbt.setString("GunFireMode", "SEMI");
            nbt.setInteger("GunCurrentAmmoCount", 20);
            nbt.setByte("HasBulletInBarrel", (byte) 1);
        });
        player.getInventory().addItem(item);
    }
    public static void get9mm(Player player) {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_AMMO"), 60);
        NBT.modify(item, nbt -> {
            nbt.setString("AmmoId","tacz:9mm");
        });
        player.getInventory().addItem(item);
    }
}
