package me.nonamegmm.mcscore.utils;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashSet;
import java.util.Set;

import static org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY;

public class HidePlayer {
    public final Set<Player> hidedPlayer = new HashSet<>();

    public void hideNick(Player player, String hide) {
        Team t = getHideTeam(player);
        if (!t.hasEntry(hide)) {
            t.addEntry(hide);
            Log.info("玩家名隐藏成功");
        }
    }

    private Team getHideTeam(Player player) {
        Scoreboard s = player.getPlayer().getScoreboard();
        Team t = s.getTeam("hideTeam");
        if (t == null) {
            t = s.registerNewTeam("hideTeam");
            t.setOption(NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        }
        return t;
    }
}
