package me.miunapa.paserverfeature;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        FeatureStart featureStart = new FeatureStart();
        featureStart.init();
        getLogger().info("PAServerFeature 啟動  Author:MiunaPA");
    }

    @Override
    public void onDisable() {
    }
}
