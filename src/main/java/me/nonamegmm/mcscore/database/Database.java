package me.nonamegmm.mcscore.database;

import java.io.File;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.nonamegmm.mcscore.utils.Log;
import me.nonamegmm.mcscore.MCSCore;
import org.bukkit.entity.Player;

import static me.nonamegmm.mcscore.MCSCore.getWorldManager;

public class Database {

    private static final MCSCore plugin = MCSCore.getInstance();
    private static final File dataFolder = plugin.getDataFolder();
    private static final String rooms = "jdbc:sqlite:" + dataFolder + "/rooms.db";
    private static final String players = "jdbc:sqlite:" + dataFolder + "/players.db";

    public static void checkDatabase() {
        try (Connection conn = DriverManager.getConnection(rooms)) {
            Log.info("连接到数据库成功！");
        } catch (SQLException e) {
            Log.warn(e.getMessage());
        }
    }

    public static void checkPlayerDatabase() {
        try (Connection conn = DriverManager.getConnection(players)) {
            Log.info("连接到数据库成功！");
        } catch (SQLException e) {
            Log.warn(e.getMessage());
        }
    }

    public static void createPlayerProfile(Player player) {
        try (Connection conn = DriverManager.getConnection(players)) {
            String createSql = "CREATE TABLE IF NOT EXISTS " + player.getName() + " (" +
                    "plays INTEGER DEFAULT 0," +
                    "win_rate DOUBLE DEFAULT 0.0," +
                    "kills INTEGER DEFAULT 0," +
                    "avg_adr DOUBLE DEFAULT 0.0," +
                    "avg_rating DOUBLE DEFAULT 0.0," +
                    "room INTEGER DEFAULT 0," +
                    "in_room TEXT" +
                    ");";
            try (PreparedStatement createStmt = conn.prepareStatement(createSql)) {
                createStmt.execute();
                Log.info("表 '" + player.getName() + "' 创建成功！");
            }

            String insertSql = "INSERT INTO " + player.getName() + " DEFAULT VALUES";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.executeUpdate();
                System.out.println("默认数据插入成功！");
            }
        } catch (SQLException e) {
            Log.warn(e.getMessage());
        }


    }

    public static void checkTable() {
        try (Connection conn = DriverManager.getConnection(rooms);
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

    public static void playerLeft(String name) {
        String in_room = "";

        String querySql = "SELECT in_room FROM " + name + " LIMIT 1";
        try (Connection conn = DriverManager.getConnection(players);
             PreparedStatement queryStmt = conn.prepareStatement(querySql);
             ResultSet rs = queryStmt.executeQuery()) {
            if (rs.next()) {
                in_room = rs.getString("in_room");
                Log.info("第一行的 in_room 值: " + in_room);
            } else {
                Log.warn("表中没有数据！");
            }
        } catch (SQLException e) {
            Log.warn("查询表时发生错误：" + e.getMessage());
        }

        String updateSql = "UPDATE " + name + " SET room = 0, in_room = '' WHERE ROWID = (SELECT MIN(ROWID) FROM " + name + ")";
        try (Connection conn = DriverManager.getConnection(players);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            int rowsAffected = updateStmt.executeUpdate();
            Log.info("更新成功，受影响的行数：" + rowsAffected);
        } catch (SQLException e) {
            Log.warn("更新表时发生错误：" + e.getMessage());
        }

        String deleteSql = "DELETE FROM " + in_room + " WHERE player_name = ?";
        try (Connection conn = DriverManager.getConnection(rooms);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            deleteStmt.setString(1, name);
            int rowsAffected = deleteStmt.executeUpdate();
            Log.info("删除成功，受影响的行数：" + rowsAffected);
        } catch (SQLException e) {
            Log.warn("删除表时发生错误：" + e.getMessage());
        }
    }

    public static String join(String name) {
        String room = checkRoomIsFull();
        String sql = "INSERT INTO " + room + " (player_name) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(rooms);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            Log.info("数据插入成功！");
        } catch (SQLException e) {
            Log.warn("插入数据时发生错误：" + e.getMessage());
        }
        String sql1 = "UPDATE " + name + " SET room = 1, in_room = ? WHERE ROWID = (SELECT MIN(ROWID) FROM " + name + ")";
        try (Connection conn = DriverManager.getConnection(players);
             PreparedStatement pstmt = conn.prepareStatement(sql1)) {
            pstmt.setString(1, room);
            pstmt.executeUpdate();
            Log.info("数据插入成功！");
        } catch (SQLException e) {
            Log.warn("插入数据时发生错误：" + e.getMessage());
        }
        return room;
    }

    public static Boolean checkJoin(String name) {
        String querySql = "SELECT room FROM " + name + " WHERE room = 1";
        try (Connection conn = DriverManager.getConnection(players);
             PreparedStatement queryStmt = conn.prepareStatement(querySql);
             ResultSet rs = queryStmt.executeQuery()) {
            if (rs.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("查询表时发生错误：" + e.getMessage());
        }
        return true;
    }

    private static String checkRoomIsFull() {
        String finalRoom = "";
        try (Connection conn = DriverManager.getConnection(rooms)) {
            Log.info("连接到数据库成功！");
            String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name LIKE 'room%'";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                String maxRoom = null;
                int maxNumber = -1;
                while (rs.next()) {
                    String tableName = rs.getString("name");
                    Pattern pattern = Pattern.compile("room(\\d+)");
                    Matcher matcher = pattern.matcher(tableName);
                    if (matcher.matches()) {
                        int number = Integer.parseInt(matcher.group(1));
                        if (number > maxNumber) {
                            maxNumber = number;
                            maxRoom = tableName;
                        }
                    }
                }
                Log.info("数字最大的表是: " + maxRoom);

                Pattern pattern = Pattern.compile("room(\\d+)");
                Matcher matcher = pattern.matcher(maxRoom);
                if (matcher.matches()) {
                    int currentRoomNumber = Integer.parseInt(matcher.group(1));
                    String nextTableName = "room" + (currentRoomNumber + 1);

                    String countSql = "SELECT COUNT(*) AS total FROM " + maxRoom;
                    try (PreparedStatement countStmt = conn.prepareStatement(countSql);
                         ResultSet rs2 = countStmt.executeQuery()) {
                        if (rs2.next()) {
                            int total = rs2.getInt("total");
                            Log.info("表 '" + maxRoom + "' 中的 id 总数为 " + total);

                            if (total == 10) {
                                String createSql = "CREATE TABLE IF NOT EXISTS " + nextTableName + " (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        "player_name TEXT NOT NULL," +
                                        "money REAL NOT NULL DEFAULT 0.0" +
                                        ");";
                                try (PreparedStatement createStmt = conn.prepareStatement(createSql)) {
                                    createStmt.execute();
                                    Log.info("表 '" + nextTableName + "' 创建成功！");
                                    finalRoom = nextTableName;
                                    getWorldManager().loadWorld(finalRoom);
                                }
                            }
                            else {
                                finalRoom = maxRoom;
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("查询表时发生错误：" + e.getMessage());
                    }
                } else {
                    System.out.println("表名格式不正确：" + maxRoom);
                }

            }
        } catch (SQLException e) {
            Log.warn("查询表时发生错误：" + e.getMessage());
        }
        Log.info(finalRoom);
        return finalRoom;
    }
}