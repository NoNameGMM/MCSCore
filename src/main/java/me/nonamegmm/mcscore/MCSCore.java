package me.nonamegmm.mcscore;

import com.minecraft.economy.apis.UltiEconomyAPI;
import me.nonamegmm.mcscore.utils.Log;
import me.nonamegmm.mcscore.utils.room.CreateRoom;
import me.nonamegmm.mcscore.utils.room.JoinRoom;
import me.nonamegmm.mcscore.utils.Init;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class MCSCore extends JavaPlugin implements Listener {
    private static MCSCore instance;
    public static UltiEconomyAPI economy;
    private static CreateRoom createRoom;
    private static JoinRoom joinRoom;

    public static UltiEconomyAPI getEconomy() {
        return economy;
    }

    public static MCSCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        Init.InitPlugin();
        joinRoom = new JoinRoom();
        createRoom = new CreateRoom();
        Log.info("插件已成功启动");
        Log.info("https://github.com/NoNameGMM/MCSCore");
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
