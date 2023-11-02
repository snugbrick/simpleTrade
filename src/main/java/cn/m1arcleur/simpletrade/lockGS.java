package cn.m1arcleur.simpletrade;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author MiracleUR
 * @version 1.0.0 2023.11.02 20:53
 * @website github.com/snugbrick;
 */
public class lockGS {
    private static boolean lock = false;

    public static void setLock(Player player, boolean boo) {
        if (lock != boo) {
            lock = boo;
        } else {
            Bukkit.getServer().getLogger().info((String.format("%s's Lock is already %b ", player.getName(), boo)));
        }
    }

    public static boolean getLock() {
        return lock;
    }
}
