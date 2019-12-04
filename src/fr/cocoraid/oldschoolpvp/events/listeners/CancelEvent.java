package fr.cocoraid.oldschoolpvp.events.listeners;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.area.Safezone;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class CancelEvent implements Listener {

    //admin command
    private static boolean pvp = true;
    @EventHandler
    public void pvp(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player && !pvp) {

            e.getDamager().sendMessage("§c" + OldSchoolPlayer.getOldSchoolPlayer((Player)e.getDamager()).getLanguage().tryPvp);
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void food(FoodLevelChangeEvent e) {
        if(Safezone.getEntered().contains(e.getEntity().getUniqueId()))
            e.setCancelled(true);
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        if(e.getPlayer().hasPermission("kitpvp.admin") || e.getPlayer().hasPermission("kitpvp.builder")) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void placeBlock(BlockPlaceEvent e) {
        if(e.getPlayer().hasPermission("kitpvp.admin") || e.getPlayer().hasPermission("kitpvp.builder")) {
            return;
        }
        e.setCancelled(true);
    }

    public static void setPvp(boolean pvp) {
        CancelEvent.pvp = pvp;
    }

    public static boolean isPvp() {
        return pvp;
    }
}
