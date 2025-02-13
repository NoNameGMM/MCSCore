package me.nonamegmm.mcscore;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menu {
    public static void openMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "购买");
        ItemStack button1 = Item.getGlock();
        ItemMeta button1Meta = button1.getItemMeta();
        button1Meta.setUnbreakable(true);
        button1.setItemMeta(button1Meta);
        ItemStack button2 = Item.getAK47();
        ItemMeta button2Meta = button2.getItemMeta();
        button2Meta.setUnbreakable(true);
        button2.setItemMeta(button2Meta);
        ItemStack button3 = Item.getAWP();
        ItemMeta button3Meta = button3.getItemMeta();
        button3Meta.setUnbreakable(true);
        button3.setItemMeta(button3Meta);
        inventory.setItem(12, button1);
        inventory.setItem(13, button2);
        inventory.setItem(14, button3);
        inventory.setContents(inventory.getContents());
        player.openInventory(inventory);
    }
}
