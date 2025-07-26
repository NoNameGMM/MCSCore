package me.nonamegmm.mcscore;

import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class Items {

    public static ItemStack getGlock() {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_glock");
            nbt.setString("GunFireMode", "SEMI");
            nbt.setInteger("GunCurrentAmmoCount", 20);
            nbt.setByte("HasBulletInBarrel", (byte) 1);
        });
        return item;
    }

    public static ItemStack getM4A1S() {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_m4a1s");
            nbt.setString("GunFireMode", "AUTO");
            nbt.setInteger("GunCurrentAmmoCount", 20);
            nbt.setByte("HasBulletInBarrel", (byte) 1);
        });
        return item;
    }

    public static ItemStack getAWP() {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_awp");
            nbt.setString("GunFireMode", "SEMI");
            nbt.setInteger("GunCurrentAmmoCount", 5);
            nbt.setByte("HasBulletInBarrel", (byte) 1);
        });
        return item;
    }

    public static ItemStack getAK47() {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_ak");
            nbt.setString("GunFireMode", "AUTO");
            nbt.setInteger("GunCurrentAmmoCount",30);
            nbt.setByte("HasBulletInBarrel", (byte) 1);
        });
        return item;
    }

    public static ItemStack getUSP() {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_usp");
            nbt.setString("GunFireMode", "SEMI");
            nbt.setInteger("GunCurrentAmmoCount", 12);
            nbt.setByte("HasBulletInBarrel", (byte) 1);
        });
        return item;
    }

    public static ItemStack getT_knife() {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_t_knife");
            nbt.setString("GunFireMode", "SEMI");
            nbt.setInteger("GunCurrentAmmoCount", 1);
            nbt.setByte("HasBulletInBarrel", (byte) 1);
        });
        return item;
    }

    public static ItemStack getCT_knife() {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_ct_knife");
            nbt.setString("GunFireMode", "SEMI");
            nbt.setInteger("GunCurrentAmmoCount", 1);
            nbt.setByte("HasBulletInBarrel", (byte) 1);
        });
        return item;
    }

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

    public static void getM4A1S(Player player) {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_m4a1s");
            nbt.setString("GunFireMode", "AUTO");
            nbt.setInteger("GunCurrentAmmoCount", 20);
            nbt.setByte("HasBulletInBarrel", (byte) 1);
        });
        player.getInventory().addItem(item);
    }

    public static void getAWP(Player player) {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_awp");
            nbt.setString("GunFireMode", "SEMI");
            nbt.setInteger("GunCurrentAmmoCount", 5);
            nbt.setByte("HasBulletInBarrel", (byte) 1);
        });
        player.getInventory().addItem(item);
    }

    public static void getAK47(Player player) {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_ak");
            nbt.setString("GunFireMode", "AUTO");
            nbt.setInteger("GunCurrentAmmoCount",30);
            nbt.setByte("HasBulletInBarrel", (byte) 1);
        });
        player.getInventory().addItem(item);
    }

    public static void getUSP(Player player) {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_usp");
            nbt.setString("GunFireMode", "SEMI");
            nbt.setInteger("GunCurrentAmmoCount", 12);
            nbt.setByte("HasBulletInBarrel", (byte) 1);
        });
        player.getInventory().addItem(item);
    }

    public static void getT_knife(Player player) {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_t_knife");
            nbt.setString("GunFireMode", "SEMI");
            nbt.setInteger("GunCurrentAmmoCount", 1);
            nbt.setByte("HasBulletInBarrel", (byte) 1);
        });
        player.getInventory().addItem(item);
    }

    public static void getCT_knife(Player player) {
        ItemStack item = new ItemStack(Material.valueOf("TACZ_MODERN_KINETIC_GUN"));
        NBT.modify(item, nbt -> {
            nbt.setString("GunId", "mcs2:cs_ct_knife");
            nbt.setString("GunFireMode", "SEMI");
            nbt.setInteger("GunCurrentAmmoCount", 1);
            nbt.setByte("HasBulletInBarrel", (byte) 1);
        });
        player.getInventory().addItem(item);
    }
}
