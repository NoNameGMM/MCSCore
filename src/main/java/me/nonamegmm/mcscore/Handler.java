package me.nonamegmm.mcscore;

import me.nonamegmm.mcscore.utils.HidePlayer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.nonamegmm.mcscore.utils.database.Database.createPlayerProfile;
import static me.nonamegmm.mcscore.utils.database.Database.playerLeft;
import static org.bukkit.Bukkit.getServer;

public class Handler implements Listener {
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
                        material == Material.CRIMSON_BUTTON || material == Material.WARPED_BUTTON) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerLeft(event.getPlayer().getName());
    }
}
