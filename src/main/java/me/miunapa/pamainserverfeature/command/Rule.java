package me.miunapa.pamainserverfeature.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.miunapa.pamainserverfeature.FeatureStart;
import net.md_5.bungee.api.ChatColor;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;

public class Rule extends FeatureStart implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            for (String s : config.getStringList("rule")) {
                s = ChatColor.translateAlternateColorCodes('&', s);
                player.sendMessage(s);
            }
        }
        return true;
    }

    public Rule() {
        Bukkit.getPluginCommand("rule").setExecutor(this);
        Bukkit.getPluginCommand("rules").setExecutor(this);
        List<String> list = new ArrayList<String>();
        list.add("伺服器規定");
        plugin.getConfig().addDefault("rule", list);
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        plugin.reloadConfig();
    }
}
