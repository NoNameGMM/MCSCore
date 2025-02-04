package me.nonamegmm.mcscore.utils.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import me.nonamegmm.mcscore.MCSCore;
import org.bukkit.plugin.java.JavaPlugin;

public class Database {
    public static void checkDatabase() {
        // 数据库文件路径
        MCSCore plugin = JavaPlugin.getPlugin(MCSCore.class);

        // 获取 dataFolder
        File dataFolder = plugin.getDataFolder();

        String url = "jdbc:sqlite:" + dataFolder + "/rooms.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("连接到数据库成功！");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}