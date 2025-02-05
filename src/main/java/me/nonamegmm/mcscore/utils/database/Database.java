package me.nonamegmm.mcscore.utils.database;

import java.io.File;
import java.sql.*;

import me.nonamegmm.mcscore.utils.Log;
import me.nonamegmm.mcscore.MCSCore;
import org.bukkit.plugin.java.JavaPlugin;

public class Database {

    static MCSCore plugin = JavaPlugin.getPlugin(MCSCore.class);
    private static final File dataFolder = plugin.getDataFolder();

    public static void checkDatabase() {
        String url = "jdbc:sqlite:" + dataFolder + "/rooms.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            Log.info("连接到数据库成功！");
        } catch (SQLException e) {
            Log.warn(e.getMessage());
        }
    }

    public static void checkPlayerDatabase() {
        String url = "jdbc:sqlite:" + dataFolder + "/players.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            Log.info("连接到数据库成功！");
        } catch (SQLException e) {
            Log.warn(e.getMessage());
        }
    }

    public static void checkTable() {
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
                    String sql =
                        "CREATE TABLE IF NOT EXISTS room1 (" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    player_name TEXT NOT NULL," +
                        "    money REAL NOT NULL DEFAULT 0.0" +
                        ");";

                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute(sql);
                        Log.info("表 'room1' 创建成功！");
                    }
                }
            }
        } catch (SQLException e) {
            Log.warn("检测表时发生错误：" + e.getMessage());
        }
    }

    public static void join(String name) {

    }
}