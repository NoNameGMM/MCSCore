package me.nonamegmm.mcscore.utils.database;

import java.io.File;
import java.sql.*;

import me.nonamegmm.mcscore.utils.Log;
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
            Log.info("连接到数据库成功！");
        } catch (SQLException e) {
            Log.warn(e.getMessage());
        }
    }

    public static void checkTable() {
        MCSCore plugin = JavaPlugin.getPlugin(MCSCore.class);

        // 获取 dataFolder
        File dataFolder = plugin.getDataFolder();

        String url = "jdbc:sqlite:" + dataFolder + "/rooms.db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM sqlite_master WHERE type='table'")) {
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    Log.warn("数据库中存在表");
                } else {
                    Log.warn("数据库中没有任何表");
                }
            }
        } catch (SQLException e) {
            Log.warn("检测表时发生错误：" + e.getMessage());
        }
    }
}