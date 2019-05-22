package me.miunapa.paserverfeature;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class TNTExplore implements Listener {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("PAServerFeature");
    FileConfiguration config = plugin.getConfig();
    Integer taskId = 0;

    @EventHandler
    public void onExplosionPrimeEvent(ExplosionPrimeEvent event) {
        if (config.getBoolean("TNT Explosive.can explosive")) {
            if (config.getBoolean("TNT Explosive.broadcast")
                    && !Bukkit.getScheduler().isQueued(taskId)) {
                taskId = Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                    }
                }, config.getInt("TNT Explosive.broadcast delay")).getTaskId();
                Location loc = event.getEntity().getLocation();
                String message = " §2" + worldConvert(loc.getWorld()) + " " + (int) loc.getX() + " "
                        + (int) loc.getY() + " " + (int) loc.getZ();
                Bukkit.broadcastMessage("§cTNT Explosion 位於:" + message);
            }
        } else {
            event.setCancelled(true);
        }
    }

    public String worldConvert(World world) {
        if (world.getName().equals("world")) {
            return "主世界";
        } else if (world.getName().equals("world_nether")) {
            return "地域";
        } else if (world.getName().equals("world_the_end")) {
            return "終界";
        } else {
            return "未知";
        }
    }

    public TNTExplore() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this, pm.getPlugin("PAServerFeature"));
        plugin.getConfig().addDefault("TNT Explosive.can explosive", true);
        plugin.getConfig().addDefault("TNT Explosive.broadcast", true);
        plugin.getConfig().addDefault("TNT Explosive.broadcast delay", 20);
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        plugin.reloadConfig();
    }
}
