package fr.cocoraid.oldschoolpvp.commands.kit;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.gui.menu.UpgradePvpMenu;
import fr.cocoraid.oldschoolpvp.language.Language;
import fr.cocoraid.oldschoolpvp.utils.Head;
import fr.cocoraid.oldschoolpvp.utils.UtilItem;
import fr.cocoraid.oldschoolpvp.utils.ezcommand.CommandInfo;
import fr.cocoraid.oldschoolpvp.utils.ezcommand.EzCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@CommandInfo(name = "upgrade", desc = "Upgrade your pvp", perm = "kitpvp.upgrade")
public class UpgradeCMD extends EzCommand {

    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            Language lang = OldSchoolPlayer.getOldSchoolPlayer(p).getLanguage();
            if(!OldSchoolPlayer.getOldSchoolPlayer(p).hasKit()) {
                severe(p,lang.nokit);
                return;
            }
           new UpgradePvpMenu(p).openGui();
        }
    }

    public List<String> onTabComplete(Player p, String[] args) {
        return null;
    }
}