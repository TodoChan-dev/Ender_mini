package tproject.ender_mini;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import tproject.ender_mini.Commands.GameSet;
import tproject.ender_mini.Events.KillDragon;
import tproject.ender_mini.Events.PlayerJoin;

public final class Ender_mini extends JavaPlugin {
    public static Plugin plugin;
    public static Scoreboard scoreboard;
    public static Team red;
    public static Team blue;


    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        getServer().getPluginCommand("game").setExecutor(new GameSet());

        //Events
        getServer().getPluginManager().registerEvents(new PlayerJoin(),this);
        getServer().getPluginManager().registerEvents(new KillDragon(),this);
        initScoreBoard();

    }
    private void initScoreBoard(){
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        red = scoreboard.getTeam("RED");
        if(red == null){
            red = scoreboard.registerNewTeam("RED");
        }
        blue = scoreboard.getTeam("BLUE");
        if(blue == null){
            blue = scoreboard.registerNewTeam("BLUE");
        }



        //赤チームの設定
        Ender_mini.red.setPrefix(ChatColor.RED + "[赤チーム] " + ChatColor.WHITE);
        Ender_mini.red.setAllowFriendlyFire(false);
        Ender_mini.red.setCanSeeFriendlyInvisibles(true);
        Ender_mini.red.setDisplayName("赤チーム");

        //青チームの設定
        Ender_mini.blue.setPrefix(ChatColor.BLUE + "[青チーム] " + ChatColor.WHITE);
        Ender_mini.blue.setAllowFriendlyFire(false);
        Ender_mini.blue.setCanSeeFriendlyInvisibles(true);
        Ender_mini.blue.setDisplayName("青チーム");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
