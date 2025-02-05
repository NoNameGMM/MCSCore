package me.nonamegmm.mcscore.utils.room;

import static me.nonamegmm.mcscore.utils.database.Database.checkTable;
import static me.nonamegmm.mcscore.utils.database.Database.join;

public class JoinRoom {
    public void joinCompetitive(String name) {
        checkTable();
        join(name);
    }
}
