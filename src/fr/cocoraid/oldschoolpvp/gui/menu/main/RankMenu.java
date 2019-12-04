package fr.cocoraid.oldschoolpvp.gui.menu.main;

import fr.cocoraid.oldschoolpvp.gui.ThreeDimensionGUI;
import fr.cocoraid.oldschoolpvp.gui.item.InteractableItem;
import fr.cocoraid.oldschoolpvp.gui.item.Item3D;
import fr.cocoraid.oldschoolpvp.utils.UtilItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RankMenu extends ThreeDimensionGUI {

    static ItemStack donator = new UtilItem(Material.EMERALD, "§a§ldonator").build();
    static ItemStack supporter = new UtilItem(Material.GOLD_BLOCK, "§6§lsupporter").build();
    static ItemStack ambassador = new UtilItem(Material.DIAMOND_BLOCK, "§3§lambassador").build();
    static ItemStack youtuber = new UtilItem(Material.REDSTONE_BLOCK, "§4You§ftuber").build(); //total damage get / total damage gave %
    static ItemStack mod = new UtilItem(Material.PISTON_STICKY_BASE, "§5mod").build();


    public RankMenu(Player player) {
        super(player, "Your personal stats", true);
    }


    @Override
    public void inizializeGUI() {

        getItems().add(new InteractableItem(new Item3D(player, donator), (InteractableItem.InteractClickable) (e) -> {

        }));

        getItems().add(new InteractableItem(new Item3D(player, supporter), (InteractableItem.InteractClickable) (e) -> {

        }));
        getItems().add(new InteractableItem(new Item3D(player, ambassador), (InteractableItem.InteractClickable) (e) -> {

        }));
        getItems().add(new InteractableItem(new Item3D(player, youtuber), (InteractableItem.InteractClickable) (e) -> {

        }));
        getItems().add(new InteractableItem(new Item3D(player, mod), (InteractableItem.InteractClickable) (e) -> {

        }));


    }
}
