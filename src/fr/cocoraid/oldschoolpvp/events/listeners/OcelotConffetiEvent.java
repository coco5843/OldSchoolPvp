package fr.cocoraid.oldschoolpvp.events.listeners;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.kit.ClassicKit;
import fr.cocoraid.oldschoolpvp.utils.UtilItem;
import fr.cocoraid.oldschoolpvp.utils.UtilMath;
import fr.cocoraid.oldschoolpvp.utils.particle.MaterialParticle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OcelotConffetiEvent implements Listener {

    @EventHandler
    public void onOcelotDestroyed(EntityDamageByEntityEvent e) {
        if(e.getEntityType() == EntityType.OCELOT) {
            if(e.getDamager()instanceof Player) {
                Location l = e.getEntity().getLocation();
                for(int k = 0; k < 50; k ++)
                    new MaterialParticle(l,
                            0.5F, 0.3F, 0.5F,
                            0.06F, 1,
                            35, UtilItem.getGreatRandomColor().getData()).send((Player)e.getDamager());
                ((Player) e.getDamager()).playSound(l, Sound.CHICKEN_EGG_POP,1,0);
                e.getEntity().remove();

                if(UtilMath.randomRange(0,10) == 1) {
                    OldSchoolPlayer op = OldSchoolPlayer.getOldSchoolPlayer((Player) e.getDamager());
                    op.getPlayerdata().addCredit(1);
                    ((Player) e.getDamager()).playSound(e.getDamager().getLocation(),Sound.ORB_PICKUP,1,1);
                    e.getDamager().sendMessage(ChatColor.AQUA + op.getLanguage().foundCredit.replace("%credit","1"));
                }
            }
        }

    }
}
