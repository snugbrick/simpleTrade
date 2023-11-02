package cn.m1arcleur.simpletrade.commands;

import cn.m1arcleur.simpletrade.SimpleTrade;
import cn.m1arcleur.simpletrade.listener.inventoryCloseListener;
import cn.m1arcleur.simpletrade.lockGS;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
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
            Player thisPlayer = (Player) commandSender;
            Player senderPlayer = tradeToSb.getFromPlayer();
            //检测是否拥有lock：lock为true
            if (!lockGS.getLock()) {
                commandSender.sendMessage(String.format("The %s already has an active transaction (does not own the lock)", thisPlayer.getName()));
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
                    if (getMoney(thisPlayer) > tradeToSb.getMoney() && thisPlayer.getInventory().firstEmpty() != -1) {
                        for (int i = 0; i < 9; i++) {
                            ItemStack tradeItem = inventoryCloseListener.getItemStackMap().get(i);
                            thisPlayer.getInventory().addItem(tradeItem);
                        }
                        addMoney(senderPlayer, tradeToSb.getMoney());
                        removeMoney(thisPlayer, tradeToSb.getMoney());
                    }
                    commandSender.sendMessage("You have accepted the transaction");
                    senderPlayer.sendMessage("You have accepted the transaction");

                    lockGS.setLock(thisPlayer, false);
                    break;
                case "simplerefuse"://如果拒绝
                    if (senderPlayer.getInventory().firstEmpty() != -1) {
                        for (int i = 0; i < 9; i++) {
                            ItemStack tradeItem = inventoryCloseListener.getItemStackMap().get(i);
                            senderPlayer.getInventory().addItem(tradeItem);
                        }
                    }
                    commandSender.sendMessage("You have refused the transaction");
                    senderPlayer.sendMessage("You have refused the transaction");

                    lockGS.setLock(thisPlayer, false);
                    break;
                default:
                    throw new IllegalStateException("Unknown command");
            }
        } catch (Exception e) {
            commandSender.sendMessage("Did you enter the wrong command? usage: /simple<viewItem|accept|refuse>");
        }
        return true;
        /*
         * * // Economy#depositePlayer(Player, int) 方法用于将金额添加到玩家的钱包中
         *    EconomyResponse response = econ.depositPlayer(player, amount);
         *
         * // Economy#withdrawPlayer(String, int) 方法用于从玩家的钱包中移除金额
         * */
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }

    private boolean addMoney(Player player, int amount) {
        try {
            EconomyResponse response = SimpleTrade.getEconomy().depositPlayer(player, amount);
            Bukkit.getLogger().info(String.format("Player %s earned %d dollars through trading", player.getName(), amount));
            if (response.transactionSuccess()) {
                player.sendMessage(String.format("You now have %s", SimpleTrade.getEconomy().getBalance(player)));
                return true;
            } else {
                player.sendMessage(response.errorMessage);
                return false;
            }
        } catch (
                NumberFormatException e) {
            player.sendMessage("The amount you enter is not a number!");
            return false;
        }
    }

    private boolean removeMoney(Player player, int amount) {
        try {
            if (amount > SimpleTrade.getEconomy().getBalance(player)) return false;
            EconomyResponse response = SimpleTrade.getEconomy().withdrawPlayer(player, amount);
            Bukkit.getLogger().info(String.format("Player %s spent %d on a transaction", player.getName(), amount));
            if (response.transactionSuccess()) {
                player.sendMessage(String.format("You now have %s", SimpleTrade.getEconomy().getBalance(player)));
                return true;
            } else {
                player.sendMessage(response.errorMessage);
                return false;
            }
        } catch (NumberFormatException e) {
            player.sendMessage("The amount you enter is not a number!");
            return false;
        }
    }

    private double getMoney(Player player) {

        return SimpleTrade.getEconomy().getBalance(player);
    }
}
