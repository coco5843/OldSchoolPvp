package fr.cocoraid.oldschoolpvp.playerdata;

import fr.cocoraid.oldschoolpvp.OldSchoolPlayer;
import fr.cocoraid.oldschoolpvp.OldSchoolPvp;
import fr.cocoraid.oldschoolpvp.playerdata.datas.ImportantData;
import fr.cocoraid.oldschoolpvp.playerdata.datas.PerformanceData;
import fr.cocoraid.oldschoolpvp.playerdata.datas.PreferenceData;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerDatabase {


    /**
     *
     * On player join -> register player if do not exist, with default data
     * if player is existing set file data to cache data
     * When player is changing something don't save it in file except credits (important data)
     * Just save it in cache
     * Then after leaving or server stop (check if server stop is triggering leave event ?)
     * set cache to file
     *
     *
     *
     *
     */

    private OldSchoolPvp instance;


    public PlayerDatabase(OldSchoolPvp instance) {
        if (!instance.getDataFolder().exists())
            instance.getDataFolder().mkdir();

        File databaseFolder = new File(instance.getDataFolder(), "database");
        if(!databaseFolder.exists())
            databaseFolder.mkdirs();
        this.instance = instance;
    }

    private File getPlayerFile(UUID uuid) {
        return new File("plugins/OldSchoolPvp/database", uuid.toString() + ".yml");
    }

    private File getPlayerFile(Player p) {
        return getPlayerFile(p.getUniqueId());
    }

    public boolean isPlayerRegistered(UUID uuid) {
        return getPlayerFile(uuid).exists();
    }

    public void registerPlayer(Player p) {
        OldSchoolPlayer op = OldSchoolPlayer.getOldSchoolPlayer(p);
        File file = getPlayerFile(p);
        if (!file.exists()) {
            try
            {
                file.createNewFile();
                try {
                    //Set default data
                    op.setPreferences((PreferenceData)new PreferenceData(p).uncache());
                    op.setPerformances((PerformanceData)new PerformanceData(p).uncache());
                    op.setPlayerdata((ImportantData)new ImportantData(p).uncache());

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            catch (IOException ev)
            {
                ev.printStackTrace();
            }
        } else {
            op.setPreferences(new PreferenceData(p));
            op.setPlayerdata(new ImportantData(p));
            op.setPerformances(new PerformanceData(p));
        }

        op.getPlayerdata().cache();
        op.getPerformances().cache();

        op.getPreferences().cache();
        //Special option
        op.setLanguageCache(op.getPreferences().getLang());
    }

    //Save data in cache
    public void savePlayerIn() {

    }

    //Save data in file
    public void savePlayerOut() {

    }

}
