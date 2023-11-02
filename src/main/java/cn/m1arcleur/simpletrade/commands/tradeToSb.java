package cn.m1arcleur.simpletrade.commands;

import cn.m1arcleur.simpletrade.lockGS;
import cn.m1arcleur.simpletrade.inventory.fromInv;
import cn.m1arcleur.simpletrade.inventory.toInv;
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

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {
            if (strings.length == 3) {//确认指令有三个参数
                String direction = strings[0].toLowerCase();
                String playerNames = strings[1];
                Player player = Bukkit.getPlayer(playerNames);
                int money = Integer.parseInt(strings[2]);

                toPlayer = player;//给别的方法用的

                //确定玩家和金额非空
                if (player == null || strings[2] == null) {
                    commandSender.sendMessage("Can't find the player you want to trade");
                    return false;
                }
                //确定目标非己
                if (playerNames.equals(commandSender.getName())) {
                    commandSender.sendMessage("Can't trade yourself");
                    return false;
                }
                //确定指令正确
                if (s.equalsIgnoreCase("simpleTrade") && direction.equals("to")) {
                    commandSender.sendMessage(String.format("You have sent a trade request to %s , Money: %d", playerNames, money));
                    player.sendMessage(String.format("You have a trade request from %s , Money: %d", commandSender.getName(), money));

                    //打开输出物品栏，给予锁
                    lockGS.setLock((Player) commandSender, true);
                    toInv.open((Player) commandSender);

                } else if (s.equalsIgnoreCase("simpleTrade") && direction.equals("from")) {
                    commandSender.sendMessage(String.format("You have sent a trade request from %s , Money: %d", playerNames, money));
                    player.sendMessage(String.format("You have a trade request to %s , Money: %d", commandSender.getName(), money));

                    //打开输出物品栏，给予锁
                    lockGS.setLock((Player) commandSender, true);
                    fromInv.open((Player) commandSender);

                } else {
                    commandSender.sendMessage("Did you enter the wrong command? usage: /simpleTrade to|from <playerName> <money>");
                }
            } else {
                commandSender.sendMessage("Did you enter the wrong command? usage: /simpleTrade to|from <playerName> <money>");
                return false;
            }
        } catch (Exception exception) {
            commandSender.sendMessage("Did you enter the wrong command? usage: /simpleTrade to|from <playerName> <money>");
            return false;
        }
        //太废物了写这么多if,QAQ
        return true;
    }

    public static Player getToPlayer() {
        return toPlayer;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
