package cn.m1arcleur.simpletrade.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @author MiracleUR
 * @version 1.0.0 2023.11.03 21:52
 * @website github.com/snugbrick;
 * 
 * //当交易订单超时时触发
 */
public class timeOutAutoLock extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private boolean lock;

    public timeOutAutoLock(Player player, boolean lock) {
        this.player = player;
        this.lock = lock;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean getLock() {
        return lock;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
