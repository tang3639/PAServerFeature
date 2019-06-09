package me.miunapa.paserver.player;

import me.miunapa.paserver.FeatureStart;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class PlayerDeath extends FeatureStart implements Listener {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("PAServer");
    // private static Economy econ = null;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (config.getBoolean("DeathClearExp") == true
                && player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY) == true) {
            Integer level = player.getLevel();
            if (level > 10) {
                level -= 3;
                player.setLevel(level);
                player.sendMessage("§d你死亡了...  §7損失了§e3§7等經驗 剩下§e" + level + "§7等");
            } else {
                player.sendMessage("§7因為等級低於10等所以沒有死亡經驗懲罰");
            }
        }
        if (player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY) == false) {
            player.sendMessage("§d請注意! 掉落物僅會存在120秒! 盡快返回此地以避免你的物品消失 \n§7(如果是死在岩漿或虛空就沒救了)");
        }
    }

    public PlayerDeath() {
        // econ =
        // plugin.getServer().getServicesManager().getRegistration(Economy.class).getProvider();
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
