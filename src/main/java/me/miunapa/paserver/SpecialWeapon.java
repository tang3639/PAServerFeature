package me.miunapa.paserver;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpecialWeapon extends FeatureStart implements Listener {

    // 中毒的劍-鑽石劍:30%機率被攻擊的人中毒5秒
    @EventHandler
    public void poisonedSword(EntityDamageByEntityEvent event) {
        Entity victimEntity = event.getEntity(); // 被攻擊的實體(人)
        Entity damager = event.getDamager(); // 攻擊的人
        if (damager instanceof Player) {
            Player player = (Player) damager;
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.DIAMOND_SWORD
                    && item.getItemMeta().getDisplayName().equals("§e中毒的劍")
                    && item.getItemMeta().getEnchants().size() == 0) {
                if (victimEntity instanceof LivingEntity) {
                    LivingEntity victim = (LivingEntity) victimEntity;
                    if (Math.random() < 0.3) {
                        victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
                        if (victim instanceof Player) {
                            ((Player) victim).sendMessage("§2受到了中毒5秒效果!");
                        }
                    }
                }
            }
        }
    }

    public SpecialWeapon() {
        pm.registerEvents(this, pm.getPlugin("PAServer"));
    }
}

