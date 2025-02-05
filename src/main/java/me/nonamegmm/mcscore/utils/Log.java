package me.nonamegmm.mcscore.utils;

import me.nonamegmm.mcscore.MCSCore;

public class Log {
    private static final MCSCore plugin = MCSCore.getInstance();

    public static void info(String str) {
        plugin.getLogger().info(str);
    }

    public static void warn(String str) {
        plugin.getLogger().warning(str);
    }
}
