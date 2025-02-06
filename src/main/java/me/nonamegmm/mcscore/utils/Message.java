package me.nonamegmm.mcscore.utils;

import me.nonamegmm.mcscore.MCSCore;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Message {

    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static void sendTitle(Player player, String message, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(message, "", fadeIn, stay, fadeOut);
    }

    public static void sendSubTitle(Player player, String message, int fadeIn, int stay, int fadeOut) {
        player.sendTitle("", message, fadeIn, stay, fadeOut);
    }
}
