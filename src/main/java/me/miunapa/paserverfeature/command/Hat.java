package me.miunapa.paserverfeature.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.Bukkit;

public class Hat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerInventory inv = player.getInventory();
            ItemStack hand = inv.getItemInMainHand();
            ItemStack hat = inv.getHelmet();
            if (hand.getType() == Material.AIR) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        TextComponent.fromLegacyText(ChatColor.GRAY + "你手上沒有東西..."));
                return true;
            }
            if (hand.getAmount() != 1) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        TextComponent.fromLegacyText(ChatColor.RED + "你手上物品數量超過一個 無法穿上"));
                return true;
            }
            inv.setHelmet(hand);
            inv.setItemInMainHand(hat);
            player.updateInventory();
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(ChatColor.GREEN + "帽子穿好囉~"));
        }
        return true;
    }

    public Hat() {
        Bukkit.getPluginCommand("hat").setExecutor(this);
    }
}
