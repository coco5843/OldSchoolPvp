package fr.cocoraid.oldschoolpvp.commands.kit;

import java.util.List;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.language.Language;
import fr.cocoraid.oldschoolpvp.utils.ezcommand.CommandInfo;
import fr.cocoraid.oldschoolpvp.utils.ezcommand.EzCommand;
import org.bukkit.entity.Player;



@CommandInfo(name = "sharpness", desc = "Upgrade your sword with sharpness", perm = "kitpvp.sharpness", aliases = {"sharp"})
public class SharpnessCMD extends EzCommand {

    public void onCommand(Player p, String[] args) {
        Language lang = OldSchoolPlayer.getOldSchoolPlayer(p).getLanguage();
        if(args.length == 0) {
            if(!OldSchoolPlayer.getOldSchoolPlayer(p).hasKit()) {
                severe(p,lang.nokit);
                return;
            }
            OldSchoolPlayer.getOldSchoolPlayer(p).getKit().sharpness();
        }

    }
   
    public List<String> onTabComplete(Player p, String[] args) {
        return null;
    }

}