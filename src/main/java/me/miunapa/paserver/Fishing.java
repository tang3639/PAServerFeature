package me.miunapa.paserver;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import net.md_5.bungee.api.ChatColor;

public class Fishing extends FeatureStart implements Listener {
    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getCaught() instanceof Item) {
            ItemStack itemStack = ((Item) event.getCaught()).getItemStack();
            if (itemStack.getType() == Material.ENCHANTED_BOOK) {
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemStack.getItemMeta();
                if (meta.getEnchants().containsKey(Enchantment.MENDING)) {
                    System.out.println(event.getPlayer().getName() + ChatColor.GOLD
                            + " get Mending EnchantmentBook");
                    event.setCancelled(true);
                } ;
            } ;
        }
    }

    public Fishing() {
        pm.registerEvents(this, pm.getPlugin("PAServer"));
    }
}

