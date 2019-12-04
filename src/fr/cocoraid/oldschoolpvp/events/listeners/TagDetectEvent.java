package fr.cocoraid.oldschoolpvp.events.listeners;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.OldSchoolPvp;
import fr.cocoraid.oldschoolpvp.area.EnterSafezoneEvent;
import fr.cocoraid.oldschoolpvp.area.Safezone;
import fr.cocoraid.oldschoolpvp.language.Language;
import fr.cocoraid.oldschoolpvp.updater.UpdateType;
import fr.cocoraid.oldschoolpvp.updater.event.UpdaterEvent;
import fr.cocoraid.oldschoolpvp.utils.ActionBar;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TagDetectEvent implements Listener {


    /**
     * Check if players and in warzone -> add both to the tag list
     * if safezone cancel, if player hitted is contained in the tagged list and the damager has the kit, add it
     *
     * check distance, if distance is small don't cancel tag
     *
     * @param e
     */
    @EventHandler
    public void damage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player damager = (Player) e.getDamager();
            Player victim = (Player) e.getEntity();
            OldSchoolPlayer victimOldSchool = OldSchoolPlayer.getOldSchoolPlayer(victim);
            OldSchoolPlayer damagerOldSchool = OldSchoolPlayer.getOldSchoolPlayer(damager);
            //Prevent victim to be damaged by a player if safe zone or without kit :)
            if(!victimOldSchool.hasKit() || !damagerOldSchool.hasKit() || (!victimOldSchool.isTagged() && Safezone.getEntered().contains(victim.getUniqueId()))) {
                e.setCancelled(true);
                return;
            }
            victimOldSchool.getTotalDamageReceived().put(damager.getUniqueId(), victimOldSchool.getTotalDamageReceived().get(damager.getUniqueId()) == null ? e.getDamage() : victimOldSchool.getTotalDamageReceived().get(damager.getUniqueId()) + e.getDamage());
            damagerOldSchool.setRecentHit(true);
            victimOldSchool.setRecentHit(true);
            //If victim is tagged -> allow damage
            if(victimOldSchool.isTagged()) return;

            //Apply tag for both
            victimOldSchool.setTagged(true);
            if(!damagerOldSchool.isTagged()) {
                damagerOldSchool.setTagged(true);
                removeTagLater(damager,true);
            }
            removeTagLater(victim, true);
        }

    }

    @EventHandler
    public void quitTagged(PlayerQuitEvent e) {
        OldSchoolPlayer op = OldSchoolPlayer.getOldSchoolPlayer(e.getPlayer());
        if(op.isTagged()) {
            //Recompense pour ceux qui ont tapés
        }
    }

    @EventHandler
    public void removeLastTagger(UpdaterEvent e) {
        if(e.getType() == UpdateType.SLOWER) {
            OldSchoolPlayer.getOldschoolplayers().values().stream().filter(op -> op.isTagged()).forEach(op -> {
                op.setRecentHit(false);
            });
        }
    }

    @EventHandler
    public void enterSafe(EnterSafezoneEvent e) {
        if(OldSchoolPlayer.getOldSchoolPlayer(e.getPlayer()).isTagged()) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20 * 3, 3, true,true));
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_BREAK,1,0);
        }
    }


    public void removeTagLater(Player p, boolean actionbar) {
        Language lang =  OldSchoolPlayer.getOldSchoolPlayer(p).getLanguage();
        if(actionbar) {
            OldSchoolPvp.getInstance().getNametag().setTaggedTeam(p);
            new ActionBar(ChatColor.RED + lang.tagged).sendToPlayer(p);
        }
        OldSchoolPlayer tagged = OldSchoolPlayer.getOldSchoolPlayer(p);
        new BukkitRunnable() {
            public void run() {
                if(tagged.getPlayer().isOnline()) {
                    if (tagged.isTagged()) {
                        if(tagged.hasRecentHit()) {
                            removeTagLater(tagged.getPlayer(), true);
                            return;
                        }
                        tagged.setRecentHit(false);
                        tagged.setTagged(false);

                        if (Safezone.getEntered().contains(p.getUniqueId())) {
                            OldSchoolPvp.getInstance().getNametag().removeTeam(p,"tagged");
                            if (tagged.getKit() != null)
                                tagged.getKit().removeKit();

                        } else
                            OldSchoolPvp.getInstance().getNametag().setPvpTeam(p);

                        new ActionBar(ChatColor.GREEN + lang.notagged).sendToPlayer(p);
                    }
                }
            }
        }.runTaskLater(OldSchoolPvp.getInstance(), 20 * 20);
    }

}
