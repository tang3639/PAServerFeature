package me.miunapa.paserverfeature;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Feature implements CommandExecutor {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("PAServerFeature");
    FileConfiguration config = plugin.getConfig();
    PluginManager pm = Bukkit.getPluginManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("paf")) {
            if (args.length >= 1) {
                if (args[0].equals("reload")) {
                    if (sender instanceof Player) {
                        plugin.reloadConfig();
                        config = plugin.getConfig();
                        sender.sendMessage("§aPAServerFeature重新讀取完成");
                    } else {
                        System.out.println("PAServerFeature重新讀取完成");
                    }
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
        new DeathExp();
        new TNTExplosion();
        new DispensePlanting();
        new PhantomSpawn();
    }
}
