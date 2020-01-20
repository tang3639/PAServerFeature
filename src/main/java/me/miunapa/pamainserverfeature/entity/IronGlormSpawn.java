package me.miunapa.pamainserverfeature.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import me.miunapa.pamainserverfeature.FeatureStart;

public class IronGlormSpawn extends FeatureStart implements Listener {

    @EventHandler
    public void ironGlormSpawnEvent(CreatureSpawnEvent event) {
        if (event.getEntityType().equals(EntityType.IRON_GOLEM)) {
            Double random = Math.random();
            Double chance = config.getDouble("IronGlormSpawn");
            if (random > chance && event.getSpawnReason() == SpawnReason.VILLAGE_DEFENSE) {
                event.setCancelled(true);
            }
        }
    }

    public IronGlormSpawn() {
        pm.registerEvents(this, plugin);
        plugin.getConfig().addDefault("IronGlormSpawn", 0.2);
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        plugin.reloadConfig();
    }
}
