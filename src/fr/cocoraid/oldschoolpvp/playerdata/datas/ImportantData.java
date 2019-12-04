package fr.cocoraid.oldschoolpvp.playerdata.datas;

import fr.cocoraid.oldschoolpvp.playerdata.PlayerData;
import org.bukkit.entity.Player;

public class ImportantData extends PlayerData {

    private int credit = 10;

    public ImportantData(Player p) {
        super(p);
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        set("Important.credit", credit);
        this.credit = credit;
    }

    public void addCredit(int credit) {
        this.credit += credit;
        set("Important.credit", this.credit);
    }

    public void substractCredit(int credit) {
        this.credit -= credit;
        set("Important.credit", this.credit);
    }
}
