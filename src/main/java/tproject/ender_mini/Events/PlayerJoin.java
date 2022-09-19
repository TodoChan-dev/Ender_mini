package tproject.ender_mini.Events;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;
import tproject.ender_mini.Ender_mini;

import java.util.HashMap;
import java.util.UUID;


public class PlayerJoin implements Listener {


    public static HashMap<UUID, Scoreboard> playerScoreboards;

    public PlayerJoin() {
        playerScoreboards = new HashMap<>();
    }



    //プレイヤーが参加したときの処理
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Team red = Ender_mini.red;
        Team blue = Ender_mini.blue;

        Player p = e.getPlayer();
        String name = p.getName();

        if(red.hasEntry(name) || blue.hasEntry(name))return;

        if (Bukkit.getOnlinePlayers().size() % 2 == 0) {
            red.addEntry(name);
        } else {
            blue.addEntry(name);
        }




        createScoreBoard(e.getPlayer());
        for(Player ppppp : Bukkit.getOnlinePlayers()){
            update(ppppp);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        for(Player ppppp : Bukkit.getOnlinePlayers()){
            update(ppppp);
        }
    }

    public void createScoreBoard(Player player) {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("info","dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("イベント情報");

        UUID uuid = player.getUniqueId();

        playerScoreboards.put(uuid,scoreboard);

        player.setScoreboard(scoreboard);
        for(Player p : Bukkit.getOnlinePlayers()){
            update(p);
        }

    }



    public static void update(Player player) {
        UUID uuid = player.getUniqueId();
        Scoreboard scoreboard = playerScoreboards.get(uuid);
        Objective objective = scoreboard.getObjective("info");

        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }
        String teamName = "";
        int teamSize = -1;
        if (Ender_mini.red.hasEntry(player.getName())) {
            teamName = "赤チーム";
            teamSize = Ender_mini.red.getEntries().size();
        } else if (Ender_mini.blue.hasEntry(player.getName())) {
            teamName = "青チーム";
            teamSize = Ender_mini.blue.getEntries().size();
        }
        Score teamNameScore = objective.getScore("現在のチーム: " + teamName);
        Score teamSizeScore = objective.getScore("チーム人数: " + teamSize);
        Score onlineSizeScore = objective.getScore("現在の人数: " + Bukkit.getOnlinePlayers().size());
        teamNameScore.setScore(0);
        teamSizeScore.setScore(-1);
        onlineSizeScore.setScore(-2);


        player.setScoreboard(scoreboard);

    }


}
