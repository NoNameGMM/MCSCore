package me.nonamegmm.mcscore.room;

import me.nonamegmm.mcscore.Items;
import me.nonamegmm.mcscore.utils.Log;
import me.nonamegmm.mcscore.utils.Message;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import static me.nonamegmm.mcscore.database.Database.*;
import static org.bukkit.Bukkit.getServer;

public class JoinRoom {
    public void joinCompetitive(Player player) {
        if(checkJoin(player.getName())){
            String room = join(player.getName());
            Log.info("正在尝试加入匹配");
            Message.sendActionBar(player, "成功加入房间");
            World world = getServer().getWorld(room);
            Location location = new Location(world, 5, 19, 36); // 设置一个默认的传送位置
            player.teleport(location);
            player.sendMessage("你已经被传送到世界: " + room);
            Items.getGlock(player);
            Items.get9mm(player);
            Items.get9mm(player);
            Items.get9mm(player);
            Items.get9mm(player);
            Items.get9mm(player);
        }
        else {
            Message.sendActionBar(player, "你已经在房间里了");
        }
    }
}
