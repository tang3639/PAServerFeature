package me.miunapa.paserver.entity;

import me.miunapa.paserver.FeatureStart;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplosion extends FeatureStart implements Listener {
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getLocation().getWorld().getName().equals("world")) {
            if (event.getEntity() instanceof Creeper || event.getEntity() instanceof Fireball
                    || event.getEntity() instanceof WitherSkull
                    || event.getEntity() instanceof Wither) {
                event.blockList().clear();
            }
        }
    }

    @EventHandler
    public void onWitherDestroy(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof Wither) {
            if (event.getEntity().getLocation().getWorld().getName().equals("world")) {
                event.setCancelled(true);
            }
        }
    }

    public EntityExplosion() {
        pm.registerEvents(this, plugin);
    }
}
