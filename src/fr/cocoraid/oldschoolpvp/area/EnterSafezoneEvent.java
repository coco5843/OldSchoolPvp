package fr.cocoraid.oldschoolpvp.area;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EnterSafezoneEvent extends Event {

    private Player player;

    public EnterSafezoneEvent(Player player) {
        this.player = player;

    }

    public Player getPlayer() {
        return this.player;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
