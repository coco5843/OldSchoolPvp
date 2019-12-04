package fr.cocoraid.oldschoolpvp.utils;

import org.bukkit.inventory.ItemStack;

public enum Head {

    NEXT(UtilItem.getSkull("YzJmOTEwYzQ3ZGEwNDJlNGFhMjhhZjZjYzgxY2Y0OGFjNmNhZjM3ZGFiMzVmODhkYjk5M2FjY2I5ZGZlNTE2In19fQ==")),
    PREVIOUS(UtilItem.getSkull("ZjI1OTliZDk4NjY1OWI4Y2UyYzQ5ODg1MjVjOTRlMTlkZGQzOWZhZDA4YTM4Mjg0YTE5N2YxYjcwNjc1YWNjIn19fQ=="));

    private ItemStack item;
    Head(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}
