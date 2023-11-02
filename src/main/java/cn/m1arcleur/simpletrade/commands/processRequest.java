package cn.m1arcleur.simpletrade.commands;

import cn.m1arcleur.simpletrade.listener.inventoryCloseListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
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
            if (s.equalsIgnoreCase("simpleRequest")) {
                String request = strings[0];
                if (strings.length == 1) {
                    switch (request) {
                        case "viewItem":
                            Map<Integer, ItemStack> itemStack = inventoryCloseListener.getItemStackMap();

                            for (int i = 0; i < 9; i++) {
                                ItemStack tradeItem = itemStack.get(i);
                                commandSender.sendMessage(String.format("trading items is: %s", tradeItem.getType()));
                            }
                            break;
                        case "accept":
                            break;
                        case "refuse":
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + request);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            commandSender.sendMessage("Did you enter the wrong command? usage: /simpleRequest <viewItem|accept|refuse>");
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
