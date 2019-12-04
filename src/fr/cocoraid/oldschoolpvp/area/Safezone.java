package fr.cocoraid.oldschoolpvp.area;

import fr.cocoraid.oldschoolpvp.updater.UpdateType;
import fr.cocoraid.oldschoolpvp.updater.event.UpdaterEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Safezone implements Listener {


    private static List<UUID> entered = new ArrayList<>();
    private static Cuboid cuboid;

    @EventHandler
    public void updateEvent(UpdaterEvent e) {
        if(e.getType() == UpdateType.FAST) {

            if(!Bukkit.getOnlinePlayers().isEmpty() && cuboid != null) {

                Bukkit.getOnlinePlayers().stream().filter(cur -> cur.getWorld().equals(cuboid.world)).forEach(cur -> {
                    if(cuboid.isIn(cur.getLocation()) && !entered.contains(cur.getUniqueId())) {
                        entered.add(cur.getUniqueId());
                        EnterSafezoneEvent event = new EnterSafezoneEvent(cur);
                        Bukkit.getPluginManager().callEvent(event);
                    }

                    if(!cuboid.isIn(cur.getLocation()) && entered.contains(cur.getUniqueId())) {
                        entered.remove(cur.getUniqueId());
                        EnterWarzoneEvent event = new EnterWarzoneEvent(cur);
                        Bukkit.getPluginManager().callEvent(event);
                    }

                });
            }
        }
    }


    public static List<UUID> getEntered() {
        return entered;
    }

    public static void setCuboid(Cuboid cuboid) {
        Safezone.cuboid = cuboid;
    }

    public static Cuboid getCuboid() {
        return cuboid;
    }
}
