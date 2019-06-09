package me.miunapa.paserver;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class TNTExplosion extends Main implements Listener {
    Integer taskId = 0;

    @EventHandler
    public void onExplosionPrimeEvent(EntityExplodeEvent event) {
        if (event.getLocation().getWorld().getName().equals("world")
                && event.getEntity() instanceof TNTPrimed) {
            if (config.getBoolean("TNT Explosive.explosive")) {
                if (!config.getBoolean("TNT Explosive.destroyBlock")) {
                    event.blockList().clear();
                }
                if (config.getBoolean("TNT Explosive.broadcast")
                        && !Bukkit.getScheduler().isQueued(taskId)) {
                    taskId = Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                        }
                    }, config.getInt("TNT Explosive.broadcast delay")).getTaskId();
                    Location loc = event.getEntity().getLocation();
                    String message = " §2" + worldConvert(loc.getWorld()) + " " + (int) loc.getX()
                            + " " + (int) loc.getY() + " " + (int) loc.getZ();
                    Bukkit.broadcastMessage("§cTNT Explosion 位於:" + message);
                }
            } else {
                event.setCancelled(true);
            }
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

    public TNTExplosion() {
        pm.registerEvents(this, pm.getPlugin("PAServer"));
        plugin.getConfig().addDefault("TNT Explosive.explosive", true);
        plugin.getConfig().addDefault("TNT Explosive.destroyBlock", true);
        plugin.getConfig().addDefault("TNT Explosive.broadcast", true);
        plugin.getConfig().addDefault("TNT Explosive.broadcast delay", 20);
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        plugin.reloadConfig();
    }
}
