package fr.cocoraid.oldschoolpvp;

import fr.cocoraid.oldschoolpvp.area.Cuboid;
import fr.cocoraid.oldschoolpvp.area.Safezone;
import fr.cocoraid.oldschoolpvp.commands.RegisterCommands;
import fr.cocoraid.oldschoolpvp.events.RegisterEvents;
import fr.cocoraid.oldschoolpvp.language.LanguageLoader;
import fr.cocoraid.oldschoolpvp.nametag.NameTag;
import fr.cocoraid.oldschoolpvp.playerdata.PlayerDatabase;
import fr.cocoraid.oldschoolpvp.updater.Updater;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class OldSchoolPvp extends JavaPlugin {


    public static Location SPAWN;

    private PlayerDatabase database;
    private NameTag nametag;


    private static OldSchoolPvp instance;
    @Override
    public void onEnable() {
        instance = this;
        new RegisterEvents(this);
        new RegisterCommands();
        new LanguageLoader(this);
        this.nametag = new NameTag();
        this.database = new PlayerDatabase(this);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Updater(this), 1L, 1L);
        new BukkitRunnable() {
            public void run() {
                SPAWN = new Location(Bukkit.getWorld("kitpvp"),-61,8,24);
                Safezone.setCuboid(new Cuboid(new Location(Bukkit.getWorld("kitpvp"),-33,7,40,0,0),
                        new Location(Bukkit.getWorld("kitpvp"),-109,29,6,0,0) ));
            }
        }.runTaskLater(OldSchoolPvp.getInstance(), 1);
    }



    @Override
    public void onDisable() {

    }

    public static OldSchoolPvp getInstance() {
        return instance;
    }


    public PlayerDatabase getPlayerDatabase() {
        return database;
    }

    public NameTag getNametag() {
        return nametag;
    }
}
