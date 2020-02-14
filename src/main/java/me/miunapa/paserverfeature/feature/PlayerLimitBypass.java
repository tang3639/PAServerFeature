package me.miunapa.paserverfeature.feature;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import me.miunapa.paserverfeature.FeatureStart;
import net.md_5.bungee.api.ChatColor;

public class PlayerLimitBypass extends FeatureStart implements Listener {

    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
            if (event.getPlayer().hasPermission("paserverfeature.bypasslimit")) {
                event.allow();
                event.getPlayer().sendMessage(ChatColor.GOLD + "你擁有滿人加入伺服器的權限");
            } else if (!event.getPlayer().hasPlayedBefore()) {
                event.allow();
            }
        }
    }

    public PlayerLimitBypass() {
        pm.registerEvents(this, plugin);
    }
}
