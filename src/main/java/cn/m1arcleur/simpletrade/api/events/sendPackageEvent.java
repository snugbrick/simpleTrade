package cn.m1arcleur.simpletrade.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @author MiracleUR
 * @version 1.0.0 2023.11.02 00:49
 * @website github.com/snugbrick;
 *
 * 玩家关闭输入输出Inv时触发
 */
public class sendPackageEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player fromPlayer;
    private Player toPlayer;
    private Inventory inventory;

    public sendPackageEvent(Player fromPlayer, Player toPlayer, Inventory inventory) {
        this.fromPlayer = fromPlayer;
        this.toPlayer = toPlayer;
        this.inventory = inventory;
    }

    @NonNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getFromPlayer() {
        return fromPlayer;
    }

    public Player getToPlayer() {
        return toPlayer;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
