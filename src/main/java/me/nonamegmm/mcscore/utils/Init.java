package me.nonamegmm.mcscore.utils;

import com.minecraft.economy.apis.UltiEconomy;
import me.nonamegmm.mcscore.MCSCore;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static me.nonamegmm.mcscore.utils.database.Database.checkDatabase;
import static me.nonamegmm.mcscore.utils.database.Database.checkPlayerDatabase;

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

        // 创建 Path 对象
        Path dirPath = Paths.get(MCSCore.getPlugin(MCSCore.class).getDataFolder().toURI());
        Path room = dirPath.resolve(rooms);
        Path player = dirPath.resolve(players);
        try {
            Files.createDirectories(dirPath); // 创建多级目录
        } catch (IOException e) {
            Log.warn("目录创建失败：" + e.getMessage());
        }

        // 检查文件是否存在，如果不存在则创建
        try {
            if (!Files.exists(room)) {
                Files.createFile(room); // 创建文件
                Log.info("文件创建成功：" + room);
            } else {
                Log.warn("文件已存在：" + room);
            }
            if (!Files.exists(player)) {
                Files.createFile(player); // 创建文件
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
    }
}
