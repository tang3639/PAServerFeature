package me.miunapa.paserverfeature;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ListPlayer implements CommandExecutor {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("PAServerFeature");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("list")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage("§e目前共有 §c" + Bukkit.getOnlinePlayers().size()
                        + " §e個玩家在線上,最大容許在線人數為 §c" + Bukkit.getMaxPlayers() + " §e個玩家");
            } else {
                Integer onlinePlayer = Bukkit.getOnlinePlayers().size();
                if (onlinePlayer == 0) {
                    System.out.println("目前沒有玩家在線上,最大容許在線人數為 " + Bukkit.getMaxPlayers() + " 個玩家");
                } else {
                    System.out.println("目前共有 " + onlinePlayer + " 個玩家在線上,最大容許在線人數為 "
                            + Bukkit.getMaxPlayers() + " 個玩家");
                }
            }
        }
        return true;
    }

    public ListPlayer() {
        Bukkit.getPluginCommand("list").setExecutor(this);
    }
}
