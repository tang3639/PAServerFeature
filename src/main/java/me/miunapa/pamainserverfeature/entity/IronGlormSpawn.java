package me.miunapa.pamainserverfeature.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import me.miunapa.pamainserverfeature.FeatureStart;

public class IronGlormSpawn extends FeatureStart implements Listener {

    @EventHandler
    public void mobSpawn(CreatureSpawnEvent event) {
        if (event.getEntityType().equals(EntityType.IRON_GOLEM)) {
            Double random = Math.random();
            if (random > 0.2 && event.getSpawnReason() == SpawnReason.VILLAGE_DEFENSE) {
                event.setCancelled(true);
            }
        }
    }

    public IronGlormSpawn() {
        pm.registerEvents(this, pm.getPlugin("pamainserverfeature"));
    }
}
