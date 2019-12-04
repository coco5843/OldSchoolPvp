package fr.cocoraid.oldschoolpvp.utils.packet;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public abstract class PacketEntity {

    protected EntityLiving entity;
    protected Location location;
    protected Player p;
    public PacketEntity(Player p, Location location) {
        this.location = location;
        this.p = p;
    }

    public void teleport(Location location) {
        this.location = location;
        fakeTeleport(location);
    }

    public void fakeTeleport(Location location) {
        entity.setLocation(location.getX(),location.getY(),location.getZ(),location.getYaw(),location.getPitch());
        PacketPlayOutEntityTeleport teleport = new PacketPlayOutEntityTeleport(entity);
       sendPacket(teleport);
    }



    public void spawn() {
        PacketPlayOutSpawnEntityLiving spawn = new PacketPlayOutSpawnEntityLiving(entity);
        sendPacket(spawn);
    }

    public void remove() {
        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(entity.getId());
        sendPacket(destroy);
    }

    protected void sendPacket(Packet packet) {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }

    public Location getLocation() {
        return location;
    }
}
