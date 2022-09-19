package tproject.ender_mini.Events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class CraftEvent implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent e){
        Player p = (Player) e.getWhoClicked();
        String name = p.getName();
        Material material = e.getRecipe().getResult().getType();

        if(material.equals(Material.CRAFTING_TABLE) || material.equals(Material.FURNACE) || material.equals(Material.SMOKER) || material.equals(Material.BLAST_FURNACE)){
            e.setCancelled(true);
            p.sendMessage("これらのアイテムを作成することはできません。");
        }

    }
}
