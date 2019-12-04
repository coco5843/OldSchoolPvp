package fr.cocoraid.oldschoolpvp.utils.ezcommand;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

public class CommandRegistry {

    @SafeVarargs
    public static void register(EzCommand... cmds) {
        for(EzCommand cmd : cmds) {

            ((CraftServer) Bukkit.getServer()).getCommandMap().register(cmd.getName(), cmd);
        }
    }   

}
