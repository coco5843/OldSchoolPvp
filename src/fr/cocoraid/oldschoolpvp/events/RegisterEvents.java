package fr.cocoraid.oldschoolpvp.events;

import fr.cocoraid.oldschoolpvp.OldSchoolPvp;
import fr.cocoraid.oldschoolpvp.area.Safezone;
import fr.cocoraid.oldschoolpvp.events.listeners.*;
import fr.cocoraid.oldschoolpvp.events.protocol.InteractableItemProtocol;
import org.bukkit.Bukkit;

public class RegisterEvents {

    public RegisterEvents(OldSchoolPvp plugin)  {
        Bukkit.getPluginManager().registerEvents(new Safezone(),plugin);
        Bukkit.getPluginManager().registerEvents(new OcelotConffetiEvent(),plugin);
        Bukkit.getPluginManager().registerEvents(new GiveKitEvent(),plugin);
        Bukkit.getPluginManager().registerEvents(new JoinLeaveEvent(),plugin);
        Bukkit.getPluginManager().registerEvents(new SoupEvent(),plugin);
        Bukkit.getPluginManager().registerEvents(new CancelEvent(),plugin);
        Bukkit.getPluginManager().registerEvents(new DeathEvent(),plugin);
        Bukkit.getPluginManager().registerEvents(new DropEvent(),plugin);
        Bukkit.getPluginManager().registerEvents(new TagDetectEvent(),plugin);
        Bukkit.getPluginManager().registerEvents(new GuiItemSelectEvent(),plugin);
        new InteractableItemProtocol(plugin);
    }
}
