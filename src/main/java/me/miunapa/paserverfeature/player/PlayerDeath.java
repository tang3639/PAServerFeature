package me.miunapa.paserverfeature.player;

import me.miunapa.paserverfeature.FeatureStart;
import me.miunapa.paserverfeature.Main;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath extends FeatureStart implements Listener {

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
            Double bal = Main.getEconomy().getBalance(player);
            if (bal >= 20.0) {
                bal -= 20;
                EconomyResponse r = Main.getEconomy().withdrawPlayer(player, 20);
                if (r.transactionSuccess()) {
                    event.setKeepInventory(true);
                    player.sendMessage(ChatColor.GOLD + "從你帳號扣除了20元,防止了死亡噴裝 " + ChatColor.YELLOW
                            + "你剩下 " + ChatColor.RED + bal + ChatColor.YELLOW + " 元");
                } else {
                    player.sendMessage(String.format("錯誤: %s", r.errorMessage));
                }
            } else {
                player.sendMessage(ChatColor.GOLD + "帳號餘額不到20元! 你的物品噴掉了!");
                player.sendMessage("§d請注意! 掉落物僅會存在120秒! 盡快返回此地以避免你的物品消失");
            } ;
        }
    }

    public PlayerDeath() {
        pm.registerEvents(this, plugin);
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
