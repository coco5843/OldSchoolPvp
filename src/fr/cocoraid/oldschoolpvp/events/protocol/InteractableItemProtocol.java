package fr.cocoraid.oldschoolpvp.events.protocol;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.gui.item.InteractableItem;
import fr.cocoraid.oldschoolpvp.utils.Reflection;
import fr.cocoraid.oldschoolpvp.utils.TinyProtocol;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


/**
 * Created by cocoraid on 30/06/2016.
 */
public class InteractableItemProtocol {

    private TinyProtocol protocol;

    public static Reflection.FieldAccessor action = Reflection.getField(PacketPlayInUseEntity.class,PacketPlayInUseEntity.EnumEntityUseAction.class,0);
    public static Reflection.FieldAccessor idField = Reflection.getField(PacketPlayInUseEntity.class,int.class,0);
    public InteractableItemProtocol(Plugin plugin) {
        this.protocol = new TinyProtocol(plugin) {

            @Override
            public Object onPacketInAsync(Player sender, Channel channel, Object packet) {
                if (packet.getClass().equals(PacketPlayInUseEntity.class)) {
                    PacketPlayInUseEntity.EnumEntityUseAction enumAction = (PacketPlayInUseEntity.EnumEntityUseAction) action.get(packet);
                    if (enumAction == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
                        OldSchoolPlayer op = OldSchoolPlayer.getOldSchoolPlayer(sender);
                        if (op.getThreeDimensionGUI() != null) {

                            int id = (int) idField.get(packet);
                            op.getThreeDimensionGUI().getInteractableItems().stream().filter(item -> item.getItem().getId() == id && op.getThreeDimensionGUI().isReady() && sender.equals(item.getItem().getPlayer())).findFirst().ifPresent(item -> {
                                if (item.getInteractable() instanceof InteractableItem.InteractClickable) {
                                    Bukkit.getScheduler().runTask(plugin, () -> {
                                        sender.playSound(sender.getLocation(), Sound.CHICKEN_EGG_POP, 1, 0);
                                        ((InteractableItem.InteractClickable) item.getInteractable()).interact(item);
                                    });
                                }
                            });
                        }

                    }
                }

                return super.onPacketInAsync(sender, channel, packet);


            }

        };
    }

}
