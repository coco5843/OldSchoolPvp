package fr.cocoraid.oldschoolpvp.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class UtilItem {


    private ItemStack itemStack;
    public UtilItem(Material material, String displayname) {
        this.itemStack = new ItemStack(material,1,(byte) 0);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayname);
        itemStack.setItemMeta(meta);
    }

    public UtilItem(Material material, byte data, String displayname) {
        this.itemStack = new ItemStack(material,1,data);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayname);
        itemStack.setItemMeta(meta);
    }

    public ItemStack build() {
        return itemStack;
    }

    public static ColorData getGreatRandomColor() {
        Color color = null;
        byte data = (byte) 0;
        switch (UtilMath.randomRange(0, 8)) {
            case 0:
                data = 0;
                color = Color.WHITE;
                break;
            case 1:
                data = 1;
                color = Color.ORANGE;
                break;
            case 2:
                data = 2;
                color = Color.FUCHSIA;
                break;
            case 3:
                data = 4;
                color = Color.YELLOW;
                break;
            case 4:
                data = 5;
                color = Color.GREEN;
                break;
            case 5:
                data = 9;
                color = Color.NAVY;
                break;
            case 6:
                data = 10;
                color = Color.PURPLE;
                break;
            case 7:
                data = 11;
                color = Color.BLUE;
                break;
            case 8:
                data = 14;
                color = Color.RED;
                break;
        }
        return new ColorData(data, color);
    }

    public static ItemStack setDisplayName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static class ColorData {
        private byte data;
        private Color color;

        public ColorData(byte data, Color color) {
            this.data = data;
            this.color = color;
        }

        public byte getData() {
            return data;
        }

        public Color getColor() {
            return color;
        }
    }

    public static ItemStack getSkull(String texture) {
        String url = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv" + texture;
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        if(url.isEmpty())return head;
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }
}
