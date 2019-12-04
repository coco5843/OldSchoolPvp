package fr.cocoraid.oldschoolpvp.utils.packet;

import fr.cocoraid.oldschoolpvp.utils.UtilMath;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PacketArmorStand extends PacketEntity {

    private Location location;
    private EntityArmorStand armorStand;
    private PacketPlayOutEntityEquipment equip;
    private ItemStack head;
    public PacketArmorStand(Player p, Location location) {
        super(p,location);
        this.location = location;
        this.p = p;
        armorStand = new EntityArmorStand(((CraftWorld)location.getWorld()).getHandle());
        armorStand.setLocation(location.getX(),location.getY(),location.getZ(),location.getYaw(),location.getPitch());
        this.entity = armorStand;

    }

    public PacketArmorStand setAsNameDisplayer(String displayName) {
        armorStand.setCustomName(displayName);
        armorStand.setCustomNameVisible(true);
        armorStand.setInvisible(true);
        return this;
    }

    public PacketArmorStand setAsItemDisplayer(ItemStack item) {
        armorStand.setInvisible(true);
        armorStand.setSmall(true);
        this.head = item;
        equip = new PacketPlayOutEntityEquipment(armorStand.getId(),4, CraftItemStack.asNMSCopy(item));
        return this;
    }


    public void rotate(float yaw) {
        PacketPlayOutEntity.PacketPlayOutEntityLook rotate = new PacketPlayOutEntity.PacketPlayOutEntityLook(armorStand.getId(), UtilMath.toPackedByte(yaw),(byte)0,true);
        sendPacket(rotate);
        this.location.setYaw(yaw);
    }

    public void updateName(String name) {
        armorStand.setCustomName(name);
        PacketPlayOutEntityMetadata meta = new PacketPlayOutEntityMetadata(armorStand.getId(),armorStand.getDataWatcher(),true);
        sendPacket(meta);
    }


    @Override
    public void spawn() {
        super.spawn();
        if(equip != null)
            sendPacket(equip);
    }

    public EntityArmorStand getArmorStand() {
        return armorStand;
    }

    public ItemStack getHead() {
        return head;
    }

    public boolean isSmall() {
        return armorStand.isSmall();
    }
}
