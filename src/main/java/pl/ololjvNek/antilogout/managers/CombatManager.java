package pl.ololjvNek.antilogout.managers;

import lombok.Getter;
import org.bukkit.entity.Player;
import pl.ololjvNek.antilogout.Main;
import pl.ololjvNek.antilogout.data.Combat;
import pl.ololjvNek.antilogout.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CombatManager {

    private static boolean isResetTime = Main.getPlugin().getConfig().getBoolean("settings.resettime");
    @Getter private static boolean isPlayerDeathMessage = Main.getPlugin().getConfig().getBoolean("messages.playerdeath.enabled");
    @Getter private static String playerDeathMessage = Main.getPlugin().getConfig().getString("messages.playerdeath.message");
    @Getter private static boolean isOpenEnder = Main.getPlugin().getConfig().getBoolean("settings.openender");
    @Getter private static List<String> blockedCmds = Main.getPlugin().getConfig().getStringList("settings.blockedcmds");
    @Getter private static boolean isSpawnProtection = Main.getPlugin().getConfig().getBoolean("settings.spawnprotection.enabled");
    @Getter private static int radius = Main.getPlugin().getConfig().getInt("settings.spawnprotection.radius");
    @Getter private static List<String> worlds = Main.getPlugin().getConfig().getStringList("settings.spawnprotection.worlds");
    private static int logoutTime = Main.getPlugin().getConfig().getInt("settings.antilogouttime");

    @Getter private static List<Combat> combats = new ArrayList<>();

    public static void checkCombat(Player attacker, Player victim){
        Combat Acombat = getCombat(attacker);
        Combat Vcombat = getCombat(victim);
        if(Acombat == null && Vcombat == null){
            Combat combat = new Combat(attacker, victim);
            combats.add(combat);
            return;
        }
        if(Acombat != null && Vcombat != null){
            if(Acombat.equals(Vcombat)){
                if(isResetTime){
                    Vcombat.getLogout().replace(victim, System.currentTimeMillis()+TimeUtil.SECOND.getTime(logoutTime));
                    Acombat.getLogout().replace(attacker, System.currentTimeMillis()+TimeUtil.SECOND.getTime(logoutTime));
                }
                return;
            }

            if(isResetTime){
                if(Acombat.getAttacker().equals(attacker) || Acombat.getVictim().equals(attacker)){
                    Acombat.getLogout().replace(attacker, System.currentTimeMillis()+TimeUtil.SECOND.getTime(logoutTime));
                }else if(Vcombat.getAttacker().equals(attacker) || Vcombat.getVictim().equals(attacker)){
                    Vcombat.getLogout().replace(attacker, System.currentTimeMillis()+TimeUtil.SECOND.getTime(logoutTime));
                }
            }

            if(!Vcombat.getAssists().contains(Vcombat.getAttacker())){
                Vcombat.getAssists().add(Vcombat.getAttacker());
            }
            if(Vcombat.getAssists().contains(attacker)){
                Vcombat.getAssists().remove(attacker);
            }
            Vcombat.setAttacker(attacker);

        }else if(Acombat == null){
            if(isResetTime){
                Vcombat.getLogout().replace((Vcombat.getAttacker().equals(victim) ? Vcombat.getAttacker() : Vcombat.getVictim()), System.currentTimeMillis()+TimeUtil.SECOND.getTime(logoutTime));
            }
            if(!Vcombat.getLogout().containsKey(attacker)){
                Vcombat.getLogout().put(attacker, System.currentTimeMillis()+TimeUtil.SECOND.getTime(logoutTime));
            }else{
                Vcombat.getLogout().replace(attacker, System.currentTimeMillis()+TimeUtil.SECOND.getTime(logoutTime));
            }
            if(!Vcombat.getAssists().contains(Vcombat.getAttacker())){
                Vcombat.getAssists().add(Vcombat.getAttacker());
            }
            if(Vcombat.getAssists().contains(attacker)){
                Vcombat.getAssists().remove(attacker);
            }
            Vcombat.setAttacker(attacker);
        }else{
            if(isResetTime){
                Acombat.getLogout().replace((Acombat.getAttacker().equals(attacker) ? Acombat.getAttacker() : Acombat.getVictim()), System.currentTimeMillis()+TimeUtil.SECOND.getTime(logoutTime));
            }
            if(!Acombat.getLogout().containsKey(victim)){
                Acombat.getLogout().put(victim, System.currentTimeMillis()+TimeUtil.SECOND.getTime(logoutTime));
            }else{
                Acombat.getLogout().replace(victim, System.currentTimeMillis()+TimeUtil.SECOND.getTime(logoutTime));
            }
            Acombat.setVictim(victim);
        }
    }

    public static void deleteCombat(Combat c){
        combats.remove(c);
    }

    public static void deleteFromCombat(Player player, Combat combat){
        combat.getAssists().remove(player);
        if(combat.getAttacker() != null && combat.getAttacker().getUniqueId().equals(player.getUniqueId())){
            combat.setAttacker(null);
        }
        if(combat.getVictim() != null && combat.getVictim().getUniqueId().equals(player.getUniqueId())){
            combat.setVictim(null);
        }
        combat.getLogout().remove(player);
        if(combat.getLogout().size() <= 1){
            deleteCombat(combat);
        }
    }

    public static Combat getCombat(Player player){
        for(Combat combat : combats){
            if(combat.getAttacker() != null && combat.getAttacker().getUniqueId().equals(player.getUniqueId())){
                return combat;
            }
            if(combat.getVictim() != null && combat.getVictim().getUniqueId().equals(player.getUniqueId())){
                return combat;
            }
            /*for(Player assisted : combat.getAssists()){
                if(assisted.getUniqueId().equals(player.getUniqueId())){
                    return combat;
                }
            }*/
        }
        return null;
    }
}
