package fr.cocoraid.oldschoolpvp.events.listeners;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SoupEvent implements Listener {

    @EventHandler
    public void soup(PlayerInteractEvent e) {
        if(OldSchoolPlayer.getOldSchoolPlayer(e.getPlayer()).hasKit()) {
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
                if(e.getPlayer().getItemInHand().getType() == Material.MUSHROOM_SOUP) {
                    if(e.getPlayer().getHealth() != e.getPlayer().getMaxHealth()) {
                        if(e.getPlayer().getHealth() + 5 > e.getPlayer().getMaxHealth())
                            e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
                        else
                            e.getPlayer().setHealth(e.getPlayer().getHealth() + 5);
                        e.setCancelled(true);
                        if(OldSchoolPlayer.getOldSchoolPlayer(e.getPlayer()).getPreferences().isAutoRemoveSoup())
                            e.getPlayer().setItemInHand(null);
                        else
                            e.getPlayer().getItemInHand().setType(Material.BOWL);
                    } else if(e.getPlayer().getFoodLevel() < 20) {
                        e.setCancelled(true);
                        if (e.getPlayer().getFoodLevel() + 5 > 20)
                            e.getPlayer().setFoodLevel(20);
                        else
                            e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel() + 5);
                        e.getPlayer().getItemInHand().setType(Material.BOWL);
                    }
                }
            }
        }
    }

}
