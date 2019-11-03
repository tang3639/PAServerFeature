package me.miunapa.paserverfeature.block;

import me.miunapa.paserverfeature.FeatureStart;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.event.Listener;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Nameable;

public class DispensePlanting extends FeatureStart implements Listener {
    List<Material> seedList = Arrays.asList(Material.WHEAT_SEEDS, Material.POTATO, Material.CARROT,
            Material.BEETROOT_SEEDS);
    Map<Material, Material> growSeed = new HashMap<Material, Material>();

    @EventHandler
    public void onDispenseEvent(BlockDispenseEvent event) {
        Block block = event.getBlock();
        // 偵測 發射器名字 --> 發射器方向
        String name = ((Nameable) block.getState()).getCustomName();
        if (name == null) {
            name = "noname";
            return;
        }
        if (!name.equals("farm")) {
            return;
        }
        BlockFace blockFace =
                ((org.bukkit.material.Dispenser) block.getState().getData()).getFacing();
        if (!(blockFace == BlockFace.DOWN)) {
            return;
        }
        event.setCancelled(true);

        // 偵測上方模式方塊
        Integer range = null;
        Location upLocation =
                new Location(block.getWorld(), block.getX(), block.getY() + 2, block.getZ());
        Block upBlock = upLocation.getBlock();
        if (upBlock.getType() == Material.HAY_BLOCK) {
            range = 0;
        } else if (upBlock.getType() == Material.IRON_BLOCK) {
            range = 1;
        } else if (upBlock.getType() == Material.DIAMOND_BLOCK) {
            range = 2;
        }
        if (range != null) {
            farming(block, range);
        }
    }

    public void farming(Block block, Integer range) {
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                Location groundLocation = new Location(block.getWorld(), block.getX() + i,
                        block.getY() - 3, block.getZ() + j);
                Location seedLocation = new Location(block.getWorld(), block.getX() + i,
                        block.getY() - 2, block.getZ() + j);
                if ((groundLocation.getBlock().getType() == Material.FARMLAND)
                        && (seedLocation.getBlock().getType() == Material.AIR)) {
                    // 播種
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            Dispenser dispenser = (Dispenser) block.getState();
                            Inventory inventory = dispenser.getSnapshotInventory();
                            ItemStack[] contents = inventory.getContents();
                            for (int k = 0; k < contents.length; k++) {
                                if (contents[k] != null) {
                                    Material selectSeed = contents[k].getType();
                                    if (seedList.contains(selectSeed)) {
                                        Material seedBlock = growSeed.get(selectSeed);
                                        contents[k].setAmount(contents[k].getAmount() - 1);
                                        inventory.setContents(contents);
                                        seedLocation.getBlock().setType(seedBlock);
                                        break;
                                    }
                                }
                            }
                            System.out.println(dispenser.update());
                        }
                    });
                }
            }
        }
    }

    public DispensePlanting() {
        pm.registerEvents(this, plugin);
        growSeed.put(Material.WHEAT_SEEDS, Material.WHEAT);
        growSeed.put(Material.POTATO, Material.POTATOES);
        growSeed.put(Material.CARROT, Material.CARROTS);
        growSeed.put(Material.BEETROOT_SEEDS, Material.BEETROOTS);
    }
}
