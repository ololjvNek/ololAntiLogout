package pl.ololjvNek.antilogout.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.ololjvNek.antilogout.Main;
import pl.ololjvNek.antilogout.data.Combat;
import pl.ololjvNek.antilogout.managers.CombatManager;
import pl.ololjvNek.antilogout.utils.Util;

import java.util.List;

public class CombatListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
            Player damager = (Player) e.getDamager();
            Player victim = (Player) e.getEntity();
            if(e.isCancelled()){
                return;
            }
            CombatManager.checkCombat(damager, victim);
        }
    }

    @EventHandler
    public void onOpen(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(CombatManager.isOpenEnder() && e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.ENDER_CHEST){
            Combat combat = CombatManager.getCombat(p);
            if(combat != null && combat.getLogout().get(p) > System.currentTimeMillis()){
                e.setCancelled(true);
                Util.sendMessage(p, Main.getPlugin().getConfig().getString("messages.openender"));
            }
        }
    }

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent e){
        List<String> blockedCmds = CombatManager.getBlockedCmds();
        Player p = e.getPlayer();
        Combat combat = CombatManager.getCombat(p);
        if(!blockedCmds.isEmpty() && combat != null && combat.getLogout().get(p) > System.currentTimeMillis()){
            for(String blockedCMD : blockedCmds){
                if(e.getMessage().contains(blockedCMD) && e.getMessage().startsWith("/")){
                    e.setCancelled(true);
                    Util.sendMessage(p, Main.getPlugin().getConfig().getString("messages.blockedcmd"));
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        Combat c = CombatManager.getCombat(p);
        if(c != null){
            CombatManager.deleteFromCombat(p,c);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent e){
        if(e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player){
            Player killer = e.getEntity().getKiller();
            Player death = e.getEntity();
            Combat c = CombatManager.getCombat(death);
            if(CombatManager.isPlayerDeathMessage()){
                StringBuilder assists = new StringBuilder("");
                if(c != null && c.getAssists().size() > 0){
                    for(Player assist : c.getAssists()){
                        assists.append(assist.getName()).append(", ");
                    }
                }
                e.setDeathMessage(CombatManager.getPlayerDeathMessage().replace("{KILLER}", killer.getName()).replace("{DEATH}", death.getName()).replace("{ASSISTS}", assists.toString()));
            }
            if(c != null && c.getVictim().equals(death)){
                CombatManager.deleteCombat(c);
                return;
            }
            if((c = CombatManager.getCombat(killer)) != null && c.getVictim().equals(death)){
                CombatManager.deleteCombat(c);
                return;
            }
            if((c = CombatManager.getCombat(death)) != null && c.getLogout().containsKey(death)){
                CombatManager.deleteFromCombat(death, c);
            }else if((c = CombatManager.getCombat(killer)) != null && c.getLogout().containsKey(death)){
                CombatManager.deleteFromCombat(death, c);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location from = e.getFrom();
        Location to = e.getTo();
        Location spawn = p.getWorld().getSpawnLocation();
        boolean isSpawnProtection = CombatManager.isSpawnProtection();
        int radius = CombatManager.getRadius();
        List<String> worlds = CombatManager.getWorlds();
        if(isSpawnProtection && to.distance(spawn) < radius && worlds.contains(p.getWorld().getName())){
            Combat combat = CombatManager.getCombat(p);
            if(combat != null && combat.getLogout().get(p) > System.currentTimeMillis()){
                e.setCancelled(true);
                p.teleport(from);
                Util.sendMessage(p, Main.getPlugin().getConfig().getString("messages.spawnprotection"));
            }
        }
    }
}
