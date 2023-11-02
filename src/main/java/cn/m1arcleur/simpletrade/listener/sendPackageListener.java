package cn.m1arcleur.simpletrade.listener;

import cn.m1arcleur.simpletrade.api.events.sendPackageEvent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

/**
 * @author MiracleUR
 * @version 1.0.0 2023.11.02 00:59
 * @website github.com/snugbrick;
 *
 * 发送按钮
 */
public class sendPackageListener implements Listener {
    @EventHandler
    public static void sendPackage(sendPackageEvent e) {
        Inventory inventory = e.getInventory();
        Player toPlayer = e.getToPlayer();

        TextComponent viewMessage = new TextComponent("Click to view item");
        TextComponent tradeMessage = new TextComponent("Click to trade");
        TextComponent refuseMessage = new TextComponent("Click to refuse");

        viewMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "simpleRequest viewItem"));
        tradeMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "simpleRequest accept"));
        refuseMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "simpleRequest refuse"));

        toPlayer.spigot().sendMessage(viewMessage, tradeMessage, refuseMessage);
    }
}
