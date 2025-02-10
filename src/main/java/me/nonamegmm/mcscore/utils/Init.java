package me.nonamegmm.mcscore.utils;

import com.minecraft.economy.apis.UltiEconomy;
import me.nonamegmm.mcscore.MCSCore;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static me.nonamegmm.mcscore.database.Database.*;

public class Init {
    private static final MCSCore plugin = MCSCore.getInstance();

    private static void setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("UltiEconomy") != null) {
            MCSCore.economy = new UltiEconomy();
        }
    }

    public static void InitPlugin() {
        String rooms = "rooms.db";
        String players = "players.db";

        Path dirPath = Paths.get(MCSCore.getPlugin(MCSCore.class).getDataFolder().toURI());
        Path room = dirPath.resolve(rooms);
        Path player = dirPath.resolve(players);
        try {
            Files.createDirectories(dirPath);
        } catch (IOException e) {
            Log.warn("目录创建失败：" + e.getMessage());
        }

        try {
            if (!Files.exists(room)) {
                Files.createFile(room);
                Log.info("文件创建成功：" + room);
            } else {
                Log.warn("文件已存在：" + room);
            }
            if (!Files.exists(player)) {
                Files.createFile(player);
                Log.info("文件创建成功：" + player);
            } else {
                Log.warn("文件已存在：" + player);
            }
        } catch (IOException e) {
            Log.warn("数据库创建失败：" + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(plugin);
        }

        setupEconomy();
        checkDatabase();
        checkPlayerDatabase();
        checkTable();
    }
}
