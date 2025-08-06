package me.nonamegmm.mcscore.maps;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.concurrent.ConcurrentHashMap;

public class Teams {
    private static final ConcurrentHashMap<String, Scoreboard> roomBoards = new ConcurrentHashMap<>();

    public static void CreateTeam(String room) {
        if (roomBoards.containsKey(room)) {
            return;
        }

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        roomBoards.put(room, board);

        Team teamCT = board.registerNewTeam(room + "CT");
        Team teamT  = board.registerNewTeam(room + "T");

        teamCT.setDisplayName("反恐精英");
        teamCT.setColor(ChatColor.BLUE);
        teamT.setDisplayName("恐怖分子");
        teamT.setColor(ChatColor.RED);

        teamCT.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
        teamT.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);

        Log.info("房间 " + room + " 创建完成：CT=" + teamCT + " T=" + teamT);
    }

    public static void JoinTeam(String room, Player player) {
        Scoreboard board = roomBoards.get(room);
        if (board == null) {
            Log.info("房间 " + room + " 不存在，正在创建");
            CreateTeam(room);
            board = roomBoards.get(room);
        }

        Team teamT  = board.getTeam(room + "T");
        Team teamCT = board.getTeam(room + "CT");

        if (teamT == null || teamCT == null) {
            return;
        }

        int tSize  = teamT.getEntries().size();
        int ctSize = teamCT.getEntries().size();

        Log.info("房间 " + room + " -> T=" + tSize + ", CT=" + ctSize);

        Team targetTeam = (tSize <= ctSize) ? teamT : teamCT;
        targetTeam.addEntry(player.getName());
        player.setScoreboard(board);

        player.sendMessage("你被分配到了 " + (targetTeam == teamT ? "§cT阵营" : "§9CT阵营"));
    }

    public static void RemoveFromAllRooms(Player player) {
        String name = player.getName();
        boolean any = false;

        for (Scoreboard board : roomBoards.values()) {
            for (Team team : board.getTeams()) {
                if (team.hasEntry(name)) {
                    team.removeEntry(name);
                    any = true;
                }
            }
        }

        if (any) {
            Log.info("玩家 " + name + " 已从所有房间队伍移除");
        } else {
            Log.info("玩家 " + name + " 不在任何房间队伍里");
        }
    }
}