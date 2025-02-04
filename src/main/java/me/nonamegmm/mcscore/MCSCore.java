package me.nonamegmm.mcscore;

import com.minecraft.economy.apis.UltiEconomy;
import com.minecraft.economy.apis.UltiEconomyAPI;
import me.nonamegmm.mcscore.utils.economy.Competitive;
import me.nonamegmm.mcscore.utils.room.CreateRoom;
import me.nonamegmm.mcscore.utils.room.JoinRoom;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static me.nonamegmm.mcscore.utils.database.Database.checkDatabase;

public final class MCSCore extends JavaPlugin implements Listener {

    private static UltiEconomyAPI economy;
    private static CreateRoom createRoom;
    private static JoinRoom joinRoom;

    public static UltiEconomyAPI getEconomy() {
        return economy;
    }

    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("UltiEconomy") != null) {
            economy = new UltiEconomy();
        }
    }

    @Override
    public void onEnable() {
        String fileName = "rooms.db";

        // 创建 Path 对象
        Path dirPath = Paths.get(getDataFolder().toURI());
        Path filePath = dirPath.resolve(fileName);
        try {
            Files.createDirectories(dirPath); // 创建多级目录
        } catch (IOException e) {
            System.out.println("目录创建失败：" + e.getMessage());
        }

        // 检查文件是否存在，如果不存在则创建
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath); // 创建文件
                System.out.println("文件创建成功：" + filePath);
            } else {
                System.out.println("文件已存在：" + filePath);
            }
        } catch (IOException e) {
            System.out.println("文件创建失败：" + e.getMessage());
        }
        setupEconomy();
        checkDatabase();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(Objects.equals(args[0], "competitive")) {
            if(Objects.equals(args[1], "join")) {
                joinRoom.JoinCompetitive();
            }
            Competitive competitive = new Competitive();
            competitive.Competitors[0] = "NoNameGMM";
            competitive.Init();
        }
        return false;
    }

    @Override
    public void onDisable() {

    }
}
