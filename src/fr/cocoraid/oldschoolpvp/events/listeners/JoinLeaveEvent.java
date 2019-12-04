package fr.cocoraid.oldschoolpvp.events.listeners;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.OldSchoolPvp;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveEvent implements Listener {


    @EventHandler
    public void join(PlayerJoinEvent e) {
        new OldSchoolPlayer(e.getPlayer());
        OldSchoolPvp.getInstance().getPlayerDatabase().registerPlayer(e.getPlayer());
    }

    @EventHandler
    public void leave(PlayerQuitEvent e) {
        OldSchoolPlayer.getOldSchoolPlayer(e.getPlayer()).unregister();
    }

}
