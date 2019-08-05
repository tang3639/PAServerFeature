package me.miunapa.paserverfeature.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Bukkit;


public class Hat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = ChatColor.LIGHT_PURPLE + "[" + ChatColor.AQUA + "PA-Hat"
                + ChatColor.LIGHT_PURPLE + "] " + ChatColor.RESET;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerInventory inv = player.getInventory();
            ItemStack hand = inv.getItemInMainHand();
            ItemStack hat = inv.getHelmet();
            if (hand.getType() == Material.AIR) {
                player.sendMessage(prefix + ChatColor.GRAY + "你手上沒有東西...");
                return true;
            }
            if (hand.getAmount() != 1) {
                player.sendMessage(prefix + ChatColor.RED + "你手上物品數量超過一個 無法穿上");
                return true;
            }
            if (hat != null && hat.getType() != Material.AIR) {
                if (hat.getItemMeta().getDisplayName().equals("§e西伯利亞礦工帽")) {
                    player.sendMessage(prefix + ChatColor.RED + "請先脫下礦工帽再使用");
                    return true;
                }
            }
            if (hand.getItemMeta().getDisplayName().equals("§e西伯利亞礦工帽")) {
                player.sendMessage(prefix + ChatColor.RED + "不能使用此指令穿上礦工帽");
                return true;
            }
            inv.setHelmet(hand);
            inv.setItemInMainHand(hat);
            player.updateInventory();
            player.sendMessage(prefix + ChatColor.GREEN + "帽子穿好囉~");
        }
        return true;
    }

    public Hat() {
        Bukkit.getPluginCommand("hat").setExecutor(this);
    }
}
