package fr.cocoraid.oldschoolpvp.gui.menu;

import fr.cocoraid.oldschoolpvp.gui.ThreeDimensionGUI;
import fr.cocoraid.oldschoolpvp.gui.item.InteractableItem;
import fr.cocoraid.oldschoolpvp.gui.item.Item3D;
import fr.cocoraid.oldschoolpvp.gui.menu.main.PreferencesMenu;
import fr.cocoraid.oldschoolpvp.gui.menu.main.RankMenu;
import fr.cocoraid.oldschoolpvp.gui.menu.main.StatsMenu;
import fr.cocoraid.oldschoolpvp.gui.menu.main.TournamentsMenu;
import fr.cocoraid.oldschoolpvp.utils.UtilItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainMenu extends ThreeDimensionGUI {

    private static ItemStack stats = new UtilItem(Material.BOOKSHELF, "§aStats").build();
    private static ItemStack cosmetics = new UtilItem(Material.GLOWSTONE, "§6Cosmetics").build();
    private static ItemStack rank = new UtilItem(Material.DIAMOND_ORE, "§3Ranks").build();
    private static ItemStack preferences = new UtilItem(Material.IRON_BLOCK, "§fSettings").build();
    private static ItemStack tournaments = new UtilItem(Material.MOSSY_COBBLESTONE, "§2Tournaments").build();


    public MainMenu(Player player) {
        super(player, "OldSchoolPvp server", true);
    }


    @Override
    public void inizializeGUI() {

        getItems().add(new InteractableItem(new Item3D(player, stats), (InteractableItem.InteractClickable) (e) -> {
            new StatsMenu(player).openGui();
        }));

        getItems().add(new InteractableItem(new Item3D(player, cosmetics), (InteractableItem.InteractClickable) (e) -> {

        }));
        getItems().add(new InteractableItem(new Item3D(player, rank), (InteractableItem.InteractClickable) (e) -> {
            new RankMenu(player).openGui();
        }));
        getItems().add(new InteractableItem(new Item3D(player, preferences), (InteractableItem.InteractClickable) (e) -> {
            new PreferencesMenu(player).openGui();
        }));
        getItems().add(new InteractableItem(new Item3D(player, tournaments), (InteractableItem.InteractClickable) (e) -> {
            new TournamentsMenu(player).openGui();
        }));


    }
}
