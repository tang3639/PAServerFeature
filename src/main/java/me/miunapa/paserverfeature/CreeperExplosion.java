package me.miunapa.paserverfeature;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class CreeperExplosion extends Feature implements Listener {
    @EventHandler
    public void onExplosionPrimeEvent(ExplosionPrimeEvent event) {
        if (event.getEntityType().equals(EntityType.CREEPER)
                && event.getEntity().getLocation().getWorld().getName().equals("world")) {
            event.setCancelled(true);
        }
    }

    public CreeperExplosion() {
        pm.registerEvents(this, pm.getPlugin("PAServerFeature"));
    }
}
