package me.miunapa.paserverfeature;

import org.bukkit.plugin.java.JavaPlugin;

public class PAServerFeature extends JavaPlugin {
    @Override
    public void onEnable() {
        FeatureStart featureStart = new FeatureStart();
        featureStart.init();
        getLogger().info("paserverfeature 啟動  Author:MiunaPA");
    }

    @Override
    public void onDisable() {
    }
}
