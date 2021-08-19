package pl.ololjvNek.antilogout;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import pl.ololjvNek.antilogout.listeners.CombatListener;
import pl.ololjvNek.antilogout.runnables.CombatRunnable;

public class Main extends JavaPlugin {

    @Getter private static Main plugin;

    public void onEnable(){
        plugin = this;
        saveDefaultConfig();
        reloadConfig();
        Bukkit.getPluginManager().registerEvents(new CombatListener(), this);
        new CombatRunnable().runTaskTimer(this, 20L, 20L);
    }
}
