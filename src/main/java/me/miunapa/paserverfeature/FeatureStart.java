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
import me.miunapa.paserverfeature.entity.*;
import me.miunapa.paserverfeature.player.*;
import me.miunapa.paserverfeature.world.*;

public class FeatureStart implements CommandExecutor {
    public static Plugin plugin = Bukkit.getPluginManager().getPlugin("paserverfeature");
    public static FileConfiguration config = plugin.getConfig();
    public static PluginManager pm = Bukkit.getPluginManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("paf")) {
            if (args.length >= 1) {
                if (args[0].equals("reload")) {
                    plugin.reloadConfig();
                    config = plugin.getConfig();
                    sender.sendMessage("§apaserverfeature重新讀取完成");
                }
            } else {
                if (sender instanceof Player) {
                    sender.sendMessage("§cpaf 請輸入參數");
                } else {
                    System.out.println("paf 請輸入參數");
                }
            }
        }
        return true;
    }

    public void init() {
        Bukkit.getPluginCommand("paf").setExecutor(this);
        // block
        new BlockPlace();
        new DispensePlanting();
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
