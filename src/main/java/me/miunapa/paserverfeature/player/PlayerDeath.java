package me.miunapa.paserverfeature.player;

import me.miunapa.paserverfeature.FeatureStart;
import me.miunapa.paserverfeature.Main;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.EconomyResponse;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class PlayerDeath extends FeatureStart implements Listener, CommandExecutor {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("PAServerFeature");
    FileConfiguration config = plugin.getConfig();
    File keepFile = new File(plugin.getDataFolder(), "keep.yml");
    YamlConfiguration keep = YamlConfiguration.loadConfiguration(keepFile);

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (config.getBoolean("DeathClearExp") == true
                && player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY) == true) {
            Integer level = player.getLevel();
            if (level > 13) {
                level -= 3;
                player.setLevel(level);
                player.sendMessage("§d你死亡了...  §7損失了§e3§7等經驗 剩下§e" + level + "§7等");
            } else if (level <= 13 && level > 10) {
                Integer less = level - 10;
                level = 10;
                player.setLevel(level);
                player.sendMessage("§d你死亡了...  §7損失了§e" + less + "§7等經驗 剩下§e" + level + "§7等");
            } else {
                player.sendMessage("§7因為等級未超過10等所以沒有死亡經驗懲罰");
            }
        }
        if (player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY) == false) {
            if (keep.getBoolean(player.getUniqueId().toString())) {
                Double bal = Main.getEconomy().getBalance(player);
                if (bal >= 100.0) {
                    bal -= 100;
                    EconomyResponse r = Main.getEconomy().withdrawPlayer(player, 100);
                    if (r.transactionSuccess()) {
                        event.setKeepInventory(true);
                        player.sendMessage(
                                ChatColor.GOLD + "從你帳號扣除了100元,防止了死亡噴裝 " + ChatColor.YELLOW + "你剩下 "
                                        + ChatColor.RED + bal + ChatColor.YELLOW + " 元");
                    } else {
                        player.sendMessage(String.format("錯誤: %s", r.errorMessage));
                    }
                } else {
                    player.sendMessage(ChatColor.GOLD + "帳號餘額不到100元! 你的物品噴掉了!");
                    player.sendMessage("§d請注意! 掉落物僅會存在120秒! 盡快返回此地以避免你的物品消失");
                } ;
            } else {
                player.sendMessage("§d請注意! 掉落物僅會存在120秒! 盡快返回此地以避免你的物品消失");
            }
        } else {
            player.sendMessage("§d請注意! 掉落物僅會存在120秒! 盡快返回此地以避免你的物品消失");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (command.getName().equals("keep")) {
                Player player = (Player) sender;
                String playerUUID = player.getUniqueId().toString();
                Boolean deathKeep = true;
                deathKeep = keep.getBoolean(playerUUID);
                if (keep.contains(playerUUID) == true) {
                    deathKeep = keep.getBoolean(playerUUID);
                }
                if (deathKeep) {
                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "防噴裝系統"
                            + ChatColor.GRAY + "] " + ChatColor.RED + "已關閉 現在在會噴裝的世界死亡 會照常噴裝!");
                    deathKeep = false;
                    keep.set(playerUUID, deathKeep);
                } else {
                    player.sendMessage(
                            ChatColor.GRAY + "[" + ChatColor.GOLD + "防噴裝系統" + ChatColor.GRAY + "] "
                                    + ChatColor.GREEN + "已開啟 現在在會噴裝的世界死亡 " + ChatColor.YELLOW
                                    + "會自動扣除100塊來防止噴裝" + ChatColor.GRAY + "(若金額不足還是會照噴)");
                    deathKeep = true;
                    keep.set(playerUUID, deathKeep);
                }
                saveKeepFile();
            }
        }
        return true;
    }

    public void saveKeepFile() {
        try {
            keep.save(keepFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PlayerDeath() {
        pm.registerEvents(this, plugin);
        Bukkit.getPluginCommand("keep").setExecutor(this);
        if (!keepFile.exists()) {
            saveKeepFile();
        }
        plugin.getConfig().addDefault("DeathClearExp", true);
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        plugin.reloadConfig();

    }

    public Double getLevelTotalExp(Integer level, Integer nowExp) {
        Double result = 0.0;
        if (level >= 0 && level <= 16) {
            result = Math.pow(level, 2) + 6 * level;
        } else if (level >= 17 && level <= 31) {
            result = 2.5 * Math.pow(level, 2) - 40.5 * level + 360;
        } else {
            result = 4.5 * Math.pow(level, 2) - 162.5 * level + 2220;
        }
        return result;
    }
}
