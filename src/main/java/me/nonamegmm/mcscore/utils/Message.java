package me.nonamegmm.mcscore.utils;

import me.nonamegmm.mcscore.Handler;
import me.nonamegmm.mcscore.MCSCore;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Message {
    private static final MCSCore plugin = MCSCore.getInstance();

    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static void sendTitle(Player player, String message, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(message, "", fadeIn, stay, fadeOut);
    }

    public static void sendSubTitle(Player player, String message, int fadeIn, int stay, int fadeOut) {
        player.sendTitle("", message, fadeIn, stay, fadeOut);
    }

    public static void sendMessageToForgeClient(Player player, String message) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {
            dataOutputStream.writeUTF(message);
            player.sendPluginMessage(plugin, Handler.menuChannel, byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
