package me.miunapa.paserver;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class PhantomSpawn extends Main implements Listener {
    @EventHandler
    public void mobSpawn(EntitySpawnEvent event) {
        if (!config.getBoolean("PhantomSpawn")) {
            if (event.getEntityType().equals(EntityType.PHANTOM)) {
                event.setCancelled(true);
            }
        }
    }

    public PhantomSpawn() {
        pm.registerEvents(this, pm.getPlugin("PAServerFeature"));
        plugin.getConfig().addDefault("PhantomSpawn", false);
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        plugin.reloadConfig();
    }
}
