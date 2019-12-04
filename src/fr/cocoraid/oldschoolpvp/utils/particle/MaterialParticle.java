package fr.cocoraid.oldschoolpvp.utils.particle;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class MaterialParticle {

    private Packet packet;
    public MaterialParticle(Location l, float vx,float vy, float vz, float speed, int amount, int materialID, byte data) {
         this.packet = new PacketPlayOutWorldParticles(EnumParticle.BLOCK_CRACK, true,(float) l.getX(),(float) l.getY(),(float) l.getZ(),vx, vy, vz, speed, amount, materialID | (data << 12));
    }

    public void send(Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
