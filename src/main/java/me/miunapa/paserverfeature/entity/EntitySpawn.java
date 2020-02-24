package me.miunapa.paserverfeature.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import me.miunapa.paserverfeature.FeatureStart;

public class EntitySpawn extends FeatureStart implements Listener {

    @EventHandler
    public void EntitySpawnEvent(CreatureSpawnEvent event) {
        if (event.getEntityType().equals(EntityType.IRON_GOLEM)) {
            Double random = Math.random();
            Double chance = config.getDouble("EntitySpawn.IronGlorm");
            if (random > chance && event.getSpawnReason() == SpawnReason.VILLAGE_DEFENSE) {
                event.setCancelled(true);
            }
        } else if (event.getEntityType().equals(EntityType.PIG_ZOMBIE)) {
            Double random = Math.random();
            Double chance = config.getDouble("EntitySpawn.ZombiePigman");
            if (random > chance && event.getSpawnReason() == SpawnReason.NETHER_PORTAL) {
                event.setCancelled(true);
            }
        }
    }

    public EntitySpawn() {
        pm.registerEvents(this, plugin);
        plugin.getConfig().addDefault("EntitySpawn.IronGlorm", 0.2);
        plugin.getConfig().addDefault("EntitySpawn.ZombiePigman", 0.0);
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        plugin.reloadConfig();
    }
}
