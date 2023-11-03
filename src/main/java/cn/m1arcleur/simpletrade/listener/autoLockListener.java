package cn.m1arcleur.simpletrade.listener;

import cn.m1arcleur.simpletrade.SimpleTrade;
import cn.m1arcleur.simpletrade.api.events.timeOutAutoLock;
import cn.m1arcleur.simpletrade.lockGS;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author MiracleUR
 * @version 1.0.0 2023.11.03 21:56
 * @website github.com/snugbrick;
 */
public class autoLockListener implements Listener {
    @EventHandler
    public static void onAutoLock(timeOutAutoLock e) {
        Player player = e.getPlayer();
        boolean lock = e.getLock();

        if (lockGS.getLock()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    lockGS.setLock(player, lock);
                }
            }.runTaskLaterAsynchronously(SimpleTrade.getInstance(), 15 * 20);
        }
    }
}
