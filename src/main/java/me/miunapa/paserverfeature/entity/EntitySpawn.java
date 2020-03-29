package me.miunapa.paserverfeature.entity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import me.miunapa.paserverfeature.FeatureStart;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class EntitySpawn extends FeatureStart implements Listener {
    Integer pigCount = 0;

    @EventHandler
    public void EntitySpawnEvent(CreatureSpawnEvent event) {
        if (event.getEntityType().equals(EntityType.IRON_GOLEM)) {
            Double random = Math.random();
            Double chance = config.getDouble("EntitySpawn.IronGlorm");
            if (random > chance && event.getSpawnReason() == SpawnReason.VILLAGE_DEFENSE) {
                event.setCancelled(true);
            }
        } else if (event.getEntityType().equals(EntityType.PIG_ZOMBIE)) {
            pigCount += 1;
            Double random = Math.random();
            Double chance = config.getDouble("EntitySpawn.ZombiePigman");
            if (random > chance && event.getSpawnReason() == SpawnReason.NETHER_PORTAL) {
                event.setCancelled(true);
            }
            if (pigCount >= config.getInt("EntitySpawn.ZombiePigman_Count")) {
                pigCount = 0;
                Integer clearCount = 0;
                for (Entity e : Bukkit.getWorld("world").getEntities()) {
                    if (e.getType() == EntityType.PIG_ZOMBIE) {
                        if (e.getCustomName() == null) {
                            LivingEntity entity = (LivingEntity) e;
                            entity.setHealth(0.0);
                            clearCount += 1;
                        }
                    } else if (e.getType() == EntityType.SNOWBALL) {
                        e.remove();
                    }
                }
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                            new ComponentBuilder("已自動清潔豬人 : ").color(ChatColor.RED)
                                    .append(clearCount.toString()).color(ChatColor.GREEN).create());
                }
            }
        }
    }

    public EntitySpawn() {
        pm.registerEvents(this, plugin);
        plugin.getConfig().addDefault("EntitySpawn.IronGlorm", 0.2);
        plugin.getConfig().addDefault("EntitySpawn.ZombiePigman", 0.0);
        plugin.getConfig().addDefault("EntitySpawn.ZombiePigman_Count", 1000);
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        plugin.reloadConfig();
    }
}
