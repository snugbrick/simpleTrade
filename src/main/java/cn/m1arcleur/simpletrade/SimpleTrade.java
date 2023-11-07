package cn.m1arcleur.simpletrade;

import cn.m1arcleur.simpletrade.commands.processRequest;
import cn.m1arcleur.simpletrade.commands.tradeCancel;
import cn.m1arcleur.simpletrade.commands.tradeToSb;
import cn.m1arcleur.simpletrade.listener.autoLockListener;
import cn.m1arcleur.simpletrade.listener.inventoryCloseListener;
import cn.m1arcleur.simpletrade.listener.sendPackageListener;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/*
 * 已完成：trade指令，to和from的Inv，发送包监听器和Inv关闭监听器
 *
 * 待完成：，查看时给出Inv，超时取消
 *
 * // Economy#depositePlayer(Player, int) 方法用于将金额添加到玩家的钱包中
 *    EconomyResponse response = econ.depositPlayer(player, amount);
 *
 * // Economy#withdrawPlayer(String, int) 方法用于从玩家的钱包中移除金额
 * */
public final class SimpleTrade extends JavaPlugin {

    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    private static SimpleTrade instance;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info(String.valueOf(setupChat()));
        getLogger().info(String.valueOf(setupPermissions()));
        getLogger().info(String.valueOf(setupEconomy()));

        setupPermissions();
        setupChat();
        setupEconomy();

        // 判断Vault前置插件是否存在
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().info("没有发现Vault前置，本插件无法继续使用！");
            // 禁用插件
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // 初始化经济服务
        if (!setupEconomy()) {
            // VaultAPI没有发现其他 经济插件
            getLogger().severe("没有Eco服务");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        commandsRegister();
        listenerRegister();
    }

    public void commandsRegister() {
        getCommand("simpleTrade").setExecutor(new tradeToSb());
        getCommand("simpleview").setExecutor(new processRequest());
        getCommand("simpleaccept").setExecutor(new processRequest());
        getCommand("simplerefuse").setExecutor(new processRequest());
        getCommand("tradeCancel").setExecutor(new tradeCancel());

    }


    public void listenerRegister() {
        getServer().getPluginManager().registerEvents(new inventoryCloseListener(), this);
        getServer().getPluginManager().registerEvents(new sendPackageListener(), this);
        getServer().getPluginManager().registerEvents(new autoLockListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;

        getLogger().info(String.format(" Disabled Version %s", getDescription().getVersion()));

        //econ.depositPlayer(Player,money);
    }

    private boolean setupEconomy() {
        //感谢senko的实例代码 :) -> github.com/Shinyoki/Minecraft-Spigot-learn/blob/master/InvokeThirdPluginAPI/src/main/java/com/senko/invokethirdpluginapi/InvokeThirdPluginAPI.java
        // 获取Economy服务（说简单点，就是看看有没有EssentialX等经济插件被加载了，如果有，VaultAPI就能够提供对应的经济服务）
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager()
                .getRegistration(Economy.class);
        econ = rsp.getProvider();
        return Objects.nonNull(econ);

    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp != null) {
            chat = rsp.getProvider();
        }
        return true;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp != null) {
            perms = rsp.getProvider();
        }
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }

    public static SimpleTrade getInstance() {
        return instance;
    }
}