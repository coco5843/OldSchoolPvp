package fr.cocoraid.oldschoolpvp.playerdata.datas;

import fr.cocoraid.oldschoolpvp.playerdata.PlayerData;
import org.bukkit.entity.Player;

public class PerformanceData extends PlayerData {

    private int kills = 0;
    private int death = 0;

    public PerformanceData(Player p) {
        super(p);
    }

    public void addKill() {
        this.kills++;
    }

    public void addDeath() {
        this.death++;
    }

    public int getKills() {
        return kills;
    }

    public int getDeath() {
        return death;
    }
}
