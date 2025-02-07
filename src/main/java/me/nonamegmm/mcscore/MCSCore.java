package me.nonamegmm.mcscore;

import com.minecraft.economy.apis.UltiEconomyAPI;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import me.nonamegmm.mcscore.utils.HidePlayer;
import me.nonamegmm.mcscore.utils.Log;
import me.nonamegmm.mcscore.utils.room.CreateRoom;
import me.nonamegmm.mcscore.utils.room.JoinRoom;
import me.nonamegmm.mcscore.utils.Init;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static me.nonamegmm.mcscore.utils.database.Database.createPlayerProfile;


public final class MCSCore extends JavaPlugin implements Listener {
    private static MCSCore instance;
    public static UltiEconomyAPI economy;
    private static CreateRoom createRoom;
    private static JoinRoom joinRoom;
    private static MVWorldManager worldManager;

    public static UltiEconomyAPI getEconomy() {
        return economy;
    }

    public static MCSCore getInstance() {
        return instance;
    }

    public static MVWorldManager getWorldManager() {
        return worldManager;
    }

    @Override
    public void onEnable() {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        worldManager = core.getMVWorldManager();
        getServer().getPluginManager().registerEvents(this, this);
        instance = this;
        Init.InitPlugin();
        joinRoom = new JoinRoom();
        createRoom = new CreateRoom();
        worldManager.loadWorld("room1");
        Log.info("插件已成功启动");
        Log.info("https://github.com/NoNameGMM/MCSCore");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!player.hasPlayedBefore())
        {
            createPlayerProfile(player);
            HidePlayer hidePlayer = new HidePlayer();
            hidePlayer.hideNick(player, player.getName());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(Objects.equals(args[0], "competitive")) {
            if(Objects.equals(args[1], "join")) {
                joinRoom.joinCompetitive((Player)sender);
            }
        }
        return false;
    }

    @Override
    public void onDisable() {

    }
}
