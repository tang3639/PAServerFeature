package me.miunapa.paserverfeature.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;

public class GetLoc implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("getloc")) {
            if (args.length == 1) {
                if (Bukkit.getPlayerExact(args[0]) == null) {
                } else {
                    Player player = Bukkit.getPlayerExact(args[0]);
                    Location loc = player.getLocation();
                    String result = ChatColor.YELLOW + player.getName() + ChatColor.GOLD
                            + " 目前在線上 位置 " + ChatColor.LIGHT_PURPLE + loc.getWorld()
                            + ChatColor.DARK_GREEN + " x:" + ChatColor.RED + (int) loc.getX()
                            + ChatColor.DARK_GREEN + " y:" + ChatColor.RED + (int) loc.getY()
                            + ChatColor.DARK_GREEN + " z:" + ChatColor.RED + (int) loc.getZ();
                    sender.sendMessage(result);
                }
            } else {
                sender.sendMessage(ChatColor.RED + "/getloc <ID>");
            }
        }
        return true;
    }

    public void init() {
        Bukkit.getPluginCommand("getloc").setExecutor(this);
    }
}
