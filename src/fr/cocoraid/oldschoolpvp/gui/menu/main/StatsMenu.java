package fr.cocoraid.oldschoolpvp.gui.menu.main;

import fr.cocoraid.oldschoolpvp.gui.ThreeDimensionGUI;
import fr.cocoraid.oldschoolpvp.gui.item.InteractableItem;
import fr.cocoraid.oldschoolpvp.gui.item.Item3D;
import fr.cocoraid.oldschoolpvp.utils.UtilItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StatsMenu  extends ThreeDimensionGUI {

    static ItemStack kills = new UtilItem(Material.NETHERRACK, "§ckills").build();
    static ItemStack deaths = new UtilItem(Material.SOUL_SAND, "§7deaths").build();
    static ItemStack level = new UtilItem(Material.GOLD_BLOCK, "§6level").build();
    static ItemStack damage_rate = new UtilItem(Material.CACTUS, "§adamage rate").build(); //total damage get / total damage gave %
    static ItemStack missclick = new UtilItem(Material.GLASS, "§fmissclick rate").build();


    public StatsMenu(Player player) {
        super(player, "Your personal stats", true);
    }


    @Override
    public void inizializeGUI() {

        getItems().add(new InteractableItem(new Item3D(player, kills), (InteractableItem.InteractClickable) (e) -> {

        }));

        getItems().add(new InteractableItem(new Item3D(player, deaths), (InteractableItem.InteractClickable) (e) -> {

        }));
        getItems().add(new InteractableItem(new Item3D(player, level), (InteractableItem.InteractClickable) (e) -> {

        }));
        getItems().add(new InteractableItem(new Item3D(player, damage_rate), (InteractableItem.InteractClickable) (e) -> {

        }));
        getItems().add(new InteractableItem(new Item3D(player, missclick), (InteractableItem.InteractClickable) (e) -> {

        }));


    }
}
