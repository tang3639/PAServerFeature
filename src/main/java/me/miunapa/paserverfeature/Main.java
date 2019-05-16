package me.miunapa.paserverfeature;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        init();
        getLogger().info("PAServerFeature 啟動  Author:MiunaPA");
    }

    @Override
    public void onDisable() {
    }

    public void init() {
        new DispensePlanting();
    }
}
