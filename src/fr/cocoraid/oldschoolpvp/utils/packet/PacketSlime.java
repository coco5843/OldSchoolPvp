package fr.cocoraid.oldschoolpvp.utils.packet;

import fr.cocoraid.oldschoolpvp.utils.UtilMath;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;

public class PacketSlime extends PacketEntity {

    private Location location;
    private EntitySlime slime;
    public PacketSlime(Player p, Location location) {
        super(p,location);
        this.location = location;
        slime = new EntitySlime(((CraftWorld)location.getWorld()).getHandle());
        slime.setLocation(location.getX(),location.getY(),location.getZ(),location.getYaw(),location.getPitch());
        slime.setInvisible(true);
        slime.setSize(2);
        this.entity = slime;
    }

    @Override
    public void spawn() {
        super.spawn();
    }

    public void rotate(float yaw) {
        PacketPlayOutEntityHeadRotation rotation = new PacketPlayOutEntityHeadRotation(slime, UtilMath.toPackedByte(yaw));
        this.location.setYaw(yaw);
        sendPacket(rotation);
    }

    public int getId() {
        return slime.getId();
    }

}
