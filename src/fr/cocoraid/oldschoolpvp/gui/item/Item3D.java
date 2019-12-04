package fr.cocoraid.oldschoolpvp.gui.item;

import fr.cocoraid.oldschoolpvp.utils.packet.PacketArmorStand;
import fr.cocoraid.oldschoolpvp.utils.packet.PacketSlime;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * Created by cocoraid on 02/07/2016.
 */
public class Item3D {

    private Player player;
    private Location location;
    private PacketArmorStand itemDisplay;
    private PacketArmorStand displayName;
    private PacketSlime selector;

    private ItemStack item;
    private boolean spawned = false;
    private boolean enable;
    private float yawRotation = 0;

    public Item3D(Player player, ItemStack item) {
        this.player = player;
        this.item = item;

        this.enable = true;
        Location temp = player.getEyeLocation();
        temp.setPitch(0);
        location = player.getEyeLocation().add(temp.getDirection());
        displayName = new PacketArmorStand(player,temp).setAsNameDisplayer(item.getItemMeta().getDisplayName());
        itemDisplay = new PacketArmorStand(player,temp).setAsItemDisplayer(item);
        selector = new PacketSlime(player,temp);
    }

    public Item3D(Player player, String arrow) {
        this.player = player;
        this.enable = true;
        Location temp = player.getEyeLocation();
        temp.setPitch(0);
        location = player.getEyeLocation().add(temp.getDirection());
        displayName = new PacketArmorStand(player,temp).setAsNameDisplayer(arrow);
        selector = new PacketSlime(player,temp);
    }

    public Item3D setPosition(Location loc) {

        displayName.spawn();
        displayName.teleport(loc.clone().subtract(0,0.2,0));

        selector.spawn();
        selector.teleport(loc.clone().add(0,1.5,0));
        selector.rotate(yawRotation);
        location = loc;
        this.spawned = true;

        return this;
    }

    public Item3D setPosition(Location loc, float yawRotation) {
        this.yawRotation = yawRotation;
        itemDisplay.spawn();
        itemDisplay.teleport(loc.clone().add(0,0,0));
        itemDisplay.rotate(itemDisplay.getHead().getType() == Material.ANVIL ? yawRotation + 90 : yawRotation);

        displayName.spawn();
        displayName.teleport(loc.clone().subtract(0,0.9,0));

        selector.spawn();
        selector.teleport(loc.clone().add(0,1,0));
        selector.rotate(yawRotation);
        location = loc;
        this.spawned = true;

        return this;
    }

    public void move(boolean back) {
        if (back) {
            itemDisplay.fakeTeleport(itemDisplay.getLocation());
            selector.fakeTeleport(selector.getLocation());
            displayName.teleport(displayName.getLocation());
        } else {
            Location l = getLocation().clone();
            Vector v = player.getLocation().toVector().subtract(l.toVector()).normalize();
            l.setDirection(v);
            l.setPitch(0F);
            Vector toadd = l.getDirection().multiply(0.3);
            itemDisplay.fakeTeleport(itemDisplay.getLocation().clone().add(toadd));
            itemDisplay.rotate(l.getYaw());
            displayName.fakeTeleport(displayName.getLocation().clone().add(toadd));
            selector.fakeTeleport(selector.getLocation().clone().add(toadd));
        }
    }


    public void teleport(Location loc) {
        Location l = itemDisplay.isSmall() ? loc.clone().add(0, 1.3, 0) : loc;
        itemDisplay.teleport(l);
        displayName.teleport(loc.clone().add(0, 0.3f, 0));
        selector.teleport(loc.clone().add(0, 1.8, 0));
        this.location = loc;
    }



    public Location getLocation() {
        return location;
    }

    public void remove() {
        if (itemDisplay != null)
            itemDisplay.remove();

        if (displayName != null)
            displayName.remove();

        if (selector != null)
            selector.remove();


        this.spawned = false;

    }

    public PacketArmorStand getItemDisplay() {
        return itemDisplay;
    }

    public int getId() {
        return selector.getId();
    }


    public Player getPlayer() {
        return player;
    }

    public boolean isSpawned() {
        return spawned;
    }

    public boolean isEnable() {
        return enable;
    }

    public PacketSlime getSelector() {
        return selector;
    }

    public PacketArmorStand getDisplayName() {
        return displayName;
    }

    public float getYawRotation() {
        return yawRotation;
    }

    public ItemStack getItem() {
        return item;
    }
}
