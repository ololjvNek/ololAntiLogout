package pl.ololjvNek.antilogout.data;

import lombok.Data;
import org.bukkit.entity.Player;
import pl.ololjvNek.antilogout.Main;
import pl.ololjvNek.antilogout.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Combat {

    private Player attacker,victim;
    private HashMap<Player, Long> logout;
    private List<Player> assists;

    public Combat(Player attacker, Player victim){
        this.attacker = attacker;
        this.victim = victim;
        this.logout = new HashMap<>();
        this.assists = new ArrayList<>();
        logout.put(attacker, (System.currentTimeMillis()+ TimeUtil.SECOND.getTime(Main.getPlugin().getConfig().getInt("settings.antilogouttime"))));
        logout.put(victim, (System.currentTimeMillis()+ TimeUtil.SECOND.getTime(Main.getPlugin().getConfig().getInt("settings.antilogouttime"))));
    }

    public List<Player> getAllPlayers(){
        return new ArrayList<>(logout.keySet());
    }
}
