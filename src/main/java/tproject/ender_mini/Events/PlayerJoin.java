package tproject.ender_mini.Events;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;
import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONObject;
import tproject.ender_mini.Utils.Players;

import javax.print.DocFlavor;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PlayerJoin implements Listener {

    public static ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    public static Scoreboard scoreboard;
    public static JSONObject json = new JSONObject();
    public static JSONArray jsonArray = new JSONArray();


    //プレイヤーが参加したときの処理
    @EventHandler
    public static void onJoin(PlayerJoinEvent e) {
        json.put(e.getPlayer().getName(), java.util.Optional.empty());
        createScoreBoard(e.getPlayer());
        update();
    }

    @EventHandler
    public static void onQuit(PlayerQuitEvent e) {
        update();
    }

    public static void createScoreBoard(Player player) {
        //以下スコアボード作成のためのベース
        scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("チーム", "dummy");
        objective.setDisplayName("情報-INFO");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        String teamName = null;
        for (Team team : scoreboard.getTeams()) {
            if (team.hasEntry(player.getDisplayName())) {
                teamName = team.getName();
            }
            if (teamName == null) return;

        }

        //以下表示させたい情報


        //以上表示させたい情報

        //以下チーム作成
        Team red = scoreboard.registerNewTeam("RED");
        Team blue = scoreboard.registerNewTeam("BLUE");

        //赤チームの設定
        red.setPrefix(ChatColor.RED + "[赤チーム] " + ChatColor.WHITE);
        red.setAllowFriendlyFire(false);
        red.setCanSeeFriendlyInvisibles(true);
        red.setDisplayName("赤チーム");

        //青チームの設定
        blue.setPrefix(ChatColor.BLUE + "[青チーム] " + ChatColor.WHITE);
        blue.setAllowFriendlyFire(false);
        blue.setCanSeeFriendlyInvisibles(true);
        blue.setDisplayName("青チーム");



        //String[] names = new String[Bukkit.getOnlinePlayers().size()];
        List<String> namesRED = new ArrayList<>();
        List<String> namesBLUE = new ArrayList<>();


        //チームのセットの分散の昨日のやつ（小並感）
        if (Bukkit.getOnlinePlayers().size() % 2 == 0) {
            namesRED.add(player.getName());
            int rC = namesRED.size();
            red.addPlayer(player);

            for(int i = 0; i< rC; i++){
                json = new JSONObject();
                json.put(namesRED.get(i), "red");
                jsonArray.put(json);
            }




            String fileName = "Date.json";
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
                json.write(writer);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }


        } else {
            namesBLUE.add(player.getName());
            blue.addPlayer(player);
            int bC = namesBLUE.size();
            for(int i = 0; i< bC; i++){
                json = new JSONObject();
                json.put(namesBLUE.get(i), "blue");
                jsonArray.put(json);
            }


            String fileName = "Date.json";
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
                json.write(writer);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        String teams = (String) json.get(player.getName());


        Score score1;
        if (teams.equals("red")) {
            score1 = objective.getScore("所属：" + ChatColor.RED + "[赤チーム]");
        } else {
            score1 = objective.getScore("所属：" + ChatColor.BLUE + "[青チーム]");
        }
        score1.setScore(98);

        player.setScoreboard(scoreboard);
    }

    public static void update() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Score score = Objects.requireNonNull(player.getScoreboard().getObjective(DisplaySlot.SIDEBAR)).getScore("全参加者数：");
            score.setScore(Bukkit.getOnlinePlayers().size());
        }
    }


}
