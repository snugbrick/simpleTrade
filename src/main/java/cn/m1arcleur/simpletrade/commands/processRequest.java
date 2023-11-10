package cn.m1arcleur.simpletrade.commands;

import cn.m1arcleur.simpletrade.SimpleTrade;
import cn.m1arcleur.simpletrade.listener.inventoryCloseListener;
import cn.m1arcleur.simpletrade.lockGS;
import cn.m1arcleur.simpletrade.theTaxation;
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
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2023.11.06 23:04
 */
public class processRequest implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        try {
            Player thisPlayer = (Player) commandSender;
            Player senderPlayer = tradeToSb.getFromPlayer();
            //检测是否拥有lock：lock为true
            if (lockGS.getLock()) {
                switch (s) {
                    case "simpleview"://如果预览
                        Map<Integer, ItemStack> itemStack = inventoryCloseListener.getItemStackMap();

                        for (int i = 0; i < 9; i++) {
                            ItemStack tradeItem = itemStack.get(i);
                            commandSender.sendMessage(String.format("§a§ltrading items is: §r§f%s", tradeItem));
                        }
                        break;
                    case "simpleaccept"://如果同意
                        if (getMoney(thisPlayer) >= tradeToSb.getMoney() && thisPlayer.getInventory().firstEmpty() != -1) {
                            for (int i = 0; i < 9; i++) {
                                ItemStack tradeItem = inventoryCloseListener.getItemStackMap().get(i);
                                if (tradeItem != null) thisPlayer.getInventory().addItem(tradeItem);
                            }
                            int beforeTaxed = tradeToSb.getMoney();
                            int afterTaxed = theTaxation.deductAmount(beforeTaxed);//税收

                            addMoney(senderPlayer, afterTaxed);
                            removeMoney(thisPlayer, tradeToSb.getMoney());

                            commandSender.sendMessage("§aYou have accepted the transaction");
                            senderPlayer.sendMessage("§aYou have accepted the transaction");
                        } else {
                            commandSender.sendMessage("§cYou do not have enough money or the inventory is full");
                            return false;
                        }

                        lockGS.setLock(thisPlayer, false);
                        break;
                    case "simplerefuse"://如果拒绝
                        if (senderPlayer.getInventory().firstEmpty() != -1) {
                            for (int i = 0; i < 9; i++) {
                                ItemStack tradeItem = inventoryCloseListener.getItemStackMap().get(i);
                                if (tradeItem != null) senderPlayer.getInventory().addItem(tradeItem);
                            }

                            commandSender.sendMessage("§aYou have refused the transaction");
                            senderPlayer.sendMessage("§aYou have refused the transaction");
                        } else {
                            commandSender.sendMessage("§cThe inventory is full");
                            return false;
                        }

                        lockGS.setLock(thisPlayer, false);
                        break;
                    default:
                        throw new IllegalStateException("Unknown command");
                }
            } else {
                commandSender.sendMessage(String.format("§c§lThe %s already has an active transaction (does not own the lock)", thisPlayer.getName()));
                return false;
            }

        } catch (Exception e) {
            commandSender.sendMessage("§c§lDid you enter the wrong command? §r§fusage: /simple<viewItem|accept|refuse>");
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }

    public static boolean addMoney(Player player, int amount) {
        try {
            EconomyResponse response = SimpleTrade.getEconomy().depositPlayer(player, amount);
            Bukkit.getLogger().info(String.format("§aPlayer %s earned %d dollars through trading", player.getName(), amount));
            if (response.transactionSuccess()) {
                player.sendMessage(String.format("§bYou now have %s", SimpleTrade.getEconomy().getBalance(player)));
                return true;
            } else {
                player.sendMessage(response.errorMessage);
                return false;
            }
        } catch (
                NumberFormatException e) {
            player.sendMessage("§cThe amount you enter is not a number!");
            return false;
        }
    }

    private boolean removeMoney(Player player, int amount) {
        try {
            if (amount > SimpleTrade.getEconomy().getBalance(player)) return false;
            EconomyResponse response = SimpleTrade.getEconomy().withdrawPlayer(player, amount);
            Bukkit.getLogger().info(String.format("§aPlayer %s spent %d on a transaction", player.getName(), amount));
            if (response.transactionSuccess()) {
                player.sendMessage(String.format("§bYou now have %s", SimpleTrade.getEconomy().getBalance(player)));
                return true;
            } else {
                player.sendMessage(response.errorMessage);
                return false;
            }
        } catch (NumberFormatException e) {
            player.sendMessage("§cThe amount you enter is not a number!");
            return false;
        }
    }

    private double getMoney(Player player) {

        return SimpleTrade.getEconomy().getBalance(player);
    }
}
