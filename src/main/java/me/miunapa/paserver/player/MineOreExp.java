package me.miunapa.paserver.player;

import me.miunapa.paserver.FeatureStart;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExpEvent;

public class MineOreExp extends FeatureStart implements Listener {

    @EventHandler
    public void onBlockExpEvent(BlockExpEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.COAL_ORE) {
            event.setExpToDrop((int) (event.getExpToDrop() * 1));
        } else if (block.getType() == Material.DIAMOND_ORE) {
            event.setExpToDrop(event.getExpToDrop() * 2);
        } else if (block.getType() == Material.EMERALD_ORE) {
            event.setExpToDrop(event.getExpToDrop() * 4);
        } else if (block.getType() == Material.LAPIS_ORE) {
            event.setExpToDrop((int) (event.getExpToDrop() * 1.2));
        } else if (block.getType() == Material.REDSTONE_ORE) {
            event.setExpToDrop((int) (event.getExpToDrop() * 1.2));
        } else if (block.getType() == Material.SPAWNER) {
            event.setExpToDrop((int) (event.getExpToDrop() * 6));
        }
    }

    public MineOreExp() {
        pm.registerEvents(this, plugin);
    }
}
