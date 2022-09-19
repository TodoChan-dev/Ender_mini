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
            String teamName = null;
            for(Team team : PlayerJoin.scoreboard.getTeams()){
                if(team.hasEntry(killer.getDisplayName())){
                    teamName = team.getName();
                }
                if(teamName == null)return;

            }


            if(Objects.equals(teamName, "RED")){
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.sendTitle(ChatColor.RED + "赤チーム" + ChatColor.WHITE + "が勝利しました。","お疲れ様");
                }
            }else {
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.sendTitle(ChatColor.BLUE + "青チーム" + ChatColor.WHITE + "が勝利しました。","お疲れ様");
                }
            }
        }
    }
}
