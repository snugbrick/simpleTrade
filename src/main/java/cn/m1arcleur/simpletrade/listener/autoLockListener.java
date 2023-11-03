package cn.m1arcleur.simpletrade.listener;

import cn.m1arcleur.simpletrade.SimpleTrade;
import cn.m1arcleur.simpletrade.api.events.timeOutAutoLock;
import cn.m1arcleur.simpletrade.commands.tradeToSb;
import cn.m1arcleur.simpletrade.lockGS;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author MiracleUR
 * @version 1.0.0 2023.11.03 21:56
 * @website github.com/snugbrick;
 */
public class autoLockListener implements Listener {
    @EventHandler
    public static void onAutoLock(timeOutAutoLock e) {
        Player player = e.getPlayer();//弃用
        boolean lock = e.getLock();

        Player fromPlayer = tradeToSb.getFromPlayer();
        Player toPlayer = tradeToSb.getToPlayer();

        if (lockGS.getLock()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    lockGS.setLock(player, lock);
                    for (int i = 0; i < 9; i++) {
                        ItemStack tradeItem = inventoryCloseListener.getItemStackMap().get(i);
                        if (tradeItem != null) e.getPlayer().getInventory().addItem(tradeItem);
                    }
                    fromPlayer.sendMessage("§cThe transaction timed out and was canceled(15s)");
                    toPlayer.sendMessage("§cThe transaction timed out and was canceled(15s)");
                }
            }.runTaskLater(SimpleTrade.getInstance(), 15 * 20);
        }
    }
}
