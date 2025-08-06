package me.nonamegmm.mcscore;

import com.codingguru.actionBarAPI.ActionBarAPI;
import com.codingguru.actionBarAPI.taskHandlers.reception;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableItemNBT;
import me.nonamegmm.mcscore.utils.HidePlayer;
import me.nonamegmm.mcscore.utils.Log;
import me.nonamegmm.mcscore.utils.PickItem;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;
import java.util.function.Function;

import static me.nonamegmm.mcscore.database.Database.createPlayerProfile;
import static me.nonamegmm.mcscore.database.Database.playerLeft;
import static me.nonamegmm.mcscore.maps.Teams.RemoveFromAllRooms;
import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getServer;

public class Handler implements PluginMessageListener,Listener {
    private static MCSCore plugin = MCSCore.getInstance();
    public static final String menuChannel = "mcscore:menu";
    private static boolean isKKeyPressed = false;
    private final Set<UUID> seenThisSession = new HashSet<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = getServer().getWorld("world");
        Location location = new Location(world, 5, 17, 36);
        player.teleport(location);
        HidePlayer hidePlayer = new HidePlayer();
        hidePlayer.hideNick(player, player.getName());
        if(!player.hasPlayedBefore())
        {
            createPlayerProfile(player);
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
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        playerLeft(event.getPlayer().getName());
        Inventory inventory = event.getPlayer().getInventory();
        inventory.clear();
        RemoveFromAllRooms(event.getPlayer());
        for (Team team : scoreboard.getTeams()) {
            if (team.hasEntry(event.getPlayer().getName())) {
                team.removeEntry(event.getPlayer().getName());
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        playerLeft(event.getPlayer().getName());
        Inventory inventory = event.getPlayer().getInventory();
        inventory.clear();
        RemoveFromAllRooms(event.getPlayer());
        for (Team team : scoreboard.getTeams()) {
            if (team.hasEntry(event.getPlayer().getName())) {
                team.removeEntry(event.getPlayer().getName());
                break;
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (seenThisSession.add(player.getUniqueId())) {
            player.sendTitle("正在进行枪械验证","请耐心等待",0,100,0);
            Bukkit.getScheduler().runTaskLater(MCSCore.getInstance(), () -> {
                Items.getGlock(player);
                player.kickPlayer("[MCSCore]\n" + player.getName() + " 感谢游玩本服务器\n枪械验证完成\n请重新加入服务器");
            }, 100L);
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.Player) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onMove(org.bukkit.event.player.PlayerMoveEvent e) {

        Player player = e.getPlayer();
        String gunIds = getNearbyDrops(player, 1.0);

        if (gunIds.isEmpty()) {
            ActionBarAPI.startActionBar(player, "周围无掉落物", reception.Styles.gradient, List.of("WHITE"), null);
        } else {
            ActionBarAPI.startActionBar(player, "周围枪械: " + gunIds, reception.Styles.gradient, List.of("WHITE"), null);
        }
    }

    private String getNearbyDrops(Player player, double radius) {
        List<String> list = new ArrayList<>();
        player.getNearbyEntities(radius, radius, radius).forEach(en -> {
            if (en instanceof org.bukkit.entity.Item) {
                String GunId = NBT.get(((Item) en).getItemStack(), (Function<ReadableItemNBT, String>) nbt -> nbt.getString("GunId"));   // 或 "gun_id"
                switch (GunId) {
                    case "mcs2:cs_glock":
                        list.add("格洛克18型");
                        break;
                    case "mcs2:cs_m4a1s":
                        list.add("M4A1消音型");
                        break;
                    case "mcs2:cs_awp":
                        list.add("AWP");
                        break;
                    case "mcs2:cs_ak":
                        list.add("AK47");
                        break;
                    case "mcs2:cs_usp":
                        list.add("USP消音型");
                        break;
                    default:
                        break;
                }
            }
        });
        if (!list.isEmpty())
        {
            return list.get(0);
        }
        return "";
    }

    private Item getNearbyDropsItem(Player player, double radius) {
        List<Item> list = new ArrayList<>();
        player.getNearbyEntities(radius, radius, radius).forEach(en -> {
            if (en instanceof org.bukkit.entity.Item) {
                list.add((Item) en);
            }
        });
        if (list.isEmpty()) {
            return null;
        }
        Log.info("获取到需要拾取的物品:" + list.get(0).getItemStack());
        return list.get(0);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClick().isShiftClick() || event.getClick().isRightClick()) {
            event.setCancelled(true);
        }
        else if (event.getClick().isLeftClick()) {
            if(!isKKeyPressed) {
                ItemStack clickedItem = event.getCurrentItem();
                Log.info(clickedItem.getType().toString());
                if (clickedItem != null) {
                    if (clickedItem.getType() == Material.AIR || clickedItem.getAmount() <= 0) {
                        return;
                    }
                    String gunid = NBT.get(clickedItem, nbt -> (String) nbt.getString("GunId"));
                    player.sendMessage("你购买了枪械: " + gunid);
                    switch (gunid) {
                        case "mcs2:cs_glock":
                            Items.getGlock(player);
                            break;
                        case "mcs2:cs_m4a1s":
                            Items.getM4A1S(player);
                            break;
                        case "mcs2:cs_awp":
                            Items.getAWP(player);
                            break;
                        case "mcs2:cs_ak":
                            Items.getAK47(player);
                            break;
                        case "mcs2:cs_usp":
                            Items.getUSP(player);
                            break;
                        default:
                            break;
                    }
                }
                isKKeyPressed = true;
            }
            else {
                isKKeyPressed = false;
            }
            event.setCancelled(true);
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
                    if(parts[0].equals("pick")) {
                        Item item = getNearbyDropsItem(getPlayer(parts[1]),1.0);
                        PickItem.pickupWithAnimation(getPlayer(parts[1]), item);
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
