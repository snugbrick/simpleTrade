package cn.m1arcleur.simpletrade;

import cn.m1arcleur.simpletrade.api.configHandler.configGetter;
import cn.m1arcleur.simpletrade.commands.processRequest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2023.11.10 00:14
 */
public class theTaxation {
    private static final int taxesAmount = Integer.parseInt(configGetter.getConfig("The-amount-of-taxes"));

    //税收方法，返回税收后的钱
    public static int deductAmount(int amount) {
        int taxes = amount * taxesAmount;
        Bukkit.getServer().getLogger().info("Deducted from the transaction: " + taxes);

        Player tasPayer = Bukkit.getPlayer(configGetter.getConfig("taxes-to"));
        processRequest.addMoney(tasPayer,taxes);

        return amount - taxes;
    }
}
