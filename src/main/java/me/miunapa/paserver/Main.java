package me.miunapa.paserver;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("PAServer");
    FileConfiguration config = plugin.getConfig();
    PluginManager pm = Bukkit.getPluginManager();

    @Override
    public void onEnable() {
        // 指令
        Bukkit.getPluginCommand("paf").setExecutor(this);
        // 功能啟動
        new PlayerDeath();
        new TNTExplosion();
        new DispensePlanting();
        new PhantomSpawn();
        new EntityExplosion();
        new Fishing();
        new BlockPlace();
        new MineOreExp();
        new SpecialWeapon();
        // 發送訊息
        getLogger().info("PAServer 啟動  Author:MiunaPA");
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("paf")) {
            if (args.length >= 1) {
                if (args[0].equals("reload")) {
                    plugin.reloadConfig();
                    config = plugin.getConfig();
                    sender.sendMessage("§aPAServer重新讀取完成");
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
}
