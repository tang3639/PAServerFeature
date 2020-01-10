package me.miunapa.pamainserverfeature.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerIP implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "請輸入ID/UUID");
        } else {
            Player player = org.bukkit.Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + "該玩家不在線");
            } else {
                String ip = player.getAddress().toString();
                ip = ip.replace("/", "").substring(0, ip.indexOf(":") - 1);
                sender.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GOLD + " 的IP為 "
                        + ChatColor.RED + ip);
            }
        }
        return true;
    }

    public PlayerIP() {
        Bukkit.getPluginCommand("getip").setExecutor(this);
    }
}
