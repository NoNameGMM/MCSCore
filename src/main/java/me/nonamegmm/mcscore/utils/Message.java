package me.nonamegmm.mcscore.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

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
