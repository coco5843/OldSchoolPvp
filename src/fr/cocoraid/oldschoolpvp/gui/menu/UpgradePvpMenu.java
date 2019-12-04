package fr.cocoraid.oldschoolpvp.gui.menu;

import fr.cocoraid.oldschoolpvp.gui.ThreeDimensionGUI;
import fr.cocoraid.oldschoolpvp.gui.item.InteractableItem;
import fr.cocoraid.oldschoolpvp.gui.item.Item3D;
import fr.cocoraid.oldschoolpvp.utils.UtilItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class UpgradePvpMenu extends ThreeDimensionGUI {

    static ItemStack sharpness = new UtilItem(Material.ENCHANTMENT_TABLE, "§3Sharpness").build();
    static ItemStack chestplate = new UtilItem(Material.DIAMOND_ORE, "§bChestPlate").build();
    static ItemStack repair = new UtilItem(Material.ANVIL, "§7Repair").build();
    static ItemStack soup = new UtilItem(Material.HUGE_MUSHROOM_1, "§6Soups").build();
    static ItemStack force = new UtilItem(Material.NETHERRACK,"§cForce").build();


    public UpgradePvpMenu(Player player) {
        super(player, "PvP Upgrade", true);
    }


    @Override
    public void inizializeGUI() {

        getItems().add(new InteractableItem(new Item3D(player,sharpness), (InteractableItem.InteractClickable) (e) -> {
            op.getKit().sharpness();
        }));

        getItems().add(new InteractableItem(new Item3D(player,chestplate), (InteractableItem.InteractClickable) (e) -> {
            op.getKit().chestPlate();
        }));
        getItems().add(new InteractableItem(new Item3D(player,repair), (InteractableItem.InteractClickable) (e) -> {
            op.getKit().repair();
        }));
        getItems().add(new InteractableItem(new Item3D(player,soup), (InteractableItem.InteractClickable) (e) -> {

        }));
        getItems().add(new InteractableItem(new Item3D(player,force), (InteractableItem.InteractClickable) (e) -> {

        }));


    }


}
