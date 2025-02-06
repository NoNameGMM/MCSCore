package me.nonamegmm.mcscore.utils.room;

import me.nonamegmm.mcscore.utils.Log;
import me.nonamegmm.mcscore.utils.Message;
import org.bukkit.entity.Player;

import static me.nonamegmm.mcscore.utils.database.Database.checkTable;
import static me.nonamegmm.mcscore.utils.database.Database.join;

public class JoinRoom {
    public void joinCompetitive(Player player) {
        checkTable();
        join(player.getName());
        Log.info("正在尝试加入匹配");
        Message.sendActionBar(player, "成功加入房间");
    }
}
