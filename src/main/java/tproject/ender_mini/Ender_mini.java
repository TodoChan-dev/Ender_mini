package tproject.ender_mini;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import tproject.ender_mini.Commands.GameSet;
import tproject.ender_mini.Events.KillDragon;
import tproject.ender_mini.Events.PlayerJoin;

public final class Ender_mini extends JavaPlugin {
    public static Plugin plugin;


    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        getServer().getPluginCommand("game").setExecutor(new GameSet());

        //Events
        getServer().getPluginManager().registerEvents(new PlayerJoin(),this);
        getServer().getPluginManager().registerEvents(new KillDragon(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
