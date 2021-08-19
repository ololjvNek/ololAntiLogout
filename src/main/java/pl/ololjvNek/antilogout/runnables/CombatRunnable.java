package pl.ololjvNek.antilogout.runnables;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.ololjvNek.antilogout.Main;
import pl.ololjvNek.antilogout.data.Combat;
import pl.ololjvNek.antilogout.managers.CombatManager;
import pl.ololjvNek.antilogout.utils.DataUtil;
import pl.ololjvNek.antilogout.utils.Util;

public class CombatRunnable extends BukkitRunnable {

    private boolean isActionBarEnabled = Main.getPlugin().getConfig().getBoolean("settings.actionbar.enabled");

    @Override
    public void run() {
        for(Combat combat : CombatManager.getCombats()){
            for(Player player : combat.getLogout().keySet()){
                if(combat.getLogout().get(player) < System.currentTimeMillis()){
                    CombatManager.deleteFromCombat(player, combat);
                }else if(combat.getLogout().get(player) > System.currentTimeMillis() && isActionBarEnabled){
                    Util.sendActionBar(player, Main.getPlugin().getConfig().getString("settings.actionbar.message").replace("{TIMELEFT}", DataUtil.secondsToString(combat.getLogout().get(player))).replace("{PROGRESS}", Util.changeTimeToProgressString(combat.getLogout().get(player))));
                }
            }
        }
    }
}
