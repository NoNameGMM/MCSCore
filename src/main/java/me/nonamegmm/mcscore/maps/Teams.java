package me.nonamegmm.mcscore.maps;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Teams {
    public static void CreateTeam(String room)
    {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Team teamCT = scoreboard.getTeam(room + "CT");
        Team teamT = scoreboard.getTeam(room + "T");

        if (teamCT == null) {
            teamCT = scoreboard.registerNewTeam(room + "CT");
        }
        if (teamT == null) {
            teamT = scoreboard.registerNewTeam(room + "T");
        }

        teamT.setDisplayName("恐怖分子");
        teamT.setColor(ChatColor.RED);
        teamCT.setDisplayName("反恐精英");
        teamCT.setColor(ChatColor.BLUE);

        teamCT.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
        teamT.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
    }

    public static void JoinTeam(String room, Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team teamT = scoreboard.getTeam(room + "T");
        Team teamCT = scoreboard.getTeam(room + "CT");

        int teamTSize = teamT.getEntries().size();
        int teamCTSize = teamCT.getEntries().size();

        if (teamTSize <= teamCTSize) {
            teamT.addEntry(player.getName());
            player.sendMessage("你被分配到了 §cT阵营");
        } else {
            teamCT.addEntry(player.getName());
            player.sendMessage("你被分配到了 §9CT阵营");
        }

        player.setScoreboard(scoreboard);
    }
}
