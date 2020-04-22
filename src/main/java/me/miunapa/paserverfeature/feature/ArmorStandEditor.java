package me.miunapa.paserverfeature.feature;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.miunapa.paserverfeature.FeatureStart;
import net.md_5.bungee.api.ChatColor;

public class ArmorStandEditor extends FeatureStart implements Listener {

    @EventHandler
    public void click(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();
            player.sendMessage(armorStand.toString());
        } ;
    }

    public ArmorStandEditor() {
        pm.registerEvents(this, plugin);
    }

    boolean testItem(ItemStack item) {
        if (!(item.getType() == Material.LANTERN)) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.getDisplayName().equals("§d盔甲架編輯用棒棒")) {
            return false;
        }
        if (!meta.getEnchants().containsKey(Enchantment.ARROW_DAMAGE)) {
            return false;
        } else {
            return true;
        }
    }

    void getArmorStandEditorItem(Player player) {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "盔甲架編輯用棒棒");
        lore.add(ChatColor.GOLD + "拿著這個木棒點盔甲架");
        lore.add(ChatColor.GOLD + "可開啟盔甲架編輯介面");
        itemMeta.setLore(lore);
        itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }
}
