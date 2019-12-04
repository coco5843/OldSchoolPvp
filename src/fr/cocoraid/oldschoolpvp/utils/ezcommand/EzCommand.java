package fr.cocoraid.oldschoolpvp.utils.ezcommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.language.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public abstract class EzCommand extends Command {

    private CommandInfo info = getClass().getAnnotation(CommandInfo.class); 

    protected EzCommand() {
        super("");
        setName(info.name());
        setDescription(info.desc());
        setPermission(info.perm());
        setAliases(Arrays.asList(info.aliases()));
    }

    public String getName() {
        return info.name();
    }

    public String getDescription() {
        return info.desc();
    }

    public String getPermission() {
        return info.perm();
    }

    public String getUsage() {
        return Arrays.toString(info.usage());
    }

    public List<String> getAliases() {
        return Arrays.asList(info.aliases());
    }

    //Easier way to message player in sub-class
    public void msg(CommandSender cs, String... msgs) {
        for(String msg : msgs) {
            cs.sendMessage(msg);
        }
    }

    //Easier way to message player in sub-class
    public void info(CommandSender cs, String... msgs) {
        for(String msg : msgs) {
            cs.sendMessage(ChatColor.AQUA + msg);
        }
    }

    //Easier way to message player in sub-class
    public void severe(CommandSender cs, String... msgs) { 
        for(String msg : msgs) {
            cs.sendMessage(ChatColor.RED + msg);
        }
    }

    public abstract void onCommand(Player p, String[] args); 
    public abstract List<String> onTabComplete(Player p, String[] args);

    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)) {
            severe(sender, "Only players can use this command");
            return true;
        }

        Player p = (Player) sender;
        Language lang = OldSchoolPlayer.getOldSchoolPlayer(p).getLanguage();
        //If the permission is "" it means that there is none and anyone can execute this command
        if(!(info.perm().equals("")) && !(p.hasPermission(info.perm()))) {
            severe(p, lang.noperm);
            return true;
        }

        onCommand(p, args);
        return true;
    }

    public List<String> tabComplete(CommandSender sender, String label, String[] args) {
        List<String> completions = onTabComplete((Player) sender, args);
        //If returned null it won't show a list of online players like it would by default
        if(completions == null) return new ArrayList<String>();
        return completions; 
    }

}