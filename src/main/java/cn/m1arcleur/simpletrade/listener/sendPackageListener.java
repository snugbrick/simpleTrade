package cn.m1arcleur.simpletrade.listener;

import cn.m1arcleur.simpletrade.api.events.sendPackageEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author MiracleUR
 * @version 1.0.0 2023.11.02 00:59
 * @website github.com/snugbrick;
 * <p>
 * 发送按钮
 */
public class sendPackageListener implements Listener {
    @EventHandler
    public static void sendPackage(sendPackageEvent e) {
        //Inventory inventory = e.getInventory();
        Player toPlayer = e.getToPlayer();

        sendButtonMessage(toPlayer, "§b§l§n[Click to view item]", "/simpleview", "");
        sendButtonMessage(toPlayer, "§b§l§n[Click to trade]", "/simpleaccept", "");
        sendButtonMessage(toPlayer, "§b§l§n[Click to refuse]", "/simplerefuse", "");
    }

    private static void sendButtonMessage(Player player, String text, String commands, String... args) {
        ComponentBuilder builder = new ComponentBuilder(text)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, commands + args[0]));

        player.spigot().sendMessage(ChatMessageType.CHAT, builder.create());
    }
}
