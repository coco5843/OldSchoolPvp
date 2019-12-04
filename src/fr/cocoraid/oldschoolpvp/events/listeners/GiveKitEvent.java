package fr.cocoraid.oldschoolpvp.events.listeners;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.OldSchoolPvp;
import fr.cocoraid.oldschoolpvp.area.EnterSafezoneEvent;
import fr.cocoraid.oldschoolpvp.area.EnterWarzoneEvent;
import fr.cocoraid.oldschoolpvp.area.Safezone;
import fr.cocoraid.oldschoolpvp.kit.ClassicKit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GiveKitEvent implements Listener {

    @EventHandler
    public void warzone(EnterWarzoneEvent e) {
        if(OldSchoolPlayer.getOldSchoolPlayer(e.getPlayer()).hasKit()) return;
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,20 * 6,2,false,false));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,20,2,false,false));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,20,2,false,false));
        new ClassicKit(e.getPlayer()).giveKit();
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERDRAGON_WINGS,0.5F,0);
        OldSchoolPvp.getInstance().getNametag().setPvpTeam(e.getPlayer());

    }

    @EventHandler
    public void safeZone(EnterSafezoneEvent e) {
        OldSchoolPlayer op = OldSchoolPlayer.getOldSchoolPlayer(e.getPlayer());
        if(!op.hasKit()) return;
        if(!op.isTagged()) {
            new BukkitRunnable() {
                public void run() {
                    if(op.isTagged() || !Safezone.getEntered().contains(e.getPlayer().getUniqueId()) || op.getKit() == null) return;
                    OldSchoolPvp.getInstance().getNametag().removeTeam(e.getPlayer(),"pvp");
                    op.getKit().removeKit();
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERDRAGON_WINGS,1,1);
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,20,2,false,false));
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,20,2,false,false));
                }
            }.runTaskLater(OldSchoolPvp.getInstance(), 20 * 5);
        }

    }
}
