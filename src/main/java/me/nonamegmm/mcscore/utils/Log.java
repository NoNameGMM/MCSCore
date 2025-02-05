package me.nonamegmm.mcscore.utils;

import me.nonamegmm.mcscore.MCSCore;

public class Log {
    private static MCSCore plugin;

    public Log(MCSCore plugin) {
        Log.plugin = plugin;
    }

    public static void setPlugin(MCSCore plugin) {
        Log.plugin = plugin;
    }

    public static void info(String str) {
        plugin.getLogger().info(str);
    }

    public static void warn(String str) {
        plugin.getLogger().warning(str);
    }
}
