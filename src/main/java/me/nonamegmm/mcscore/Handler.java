package me.nonamegmm.mcscore;

import me.nonamegmm.mcscore.utils.HidePlayer;
import me.nonamegmm.mcscore.utils.Log;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Objects;

import static me.nonamegmm.mcscore.database.Database.createPlayerProfile;
import static me.nonamegmm.mcscore.database.Database.playerLeft;
import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getServer;

public class Handler implements PluginMessageListener,Listener {
    private static MCSCore plugin = MCSCore.getInstance();
    public static final String menuChannel = "mcscore:menu";

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = getServer().getWorld("world");
        Location location = new Location(world, 5, 17, 36);
        player.teleport(location);
        if(!player.hasPlayedBefore())
        {
            createPlayerProfile(player);
            HidePlayer hidePlayer = new HidePlayer();
            hidePlayer.hideNick(player, player.getName());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.ADVENTURE) {
            if (event.getClickedBlock() != null) {
                Material material = event.getClickedBlock().getType();
                if (material == Material.OAK_DOOR || material == Material.SPRUCE_DOOR ||
                        material == Material.BIRCH_DOOR || material == Material.JUNGLE_DOOR ||
                        material == Material.ACACIA_DOOR || material == Material.DARK_OAK_DOOR ||
                        material == Material.CRIMSON_DOOR || material == Material.WARPED_DOOR ||
                        material == Material.IRON_DOOR || material == Material.OAK_TRAPDOOR ||
                        material == Material.SPRUCE_TRAPDOOR || material == Material.BIRCH_TRAPDOOR ||
                        material == Material.JUNGLE_TRAPDOOR || material == Material.ACACIA_TRAPDOOR ||
                        material == Material.DARK_OAK_TRAPDOOR || material == Material.CRIMSON_TRAPDOOR ||
                        material == Material.WARPED_TRAPDOOR || material == Material.IRON_TRAPDOOR ||
                        material == Material.OAK_FENCE_GATE || material == Material.SPRUCE_FENCE_GATE ||
                        material == Material.BIRCH_FENCE_GATE || material == Material.JUNGLE_FENCE_GATE ||
                        material == Material.ACACIA_FENCE_GATE || material == Material.DARK_OAK_FENCE_GATE ||
                        material == Material.CRIMSON_FENCE_GATE || material == Material.WARPED_FENCE_GATE ||
                        material == Material.OAK_BUTTON || material == Material.SPRUCE_BUTTON ||
                        material == Material.BIRCH_BUTTON || material == Material.JUNGLE_BUTTON ||
                        material == Material.ACACIA_BUTTON || material == Material.DARK_OAK_BUTTON ||
                        material == Material.CRIMSON_BUTTON || material == Material.WARPED_BUTTON ||
                        material == Material.STONE_BUTTON) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerLeft(event.getPlayer().getName());
        Inventory inventory = event.getPlayer().getInventory();
        inventory.clear();
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClick().isShiftClick() || event.getClick().isRightClick()) {
            event.setCancelled(true);
        }
        else if (event.getClick().isLeftClick()) {
            ItemStack item = event.getCurrentItem();
            if (item != null && item.hasItemMeta()) {
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta != null && itemMeta.hasDisplayName()) {
                    String itemName = itemMeta.getDisplayName();
                    System.out.println("点击的物品名称: " + itemName);
                } else {
                    System.out.println("点击的物品没有名称");
                }
            }
        }
    }

    public void registerChannel() {
        plugin = MCSCore.getInstance();
        getServer().getMessenger().registerOutgoingPluginChannel(plugin, menuChannel);
        getServer().getMessenger().registerIncomingPluginChannel(plugin, menuChannel, this);
        Bukkit.getPluginManager().registerEvents(this,plugin);
        Log.info("消息通道注册成功");
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        if (channel.equals(menuChannel)) {
            ByteArrayInputStream stream = new ByteArrayInputStream(message);
            DataInputStream in = new DataInputStream(stream);

            try {
                while(in.available() > 0) {
                    String receivedMessage = in.readUTF();
                    Log.info("收到来自客户端的消息: " + receivedMessage);
                    String[] parts = receivedMessage.split("\\s+");
                    if(parts[0].equals("buy")) {
                        Menu.openMenu(getPlayer(parts[1]));
                    }
                }
            } catch (IOException e) {
                Log.info(e.toString());
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.info(e.toString());
                }

            }
        }
    }
}
