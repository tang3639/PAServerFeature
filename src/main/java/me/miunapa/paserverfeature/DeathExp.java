package me.miunapa.paserverfeature;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class DeathExp implements Listener {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("PATeleport");
    FileConfiguration config = plugin.getConfig();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (plugin.getConfig().getBoolean("DeathClearExp") == true) {
        }
    }

    public DeathExp() {
        plugin.getConfig().addDefault("DeathClearExp", true);
    }
}
