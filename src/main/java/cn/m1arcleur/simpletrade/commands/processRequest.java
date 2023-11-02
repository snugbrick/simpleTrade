package cn.m1arcleur.simpletrade.commands;

import cn.m1arcleur.simpletrade.listener.inventoryCloseListener;
import cn.m1arcleur.simpletrade.lockGS;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * @author MiracleUR
 * @version 1.0.0 2023.11.02 02:37
 * @website github.com/snugbrick;
 */
public class processRequest implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        try {
            Player player = (Player) commandSender;
            //检测是否拥有lock：lock为true
            if (!lockGS.getLock()) {
                commandSender.sendMessage(String.format("The %s already has an active transaction (does not own the lock)", player.getName()));
                return false;
            }
            switch (s) {
                case "simpleview"://如果预览
                    Map<Integer, ItemStack> itemStack = inventoryCloseListener.getItemStackMap();

                    for (int i = 0; i < 9; i++) {
                        ItemStack tradeItem = itemStack.get(i);
                        commandSender.sendMessage(String.format("trading items is: %s", tradeItem));
                    }
                    break;
                case "simpleaccept"://如果同意
                    commandSender.sendMessage("You have accepted the transaction");

                    lockGS.setLock(player, false);
                    break;
                case "simplerefuse"://如果拒绝
                    lockGS.setLock(player, false);
                    commandSender.sendMessage("You have refused the transaction");
                    break;
                default:
                    throw new IllegalStateException("Unknown command");
            }
        } catch (Exception e) {
            commandSender.sendMessage("Did you enter the wrong command? usage: /simple<viewItem|accept|refuse>");
        }
        return true;

    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
