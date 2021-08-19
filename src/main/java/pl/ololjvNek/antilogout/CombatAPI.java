package pl.ololjvNek.antilogout;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.ololjvNek.antilogout.data.Combat;
import pl.ololjvNek.antilogout.managers.CombatManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CombatAPI {

    public static List<Player> getAssisted(Player player){
        if(CombatManager.getCombat(player) != null){
            return Objects.requireNonNull(CombatManager.getCombat(player)).getAssists();
        }else{
            return new ArrayList<>();
        }
    }

    public static Combat getPlayerCombat(Player player){
        return CombatManager.getCombat(player);
    }

    public static List<Combat> getAllActiveCombats(){
        return CombatManager.getCombats();
    }
}
