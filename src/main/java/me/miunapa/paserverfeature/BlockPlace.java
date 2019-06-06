package me.miunapa.paserverfeature;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import net.md_5.bungee.api.ChatColor;

public class BlockPlace extends Feature implements Listener {
    List<Material> trippedLog = new ArrayList<Material>();

    @EventHandler
    public void onTrippedLog(BlockPlaceEvent event) {
        if (trippedLog.contains(event.getBlock().getType())) {
            event.getPlayer().sendMessage(
                    ChatColor.GOLD + "若要取得剝皮原木" + ChatColor.WHITE + " 請將原木or木塊以壓力版合成方式取得");
            event.setCancelled(true);
        }
    }

    public void trippedLogListInit() {
        trippedLog.add(Material.STRIPPED_ACACIA_LOG);
        trippedLog.add(Material.STRIPPED_ACACIA_WOOD);
        trippedLog.add(Material.STRIPPED_BIRCH_LOG);
        trippedLog.add(Material.STRIPPED_BIRCH_WOOD);
        trippedLog.add(Material.STRIPPED_DARK_OAK_LOG);
        trippedLog.add(Material.STRIPPED_DARK_OAK_WOOD);
        trippedLog.add(Material.STRIPPED_JUNGLE_LOG);
        trippedLog.add(Material.STRIPPED_JUNGLE_WOOD);
        trippedLog.add(Material.STRIPPED_OAK_LOG);
        trippedLog.add(Material.STRIPPED_OAK_WOOD);
        trippedLog.add(Material.STRIPPED_SPRUCE_LOG);
        trippedLog.add(Material.STRIPPED_SPRUCE_WOOD);
    }

    public BlockPlace() {
        trippedLogListInit();
        pm.registerEvents(this, pm.getPlugin("PAServerFeature"));
    }
}
