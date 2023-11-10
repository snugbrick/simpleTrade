package cn.m1arcleur.simpletrade.commands;

import cn.m1arcleur.simpletrade.inventory.fromInv;
import cn.m1arcleur.simpletrade.inventory.toInv;
import cn.m1arcleur.simpletrade.lockGS;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author MiracleUR
 * @version 1.0.0 2023.11.01 21:51
 * @website github.com/snugbrick;
 * <p>
 * /simpleTrade to|from <playerName> <money>指令
 */
public class tradeToSb implements TabExecutor {
    private static Player toPlayer;
    private static Player fromPlayer;
    private static int Money;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {
            if (strings.length == 3) {//确认指令有三个参数
                String direction = strings[0].toLowerCase();
                String playerNames = strings[1];
                Player player = Bukkit.getPlayer(playerNames);
                int money = Integer.parseInt(strings[2]);

                Money = money;
                fromPlayer = (Player) commandSender;
                toPlayer = player;//给别的方法用的

                //确定玩家和金额非空
                if (player == null || strings[2] == null) {
                    commandSender.sendMessage("§cCan't find the player you want to trade");
                    return false;
                }
                //确定目标非己
                if (playerNames.equals(commandSender.getName())) {
                    commandSender.sendMessage("§cCan't trade yourself");
                    return false;
                }
                //确定指令正确
                if (s.equalsIgnoreCase("simpleTrade") && direction.equals("to")) {
                    commandSender.sendMessage(String.format("§aYou have sent a trade request to §b§l%s §r§a, Money: %d", playerNames, money));
                    player.sendMessage(String.format("§aYou have a trade request from §b§l%s §r§a, Money: %d", commandSender.getName(), money));

                    //打开输出物品栏，给予锁
                    lockGS.setLock((Player) commandSender, true);
                    toInv.open((Player) commandSender);

                    sendButtonMessage((Player) commandSender, "§b§l§n[Click to Cancel]", "/tradecancel", "");
                } else if (s.equalsIgnoreCase("simpleTrade") && direction.equals("from")) {
                    commandSender.sendMessage(String.format("§aYou have sent a trade request from §b§l%s §r§a, Money: %d", playerNames, money));
                    player.sendMessage(String.format("§aYou have a trade request to §b§l%s §r§a, Money: %d", commandSender.getName(), money));

                    //打开输出物品栏，给予锁
                    lockGS.setLock((Player) commandSender, true);
                    fromInv.open((Player) commandSender);

                    sendButtonMessage((Player) commandSender, "§b§l§n[Click to Cancel]", "/tradecancel", "");
                } else {
                    commandSender.sendMessage("§c§lDid you enter the wrong command? §r§fusage: /simpleTrade to|from <playerName> <money>");
                }
            } else {
                commandSender.sendMessage("§c§lDid you enter the wrong command? §r§fusage: /simpleTrade to|from <playerName> <money>");
                return false;
            }
        } catch (Exception exception) {
            commandSender.sendMessage("§c§lDid you enter the wrong command? §r§fusage: /simpleTrade to|from <playerName> <money>");
            return false;
        }
        //我太废物了QAQ
        return true;
    }

    public static Player getToPlayer() {
        return toPlayer;
    }

    public static Player getFromPlayer() {
        return fromPlayer;
    }

    public static int getMoney() {
        return Money;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }

    private static void sendButtonMessage(Player player, String text, String commands, String... args) {
        ComponentBuilder builder = new ComponentBuilder(text)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, commands + args[0]));

        player.spigot().sendMessage(ChatMessageType.CHAT, builder.create());
    }
}
