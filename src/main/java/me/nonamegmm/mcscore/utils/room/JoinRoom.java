package me.nonamegmm.mcscore.utils.room;

import me.nonamegmm.mcscore.utils.Log;

import static me.nonamegmm.mcscore.utils.database.Database.checkTable;
import static me.nonamegmm.mcscore.utils.database.Database.join;

public class JoinRoom {
    public void joinCompetitive(String name) {
        checkTable();
        join(name);
        Log.info("正在尝试加入匹配");
    }
}
