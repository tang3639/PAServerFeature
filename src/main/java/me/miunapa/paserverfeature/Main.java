package me.miunapa.paserverfeature;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        init();
        getLogger().info("PAServerFeature 啟動  Author:MiunaPA");
        }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("paf")) {
            if (args.length >= 1) {
                if (args[0].equals("reload")) {
                    init();
                    if (sender instanceof Player) {
                        sender.sendMessage("§aPAServerFeature重新讀取完成");
                    } else {
                        System.out.println("PAServerFeature重新讀取完成");
                    }
                }
            }
        }
        return true;
    }

    public void init() {
        new DispensePlanting();
        new ListPlayer();
        new DeathExp();
        new TNTExplosion();
    }
}
