package fr.cocoraid.oldschoolpvp.playerdata.datas;

import fr.cocoraid.oldschoolpvp.playerdata.PlayerData;
import org.bukkit.entity.Player;

public class PreferenceData extends PlayerData {

    private String lang = "English";
    private int swordPosition = 0;
    private boolean allowDropSword = true;
    private boolean autoRemoveSoup = false;

    public PreferenceData(Player p) {
        super(p);
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public void setSwordPosition(int swordPosition) {
        this.swordPosition = swordPosition;
    }

    public void setAllowDropSword(boolean allowDropSword) {
        this.allowDropSword = allowDropSword;
    }

    public void setAutoRemoveSoup(boolean autoRemoveSoup) {
        this.autoRemoveSoup = autoRemoveSoup;
    }

    public int getSwordPosition() {
        return swordPosition;
    }

    public boolean isAutoRemoveSoup() {
        return autoRemoveSoup;
    }

    public boolean isAllowDropSword() {
        return allowDropSword;
    }
}
