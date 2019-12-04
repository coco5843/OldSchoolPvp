package fr.cocoraid.oldschoolpvp.events.listeners;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.OldSchoolPvp;
import fr.cocoraid.oldschoolpvp.utils.Reflection;
import net.minecraft.server.v1_8_R3.EntityItem;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DropEvent implements Listener {


    @EventHandler
    public void drop(PlayerDropItemEvent e) {
        OldSchoolPlayer op = OldSchoolPlayer.getOldSchoolPlayer(e.getPlayer());
        if(op.hasKit()) {
            Material type = e.getItemDrop().getItemStack().getType();
            if(type == Material.IRON_LEGGINGS || type == Material.IRON_BOOTS || type == Material.IRON_HELMET || type == Material.IRON_CHESTPLATE) e.setCancelled(true);
            if(type == Material.DIAMOND_SWORD) {
                if (!op.getPreferences().isAllowDropSword())
                    e.setCancelled(true);
            } else if(type == Material.BOWL ||type == Material.MUSHROOM_SOUP) {
                new BukkitRunnable() {
                    public void run() {
                        Reflection.getField(EntityItem.class, "age", int.class).set(((CraftItem) e.getItemDrop()).getHandle(), 6000 - (20*10));
                    }
                }.runTaskLater(OldSchoolPvp.getInstance(), 10);
            }
        }
    }

    @EventHandler
    public void stack(ItemMergeEvent e) {

        if(e.getEntity().getItemStack().getType() == Material.BOWL) {
            new BukkitRunnable() {
                public void run() {
                    Reflection.getField(EntityItem.class, "age", int.class).set(((CraftItem) e.getTarget()).getHandle(), 6000 - (20*10));
                }
            }.runTaskLater(OldSchoolPvp.getInstance(), 10);

        }
    }
}
