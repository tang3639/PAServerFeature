package me.miunapa.paserverfeature.block;

import me.miunapa.paserverfeature.FeatureStart;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.event.Listener;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Nameable;

public class DispensePlanting extends FeatureStart implements Listener {
    @EventHandler
    public void onDispenseEvent(BlockDispenseEvent event) {
        Block block = event.getBlock();
        String name = ((Nameable) block.getState()).getCustomName();
        if (name == null) {
            name = "noname";
        }
        if (name.equals("farm")) {
            Material selectSeed = detectSelf(block);
            if (selectSeed != null) {
                Character mode = detectModeBlock(block);
                if (mode != 'N') {
                    farming(block, selectSeed, mode);
                    event.setCancelled(true);
                }
            }
        }
    }

    // 檢查發射器是否朝下 & 是否有種子
    public Material detectSelf(Block block) {
        Inventory inventory = ((Dispenser) block.getState()).getInventory();
        BlockFace blockFace =
                ((org.bukkit.material.Dispenser) block.getState().getData()).getFacing();
        if ((blockFace == BlockFace.DOWN)) {
            List<Material> itemList = new ArrayList<Material>();
            itemList = seedList(itemList);
            Material selectSeed = null;
            for (Material item : itemList) {
                if (inventory.contains(item)) {
                    selectSeed = item;
                    break;
                }
            }
            return selectSeed;
        } else {
            return null;
        }
    }

    // 種子列表
    public List<Material> seedList(List<Material> itemList) {
        itemList.add(Material.WHEAT_SEEDS);
        itemList.add(Material.POTATO);
        itemList.add(Material.CARROT);
        itemList.add(Material.BEETROOT_SEEDS);
        return itemList;
    }

    // 檢查發射器上方的模式方塊
    public Character detectModeBlock(Block block) {
        Location upLocation =
                new Location(block.getWorld(), block.getX(), block.getY() + 2, block.getZ());
        Block upBlock = upLocation.getBlock();
        if (upBlock.getType() == Material.HAY_BLOCK) {
            return 'S';
        } else if (upBlock.getType() == Material.IRON_BLOCK) {
            return 'M';
        } else if (upBlock.getType() == Material.DIAMOND_BLOCK) {
            return 'L';
        } else {
            return 'N';
        }
    }

    // 種植種子
    public void farming(Block block, Material selectSeed, Character mode) {
        int min = 0;
        int max = 0;
        if (mode == 'S') {
            min = 0;
            max = 1;
        } else if (mode == 'M') {
            min = -1;
            max = 2;
        } else if (mode == 'L') {
            min = -2;
            max = 3;
        }
        Material seedBlock = null;
        if (selectSeed == Material.WHEAT_SEEDS) {
            seedBlock = Material.WHEAT;
        } else if (selectSeed == Material.POTATO) {
            seedBlock = Material.POTATOES;
        } else if (selectSeed == Material.CARROT) {
            seedBlock = Material.CARROTS;
        } else if (selectSeed == Material.BEETROOT_SEEDS) {
            seedBlock = Material.BEETROOTS;
        }
        Dispenser dispenser = (Dispenser) block.getState();
        Inventory inventory = dispenser.getInventory();
        for (int i = min; i < max; i++) {
            for (int j = min; j < max; j++) {
                Location groundLocation = new Location(block.getWorld(), block.getX() + i,
                        block.getY() - 3, block.getZ() + j);
                Location seedLocation = new Location(block.getWorld(), block.getX() + i,
                        block.getY() - 2, block.getZ() + j);
                if ((groundLocation.getBlock().getType() == Material.FARMLAND)
                        && (seedLocation.getBlock().getType() == Material.AIR)) {
                    for (ItemStack item : inventory.getContents()) {
                        if (item != null) {
                            if (item.getType() == selectSeed) {
                                item.setAmount(item.getAmount() - 1);
                                seedLocation.getBlock().setType(seedBlock);
                                break;
                            }
                        }
                    }
                }
            }
        }
        dispenser.update();
    }

    public DispensePlanting() {
        pm.registerEvents(this, plugin);
    }
}
