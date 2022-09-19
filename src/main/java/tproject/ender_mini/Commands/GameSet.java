package tproject.ender_mini.Commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import tproject.ender_mini.Ender_mini;
import tproject.ender_mini.Events.KillDragon;


import javax.swing.plaf.IconUIResource;
import java.util.List;

public class GameSet implements CommandExecutor, TabCompleter {

    public boolean countCheck;
    public static int counter;
    public static int i;
    public static NamespacedKey key = new NamespacedKey(Ender_mini.plugin, "counter");
    public final BossBar BOSSBAR = Bukkit.createBossBar(key, "制限時間 : N/A", BarColor.YELLOW, BarStyle.SOLID);
    public static String timer;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("set")) {
                if (args[1].equalsIgnoreCase("counter")) {
                    if (args[2].equalsIgnoreCase("down")) {
                        countCheck = true;
                        if (!StringUtils.isNumeric(args[3])) {
                            sender.sendMessage("数字を使用してください");
                            return true;
                        }
                        counter = Integer.parseInt(args[3]);
                        if (counter <= 0) {
                            sender.sendMessage("0よりも大きい数を指定してください。");
                            return true;
                        }

                        Bukkit.broadcastMessage("タイマーを" + ChatColor.BLUE + "カウントダウン" + ChatColor.WHITE + "で" + ChatColor.YELLOW + counter
                                + ChatColor.WHITE + "分にセットしました。");


                    } else if (args[2].equalsIgnoreCase("up")) {
                        Bukkit.broadcastMessage("タイマーを" + ChatColor.RED + "カウントアップ" + ChatColor.WHITE + "にしました。");
                        countCheck = false;
                    }
                }


            }else if(args[0].equalsIgnoreCase("start")){
                if(!countCheck){
                    for(Player player : Bukkit.getOnlinePlayers()){
                        BOSSBAR.addPlayer(player);
                        BOSSBAR.setVisible(true);
                    }
                    BukkitRunnable task = new BukkitRunnable() {
                        int baseTime = 0;

                        @Override
                        public void run() {
                            int min = baseTime / 60;
                            int hour = min / 60;

                            if(baseTime < 60){
                                BOSSBAR.setTitle("[ 経過時間§a: §f" + " -- " + "時間 " + " -- " + "分 " + baseTime +"秒 ]");
                            }else if(baseTime < 3600){
                                BOSSBAR.setTitle("[ 経過時間§a: §f" + " -- " + "時間 " + min + "分 " + baseTime%60 +"秒 ]");
                            }else {
                                BOSSBAR.setTitle("[ 経過時間§a: §f" + hour + "時間 " + min%60 + "分 " + baseTime%60 +"秒 ]");


                            }
                            if(!BOSSBAR.isVisible()){
                                cancel();
                            }
                            if(KillDragon.game){
                                for(Player player : Bukkit.getOnlinePlayers()){
                                    player.sendMessage("§6§kMM§r"+ ChatColor.YELLOW + "ゲーム終了"+ "§6§kMM§r" ,"クリアまでの時間§a: §f" + hour + "時間" + min%60 + "分" + baseTime%60 +"秒");
                                    Bukkit.removeBossBar(key);
                                    BOSSBAR.removePlayer(player);
                                    BOSSBAR.setVisible(false);

                                }
                                cancel();
                                try{
                                    Thread.sleep(1000);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }KillDragon.game = false;


                            }

                            baseTime++;
                        }

                    };
                    task.runTaskTimer(Ender_mini.plugin,0L,20L);

                }else {
                    for(Player player : Bukkit.getOnlinePlayers()){
                        BOSSBAR.addPlayer(player);
                        BOSSBAR.setVisible(true);
                    }
                    BukkitRunnable task = new BukkitRunnable() {
                        int i = counter * 60;// s
                        final double criterion = counter * 60; //henka sinai s
                        @Override
                        public void run() {
                            if(i == 0){
                                for(Player player : Bukkit.getOnlinePlayers()){
                                    player.sendTitle(ChatColor.RED + "ゲーム終了","");
                                    cancel();
                                    Bukkit.removeBossBar(key);
                                    BOSSBAR.removePlayer(player);
                                    BOSSBAR.setVisible(false);
                                    KillDragon.game = false;
                                }
                                final int min = (int)Math.floor(i / 60);
                                if(counter == 1) {
                                    if(i % 2 == 0 && i > 10){
                                        BOSSBAR.setTitle("残り時間： " + ChatColor.RED + i % 60 + " 秒");
                                    }else {
                                        BOSSBAR.setTitle("残り時間： " + ChatColor.YELLOW + i % 60 + " 秒");
                                        BOSSBAR.setColor(BarColor.RED);
                                    }
                                } if(counter > 1 && counter < 60){ //1分を超えて1時間を超えない場合の処理
                                    if(i % 2 == 0){ //秒数が偶数の場合
                                        BOSSBAR.setTitle("残り時間： " + ChatColor.RED + min + " 分 " + i % 60 +" 秒");
                                    }else //奇数の場合
                                        BOSSBAR.setTitle("残り時間： " + ChatColor.YELLOW + min + " 分 " + i % 60 + " 秒");
                                }else if(counter >= 60){
                                    if(i % 2 == 0){ //秒数が偶数の場合
                                        BOSSBAR.setTitle("残り時間： " + ChatColor.RED + min/60 + " 時間 " + min + ChatColor.GOLD + " 分 " + i % 60 + " 秒");
                                    }else //奇数の場合
                                        BOSSBAR.setTitle("残り時間： " + ChatColor.YELLOW + min/60 + " 時間 " + min + " 分" + i % 60 + " 秒");
                                }
                                double barValue = i / criterion;
                                BOSSBAR.setProgress(barValue);
                            }


                            i--;
                        }
                    };
                    task.runTaskTimer(Ender_mini.plugin,0L,20L);
                }
            }
        }


        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
