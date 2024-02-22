package cn.m1arcleur.simpletrade.api.filesHandler;


import cn.m1arcleur.simpletrade.SimpleTrade;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2023.11.10 23:18
 */
public class LangGetter {
    public static String getLang(String key) {
        File langFile = new File(SimpleTrade.getInstance().getDataFolder(), "lang.yml");
        FileConfiguration langConfig = YamlConfiguration.loadConfiguration(langFile);

        // 尝试重新加载配置文件
        try {
            langConfig.load(langFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace(); // 在控制台打印异常信息
            return "Error: Unable to load lang file"; // 返回默认值或者错误提示
        }

        return langConfig.getString(key); // 返回键对应的值
    }
}

