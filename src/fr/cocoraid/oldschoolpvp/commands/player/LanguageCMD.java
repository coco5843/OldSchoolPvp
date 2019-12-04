package fr.cocoraid.oldschoolpvp.commands.player;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.language.Language;
import fr.cocoraid.oldschoolpvp.language.LanguageLoader;
import fr.cocoraid.oldschoolpvp.utils.ezcommand.CommandInfo;
import fr.cocoraid.oldschoolpvp.utils.ezcommand.EzCommand;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@CommandInfo(name = "Language", desc = "Change language preference", aliases = {"lang"})
public class LanguageCMD extends EzCommand {

    public void onCommand(Player p, String[] args) {

        if(args.length == 0) {
            available(p);
        } else if(args.length == 1) {
            String converted = StringUtils.capitalize(args[0].toLowerCase());
            Language lang = OldSchoolPlayer.getOldSchoolPlayer(p).getLanguage();
            if(LanguageLoader.getLanguages().containsKey(converted)) {
                OldSchoolPlayer.getOldSchoolPlayer(p).setLanguage(converted);
                p.playSound(p.getLocation(), Sound.ORB_PICKUP,1,1);
                info(p,lang.newLang);
            } else {
                severe(p,lang.langnotfound);
                available(p);
            }
        }

    }

    public List<String> onTabComplete(Player p, String[] args) {
        return null;
    }

    private void available(Player p) {
        p.sendMessage("§bYour current language: " + OldSchoolPlayer.getOldSchoolPlayer(p).getPreferences().getLang());
        String available = LanguageLoader.getLanguages().keySet().stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        info(p,"Available languages: ");
        info(p, ChatColor.GRAY + available);
    }

}
