package tproject.ender_mini.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scoreboard.Team;
import tproject.ender_mini.Ender_mini;

import java.util.Objects;

public class KillDragon implements Listener {
    public static boolean game = false;


    @EventHandler
    public static void onKill(EntityDeathEvent e){
        LivingEntity entity = e.getEntity();
        if(entity.getType() == EntityType.ENDER_DRAGON){
            Player killer = entity.getKiller();
            String name = killer.getName();
            Team killTeam = killer.getScoreboard().getPlayerTeam(killer);
             game = true;

            if (Ender_mini.red.hasEntry(name)) {
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.sendTitle(ChatColor.RED + "赤チーム" + ChatColor.WHITE + "が勝利しました。","お疲れ様");
                }
            } else if (Ender_mini.blue.hasEntry(name)) {
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.sendTitle(ChatColor.BLUE + "青チーム" + ChatColor.WHITE + "が勝利しました。","お疲れ様");
                }
            }
        }
    }
}
