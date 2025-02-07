package me.nonamegmm.mcscore;

import com.minecraft.economy.apis.UltiEconomyAPI;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import me.nonamegmm.mcscore.utils.Log;
import me.nonamegmm.mcscore.utils.room.CreateRoom;
import me.nonamegmm.mcscore.utils.room.JoinRoom;
import me.nonamegmm.mcscore.utils.Init;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public final class MCSCore extends JavaPlugin {
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
        getServer().getPluginManager().registerEvents(new Handler(), this);
        instance = this;
        Init.InitPlugin();
        joinRoom = new JoinRoom();
        createRoom = new CreateRoom();
        worldManager.loadWorld("room1");
        Log.info("插件已成功启动");
        Log.info("https://github.com/NoNameGMM/MCSCore");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(Objects.equals(args[0], "competitive")) {
            if(Objects.equals(args[1], "join")) {
                joinRoom.joinCompetitive((Player)sender);
            }
            else {
                sender.sendMessage("这个指令还没做");
            }
        }
        else {
            sender.sendMessage("这个指令还没做");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if(args.length == 1) {
            list.add("competitive");
            return list;
        }
        if(args.length == 2) {
            list.add("join");
            return list;
        }
        return null;
    }

    @Override
    public void onDisable() {

    }
}
