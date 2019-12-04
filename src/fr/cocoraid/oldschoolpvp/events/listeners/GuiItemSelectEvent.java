package fr.cocoraid.oldschoolpvp.events.listeners;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.OldSchoolPvp;
import fr.cocoraid.oldschoolpvp.gui.ThreeDimensionGUI;
import fr.cocoraid.oldschoolpvp.updater.UpdateType;
import fr.cocoraid.oldschoolpvp.updater.event.UpdaterEvent;
import fr.cocoraid.oldschoolpvp.utils.packet.PacketArmorStand;
import fr.cocoraid.oldschoolpvp.utils.textanim.Pulse;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by cocoraid on 02/07/2016.
 */
public class GuiItemSelectEvent implements Listener {


    @EventHandler
    public void checkDistance(UpdaterEvent e) {
        if (e.getType() == UpdateType.FAST) {

            OldSchoolPlayer.getOldschoolplayers().values().forEach(p -> {
                if (p.getPlayer() != null && p.getPlayer().isOnline() && p.getThreeDimensionGUI() != null) {
                    if (!p.getThreeDimensionGUI().getCenter().getWorld().equals(p.getPlayer().getWorld()) || p.getThreeDimensionGUI().getCenter().distance(p.getPlayer().getLocation()) >= 12) {
                        p.getThreeDimensionGUI().closeGui(true);
                    }

                }
            });
        } else if (e.getType() == UpdateType.TICK) {

            OldSchoolPlayer.getOldschoolplayers().values().forEach(p -> {

                ThreeDimensionGUI gui = p.getThreeDimensionGUI();
                Player player = p.getPlayer();
                Location location = p.getPlayer().getLocation();
                if (player != null && p.getPlayer().isOnline() && gui != null && gui.isReady()) {
                    gui.update();
                    /*if (gui.getCoin() != null) {
                        if (isLookingAt(player, gui.getCoin().getSelector().getLocation())) {
                            new ColoredParticle(Particle.REDSTONE, gui.getCoin().getSelector().getLocation().clone().add(UtilMath.randomRange(-0.5, 0.5), UtilMath.randomRange(0.3, 0.5), UtilMath.randomRange(-0.5, 0.5)), 255, 255, 0).send(p.getPlayer());
                            gui.getCoin().getItemDisplay().setHeadPose(new EulerAngle(Math.toRadians(UtilMath.randomRange(-10, 10)), Math.toRadians(UtilMath.randomRange(-10, 10)), 0));
                            gui.getCoin().getItemDisplay().updateMetadata();
                        }
                    }*/

                    gui.getItems().stream().filter(i ->
                            i.getItem().getLocation() != null
                                    && isLookingAt(p.getPlayer(), i.getItem().getSelector().getLocation())
                                    && gui.getSelected() <= 4
                                    && i.getItem().isSpawned()
                                    && (gui.getLastSelected() == null || !p.getThreeDimensionGUI().getLastSelected().equals(i.getItem()))).findAny().ifPresent(i -> {

                        gui.addSelected();
                        if (gui.getLastSelected() != null) {
                            gui.getLastSelected().move(true);
                        }
                        gui.setLastSelected(i.getItem());

                        //gui.description();
                        player.playSound(location, Sound.ITEM_PICKUP, 0.1F, 2);

                        gui.getLastSelected().move(false);

                    });
                }
            });
        } else if (e.getType() == UpdateType.SEC) {
            OldSchoolPlayer.getOldschoolplayers().values().forEach(p -> {
                if (p.getPlayer() != null && p.getPlayer().isOnline() && p.getThreeDimensionGUI() != null) {
                    p.getThreeDimensionGUI().setSelected(0);

                }
            });

        }
    }


    private boolean isLookingAt(Player player, Location l) {
        Location eye = player.getEyeLocation();
        Vector toEntity = l.toVector().subtract(eye.toVector());
        double dot = toEntity.normalize().dot(eye.getDirection());
        return dot > 0.96D;
    }
}
