package fr.cocoraid.oldschoolpvp.commands.admin;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.OldSchoolPvp;
import fr.cocoraid.oldschoolpvp.events.listeners.CancelEvent;
import fr.cocoraid.oldschoolpvp.language.Language;
import fr.cocoraid.oldschoolpvp.utils.ActionBar;
import fr.cocoraid.oldschoolpvp.utils.ezcommand.CommandInfo;
import fr.cocoraid.oldschoolpvp.utils.ezcommand.EzCommand;
import org.bukkit.entity.Player;

import java.util.List;

@CommandInfo(name = "PvpToggle", desc = "Toggle the pvp in warzone", perm = "admin.pvptoggle")
public class PvpToggleCMD extends EzCommand {

    public void onCommand(Player p, String[] args) {
        if(args.length == 0) {
            Language lang = OldSchoolPlayer.getOldSchoolPlayer(p).getLanguage();
            if(CancelEvent.isPvp()) {
                CancelEvent.setPvp(false);
                new ActionBar(lang.pvpDisabled).sendToAll();
            } else {
                CancelEvent.setPvp(true);
                new ActionBar(lang.pvpEnabled).sendToAll();
            }
        }

    }

    public List<String> onTabComplete(Player p, String[] args) {
        return null;
    }

}
