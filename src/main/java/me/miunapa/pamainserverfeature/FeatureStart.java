package me.miunapa.pamainserverfeature;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import me.miunapa.pamainserverfeature.block.*;
import me.miunapa.pamainserverfeature.command.*;
import me.miunapa.pamainserverfeature.entity.IronGlormSpawn;
import me.miunapa.pamainserverfeature.feature.PlayerLimitBypass;
import me.miunapa.pamainserverfeature.player.*;

public class FeatureStart {
    public static Plugin plugin = Bukkit.getPluginManager().getPlugin("PAMainServerFeature");
    public static FileConfiguration config = plugin.getConfig();
    public static PluginManager pm = Bukkit.getPluginManager();

    public void init() {
        // block
        new BlockPlace();
        // command
        new Hat();
        new PlayerIP();
        new Suicide();
        // entity
        new IronGlormSpawn();
        // feature
        new PlayerLimitBypass();
        // player
        new PlayerDeath();
    }
}
