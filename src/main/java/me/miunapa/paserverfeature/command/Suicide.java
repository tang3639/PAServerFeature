package me.miunapa.paserverfeature.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;

public class Suicide implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1f, 1f);
            player.setHealth(0.0);
            player.sendMessage(ChatColor.RED + "生命線協談專線 1995");
        } else {
            sender.sendMessage(ChatColor.RED + "只有玩家可以自殺");
        }
        return true;
    }

    public Suicide() {
        Bukkit.getPluginCommand("suicide").setExecutor(this);
    }
}
