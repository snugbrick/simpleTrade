package cn.m1arcleur.simpletrade.listener;

import cn.m1arcleur.simpletrade.api.events.sendPackageEvent;
import cn.m1arcleur.simpletrade.api.events.timeOutAutoLock;
import cn.m1arcleur.simpletrade.commands.tradeToSb;
import cn.m1arcleur.simpletrade.inventory.fromInv;
import cn.m1arcleur.simpletrade.inventory.toInv;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2023.11.06 23:04
 * <p>
 * 监听Inv关闭
 */
public class inventoryCloseListener implements Listener {
    private static Map<Integer, ItemStack> itemStackMap;
//  输入的物品在此

    static {//?
        itemStackMap = new HashMap<>();//?
    }//?

    @EventHandler
    public static void onInventoryClose(InventoryCloseEvent event) {
        Inventory newInventory = event.getInventory();
        Player player = (Player) event.getPlayer();//senderPlayer
        Player toPlayer = tradeToSb.getToPlayer();

        int size = newInventory.getSize();
        if (newInventory.equals(toInv.inventory) || newInventory.equals(fromInv.inventory)) {
            for (int i = 0; i < size; i++) {
                itemStackMap.put(i, newInventory.getItem(i));
            }
            sendPackageEvent sendPackageEvent = new sendPackageEvent(player, toPlayer, newInventory);
            Bukkit.getServer().getPluginManager().callEvent(sendPackageEvent);

            timeOutAutoLock timeOutAutoLock = new timeOutAutoLock(player, false);
            Bukkit.getServer().getPluginManager().callEvent(timeOutAutoLock);
        }
    }

    public static Map<Integer, ItemStack> getItemStackMap() {
        return itemStackMap;
    }
}
