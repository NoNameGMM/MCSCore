package me.nonamegmm.mcscore.utils.room;

import me.nonamegmm.mcscore.utils.Log;
import me.nonamegmm.mcscore.utils.Message;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import static me.nonamegmm.mcscore.utils.database.Database.checkTable;
import static me.nonamegmm.mcscore.utils.database.Database.join;
import static org.bukkit.Bukkit.getServer;

public class JoinRoom {
    public void joinCompetitive(Player player) {
        checkTable();
        String room = join(player.getName());
        Log.info("正在尝试加入匹配");
        Message.sendActionBar(player, "成功加入房间");
        World world = getServer().getWorld(room);
        Location location = new Location(world, 0, 100, 0); // 设置一个默认的传送位置
        player.teleport(location);
        player.sendMessage("你已经被传送到世界: " + room);
    }
}
