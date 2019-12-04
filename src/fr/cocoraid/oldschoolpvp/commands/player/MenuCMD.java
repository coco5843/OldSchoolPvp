package fr.cocoraid.oldschoolpvp.commands.player;

import fr.cocoraid.oldschoolpvp.gui.menu.MainMenu;
import fr.cocoraid.oldschoolpvp.utils.ezcommand.CommandInfo;
import fr.cocoraid.oldschoolpvp.utils.ezcommand.EzCommand;
import fr.cocoraid.oldschoolpvp.utils.textanim.Glow;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

@CommandInfo(name = "menu", desc = "Open the main menu", perm = "kitpvp.menu")
public class MenuCMD extends EzCommand {

    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            new MainMenu(p).openGui();
        }
    }

    public List<String> onTabComplete(Player p, String[] args) {
        return null;
    }
}