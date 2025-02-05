package me.nonamegmm.mcscore.utils;

import com.minecraft.economy.apis.UltiEconomy;
import com.minecraft.economy.apis.UltiEconomyAPI;
import me.nonamegmm.mcscore.MCSCore;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static me.nonamegmm.mcscore.utils.database.Database.checkDatabase;
import static me.nonamegmm.mcscore.utils.database.Database.checkTable;

public class Init {

    private static void setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("UltiEconomy") != null) {
            MCSCore.economy = new UltiEconomy();
        }
    }

    public static void InitPlugin() {

        String fileName = "rooms.db";

        // 创建 Path 对象
        Path dirPath = Paths.get(MCSCore.getPlugin(MCSCore.class).getDataFolder().toURI());
        Path filePath = dirPath.resolve(fileName);
        try {
            Files.createDirectories(dirPath); // 创建多级目录
        } catch (IOException e) {
            Log.warn("目录创建失败：" + e.getMessage());
        }

        // 检查文件是否存在，如果不存在则创建
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath); // 创建文件
                Log.info("文件创建成功：" + filePath);
            } else {
                Log.warn("文件已存在：" + filePath);
            }
        } catch (IOException e) {
            Log.warn("文件创建失败：" + e.getMessage());
        }

        setupEconomy();
        checkDatabase();
        checkTable();
    }
}
