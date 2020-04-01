package me.miunapa.paserverfeature.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTListCompound;
import net.md_5.bungee.api.ChatColor;

public class Gethead implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 2) {
                Player player = (Player) sender;
                ItemStack head = getHead(args[0], args[1]);
                player.getInventory().addItem(head);
                player.sendMessage(ChatColor.YELLOW + "已給予自訂頭顱");
            }
        }
        return true;

    }

    public Gethead() {
        Bukkit.getPluginCommand("gh").setExecutor(this);
    }

    public ItemStack getHead(String uuid, String texture) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        NBTItem nbti = new NBTItem(head);
        NBTCompound skullOwner = nbti.addCompound("SkullOwner");
        skullOwner.setString("Id", uuid);
        NBTListCompound textureNbt =
                skullOwner.addCompound("Properties").getCompoundList("textures").addCompound();
        textureNbt.setString("Value", texture);
        head = nbti.getItem();
        return head;
    }
}
