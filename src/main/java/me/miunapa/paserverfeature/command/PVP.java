package me.miunapa.paserverfeature.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import me.miunapa.paserverfeature.FeatureStart;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class PVP extends FeatureStart implements Listener, CommandExecutor {
    List<UUID> pvpOff = new ArrayList<UUID>();
    String pvpDisabled = ChatColor.RED + "你目前關閉了PVP!";

    void pvpDisabledMessageBoth(Player damager, Player attacker) {
        attacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                ChatColor.DARK_RED + damager.getName() + " 與你 " + ChatColor.GOLD + "都關閉了PVP"));
    }

    void pvpDisabledMessageDamager(Player damager, Player attacker) {
        attacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                ChatColor.DARK_RED + damager.getName() + ChatColor.GOLD + " 關閉了PVP"));
    }

    void pvpDisabledMessageAttacker(Player attacker) {
        attacker.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(ChatColor.DARK_RED + "你關閉了PVP"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (pvpOff.contains(player.getUniqueId())) {

            } else {
                player.sendMessage(ChatColor.RED)
                pvpOff.add(player.getUniqueId());
            }
        } else {
            sender.sendMessage(ChatColor.RED + "只有玩家能使用該指令");
        }
        return true;

    }


    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager(); // 被打的人
            Player attacker = (Player) event.getEntity(); // 打的人
            if (pvpOff.contains(damager.getUniqueId()) && pvpOff.contains(attacker.getUniqueId())) {
                event.setCancelled(true);
                pvpDisabledMessageBoth(damager, attacker);
            } else if (pvpOff.contains(damager.getUniqueId())) {
                event.setCancelled(true);
                pvpDisabledMessageDamager(damager, attacker);
            } else if (pvpOff.contains(attacker.getUniqueId())) {
                event.setCancelled(true);
                pvpDisabledMessageAttacker(attacker);
            }
        } else if (event.getDamager() instanceof Projectile) {
            Projectile arrow = (Projectile) event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                if (event.getEntity() instanceof Player) {
                    Player damager = (Player) event.getDamager(); // 被打的人
                    Player attacker = (Player) event.getEntity(); // 打的人
                    if (pvpOff.contains(damager.getUniqueId())
                            && pvpOff.contains(attacker.getUniqueId())) {
                        event.setCancelled(true);
                        pvpDisabledMessageBoth(damager, attacker);
                    } else if (pvpOff.contains(damager.getUniqueId())) {
                        event.setCancelled(true);
                        pvpDisabledMessageDamager(damager, attacker);
                    } else if (pvpOff.contains(attacker.getUniqueId())) {
                        event.setCancelled(true);
                        pvpDisabledMessageAttacker(attacker);
                    }
                }
            }
            // checks if damage was done by a potion
        } else if (event.getDamager() instanceof ThrownPotion) {
            ThrownPotion potion = (ThrownPotion) event.getDamager();
            if (potion.getShooter() instanceof Player && event.getEntity() instanceof Player) {
                Player damager = (Player) event.getDamager(); // 被打的人
                Player attacker = (Player) event.getEntity(); // 打的人
                if (pvpOff.contains(damager.getUniqueId())
                        && pvpOff.contains(attacker.getUniqueId())) {
                    event.setCancelled(true);
                    pvpDisabledMessageBoth(damager, attacker);
                } else if (pvpOff.contains(damager.getUniqueId())) {
                    event.setCancelled(true);
                    pvpDisabledMessageDamager(damager, attacker);
                } else if (pvpOff.contains(attacker.getUniqueId())) {
                    event.setCancelled(true);
                    pvpDisabledMessageAttacker(attacker);
                }
            }
        } else if (event.getDamager() instanceof LightningStrike
                && event.getDamager().getMetadata("TRIDENT").size() >= 1
                && event.getEntity() instanceof Player) {
            Player attacker = (Player) event.getEntity();
            if (pvpOff.contains(attacker.getUniqueId())) {
                event.setCancelled(true);
            }
        } else if (event.getDamager() instanceof Firework && event.getEntity() instanceof Player) {
            Player attacker = (Player) event.getEntity();
            if (pvpOff.contains(attacker.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    public PVP() {
        pm.registerEvents(this, plugin);

    }

    /*
     * @EventHandler(ignoreCancelled = true) // fired when a player is shot with a flaming arrow
     * public void onFlameArrow(EntityCombustByEntityEvent event) {
     * 
     * if (event.getCombuster() instanceof Arrow) { Arrow arrow = (Arrow) event.getCombuster(); if
     * (arrow.getShooter() instanceof Player && event.getEntity() instanceof Player) { Player
     * damager = (Player) arrow.getShooter(); Boolean damagerState =
     * PvPToggle.instance.players.get(damager.getUniqueId()); Player attacked = (Player)
     * event.getEntity(); Boolean attackedState =
     * PvPToggle.instance.players.get(attacked.getUniqueId()); if (damagerState) {
     * event.setCancelled(true); } else if (attackedState != null && attackedState) {
     * event.setCancelled(true); } else { Util.setCooldownTime(damager);
     * Util.setCooldownTime(attacked); } } } }
     * 
     * @EventHandler(ignoreCancelled = true) // fired when a splash potion is thrown public void
     * onPotionSplash(PotionSplashEvent event) { if
     * (PvPToggle.blockedWorlds.contains(event.getEntity().getWorld().getName())) { return; }
     * 
     * if (event.getPotion().getShooter() instanceof Player) { for (LivingEntity entity :
     * event.getAffectedEntities()) { if (entity instanceof Player) { Player damager = (Player)
     * event.getPotion().getShooter(); Boolean damagerState =
     * PvPToggle.instance.players.get(damager.getUniqueId()); Player attacked = (Player) entity;
     * Boolean attackedState = PvPToggle.instance.players.get(attacked.getUniqueId()); if (damager
     * != attacked) { if (damagerState) { Collection<LivingEntity> affected =
     * event.getAffectedEntities(); for (LivingEntity ent : affected) { if (ent instanceof Player &&
     * ent != damager) { event.setIntensity(ent, 0); } } Chat.send(damager, "PVP_DISABLED"); } else
     * if (attackedState != null && attackedState) { Collection<LivingEntity> affected =
     * event.getAffectedEntities(); for (LivingEntity ent : affected) { if (ent instanceof Player &&
     * ent != damager) { event.setIntensity(ent, 0); } } Chat.send(damager, "PVP_DISABLED_OTHERS",
     * attacked.getDisplayName()); } else { Util.setCooldownTime(damager);
     * Util.setCooldownTime(attacked); } } } } } }
     * 
     * @EventHandler(ignoreCancelled = true) // fired when lingering potion cloud is active public
     * void onCloudEffects(AreaEffectCloudApplyEvent event) { if
     * (PvPToggle.blockedWorlds.contains(event.getEntity().getWorld().getName())) { return; }
     * 
     * if (event.getEntity().getSource() instanceof Player) { Iterator<LivingEntity> it =
     * event.getAffectedEntities().iterator(); while (it.hasNext()) { LivingEntity entity =
     * it.next(); if (entity instanceof Player && entity != null) { Player damager = (Player)
     * event.getEntity().getSource(); Boolean damagerState =
     * PvPToggle.instance.players.get(damager.getUniqueId()); Player attacked = (Player) entity;
     * Boolean attackedState = PvPToggle.instance.players.get(attacked.getUniqueId()); if
     * (attackedState != null && attackedState) { it.remove(); } else if (damagerState) {
     * it.remove(); } else { Util.setCooldownTime(damager); Util.setCooldownTime(attacked); } } } }
     * }
     * 
     * @EventHandler(ignoreCancelled = true) // fired when a player uses a fishing rod public void
     * onPlayerFishing(PlayerFishEvent event) { if
     * (PvPToggle.blockedWorlds.contains(event.getPlayer().getWorld().getName())) { return; }
     * 
     * if (event.getCaught() instanceof Player) { final Player damager = event.getPlayer(); Boolean
     * damagerState = PvPToggle.instance.players.get(damager.getUniqueId()); final Player attacked =
     * (Player) event.getCaught(); Boolean attackedState =
     * PvPToggle.instance.players.get(attacked.getUniqueId()); if
     * (damager.getInventory().getItemInMainHand().getType() == Material.FISHING_ROD ||
     * damager.getInventory().getItemInOffHand() .getType() == Material.FISHING_ROD) { if
     * (damagerState) { event.setCancelled(true); Chat.send(damager, "PVP_DISABLED"); } else if
     * (attackedState != null && attackedState) { event.setCancelled(true); Chat.send(damager,
     * "PVP_DISABLED_OTHERS", attacked.getDisplayName()); } else { Util.setCooldownTime(damager);
     * Util.setCooldownTime(attacked); } } } }
     * 
     * // Tag lightning strike as from a trident
     * 
     * @EventHandler(ignoreCancelled = true) public void onLightningStrike(LightningStrikeEvent
     * event) { if (event.getCause() == LightningStrikeEvent.Cause.TRIDENT) {
     * event.getLightning().setMetadata("TRIDENT", new FixedMetadataValue(PvPToggle.instance,
     * event.getLightning().getLocation())); } }
     */

}
