package me.miunapa.paserverfeature;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import me.miunapa.paserverfeature.block.*;
import me.miunapa.paserverfeature.command.*;
import me.miunapa.paserverfeature.entity.*;
import me.miunapa.paserverfeature.player.*;
import me.miunapa.paserverfeature.world.*;

public class FeatureStart {
    public static Plugin plugin = Bukkit.getPluginManager().getPlugin("PAServerFeature");
    public static FileConfiguration config = plugin.getConfig();
    public static PluginManager pm = Bukkit.getPluginManager();

    public void init() {
        // block
        new BlockPlace();
        // new DispensePlanting();
        // command
        new Hat();
        new PlayerIP();
        // entity
        new EntityExplosion();
        new TNTExplosion();
        // player
        new Fishing();
        new PlayerDeath();
        // world
        new PhantomSpawn();
    }
}
