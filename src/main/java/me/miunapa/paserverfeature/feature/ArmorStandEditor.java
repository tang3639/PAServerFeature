package me.miunapa.paserverfeature.feature;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

public class ArmorStandEditor extends FeatureStart implements Listener, CommandExecutor {

    @EventHandler
    public void click(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();
            if (testItem(player.getInventory().getItemInMainHand())) {
                player.sendMessage(armorStand.toString());
            } ;
        } ;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(ChatColor.GRAY + "(ArmorStandEditor)" + ChatColor.RED + "請輸入參數");
            } else {
                if (args[0].equals("get")) {
                    getArmorStandEditorItem(player);
                    player.sendMessage(ChatColor.GREEN + "已給予盔甲架編輯用棒棒");
                } else {
                    player.sendMessage(ChatColor.RED + "目前的參數有:get");
                }
            }
        }
        return true;
    }



    boolean testItem(ItemStack item) {
        if (!(item.getType() == Material.STICK)) {
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

    public ArmorStandEditor() {
        pm.registerEvents(this, plugin);
        Bukkit.getPluginCommand("aseditor").setExecutor(this);
    }
}
