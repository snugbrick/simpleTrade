package cn.m1arcleur.simpletrade.api.configHandler;

import cn.m1arcleur.simpletrade.SimpleTrade;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2023.11.06 23:04
 */
public class configGetter {
    public static String getConfig(String o) {
        return SimpleTrade.getInstance().getConfig().getString(o);
    }
}
