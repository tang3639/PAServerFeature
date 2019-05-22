package me.miunapa.paserverfeature;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class DeathExp implements Listener {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("PAServerFeature");
    FileConfiguration config = plugin.getConfig();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (config.getBoolean("DeathClearExp") == true
                && player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY) == true) {
            player.setLevel(0);
            player.setExp(0);
            player.sendMessage("§d你死亡了...經驗已歸零");
        }
        if (player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY) == false) {
            player.sendMessage("§d請注意! 掉落物僅會存在120秒! 盡快返回此地以避免你的物品消失 &7(如果是死在岩漿或虛空就沒救了)");
        }
    }

    public DeathExp() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this, pm.getPlugin("PAServerFeature"));
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
