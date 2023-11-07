package cn.m1arcleur.simpletrade.commands;

import cn.m1arcleur.simpletrade.lockGS;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2023.11.07 20:00
 */
public class tradeCancel implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (s.equalsIgnoreCase("tradeCancel")) {
            if (lockGS.getLock()) {
                commandSender.sendMessage("§cThe trade has now been canceled !");
                lockGS.setLock((Player) commandSender, false);
                return false;
            } else if (!lockGS.getLock()) {
                commandSender.sendMessage("§cThe trade is canceled !");
                return false;
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
