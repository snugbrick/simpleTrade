package cn.m1arcleur.simpletrade.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author MiracleUR
 * @version 1.0.0 2023.11.01 22:20
 * @website github.com/snugbrick;
 */
public class toInv {
    public static Inventory inventory;

    public static void open(Player player) {
        inventory = Bukkit.createInventory(player, 9, "§b§lPut in the items you need to trade");
        player.openInventory(inventory);
    }

    public static Inventory getInventory() {
        return inventory;
    }

}
