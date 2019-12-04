package fr.cocoraid.oldschoolpvp;

import fr.cocoraid.oldschoolpvp.gui.ThreeDimensionGUI;
import fr.cocoraid.oldschoolpvp.kit.ClassicKit;
import fr.cocoraid.oldschoolpvp.language.Language;
import fr.cocoraid.oldschoolpvp.language.LanguageLoader;
import fr.cocoraid.oldschoolpvp.playerdata.datas.ImportantData;
import fr.cocoraid.oldschoolpvp.playerdata.datas.PerformanceData;
import fr.cocoraid.oldschoolpvp.playerdata.datas.PreferenceData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class OldSchoolPlayer {

    /**
     * Saved data
     */
    private PreferenceData preferences;
    private PerformanceData performances;
    private ImportantData playerdata;
    private Language language = new Language();

    private ThreeDimensionGUI threeDimensionGUI;
    private LinkedList<Class<? extends ThreeDimensionGUI>> previousMenus = new LinkedList<>();


    private static Map<UUID,OldSchoolPlayer> oldschoolplayers = new HashMap();

    private Map<UUID,Double> totalDamageReceived = new HashMap<>();
    private ClassicKit kit;
    private boolean tagged;
    private boolean recentHit;

    private Player p;
    public OldSchoolPlayer(Player p) {
        this.p = p;

        PlayerInventory inv = p.getInventory();
        p.setHealth(20);
        p.setFoodLevel(20);
        inv.clear();
        inv.setChestplate(null);
        inv.setHelmet(null);
        inv.setLeggings(null);
        inv.setBoots(null);
        p.teleport(OldSchoolPvp.SPAWN);

        p.setScoreboard(OldSchoolPvp.getInstance().getNametag().getScoreboard());
        oldschoolplayers.put(p.getUniqueId(),this);
    }

    public void unregister() {
        if(getKit() != null)
            getKit().removeKit();

        getPreferences().uncache();
        getPerformances().uncache();
    }

    public static OldSchoolPlayer getOldSchoolPlayer(Player p) {
        if(oldschoolplayers.containsKey(p.getUniqueId())) return oldschoolplayers.get(p.getUniqueId());
        else return new OldSchoolPlayer(p);
    }

    public void setRecentHit(boolean recentHit) {
        this.recentHit = recentHit;
    }

    public boolean hasRecentHit() {
        return recentHit;
    }

    public void setKit(ClassicKit kit) {
        this.kit = kit;
    }

    public ClassicKit getKit() {
        return kit;
    }

    public boolean hasKit() {
        return kit != null;
    }

    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    public boolean isTagged() {
        return tagged;
    }

    public static Map<UUID, OldSchoolPlayer> getOldschoolplayers() {
        return oldschoolplayers;
    }

    public Player getPlayer() {
        return p;
    }

    public Map<UUID, Double> getTotalDamageReceived() {
        return totalDamageReceived;
    }


    public void setPerformances(PerformanceData performances) {
        this.performances = performances;
    }

    public void setPreferences(PreferenceData preferences) {
        this.language = LanguageLoader.getLanguage(preferences.getLang());
        this.preferences = preferences;
    }

    public PreferenceData getPreferences() {
        return preferences;
    }

    public PerformanceData getPerformances() {
        return performances;
    }

    public ImportantData getPlayerdata() {
        return playerdata;
    }

    public void setLanguageCache(String lang) {
        this.language = LanguageLoader.getLanguage(lang);
    }

    public void setLanguage(String lang) {
        this.preferences.setLang(lang);
        this.language = LanguageLoader.getLanguage(lang);
    }

    public Language getLanguage() {
        return language;
    }

    public void setPlayerdata(ImportantData playerdata) {
        this.playerdata = playerdata;
    }

    public ThreeDimensionGUI getThreeDimensionGUI() {
        return threeDimensionGUI;
    }

    public void setThreeDimensionGUI(ThreeDimensionGUI threeDimensionGUI) {
        this.threeDimensionGUI = threeDimensionGUI;
    }

    public boolean withdraw(int credit, String type) {
        if(getPlayerdata().getCredit() < credit) {
            p.sendMessage(ChatColor.RED + language.notenoughcredit);
            return false;
        }

        p.sendMessage(language.purchased.replace("%item",type).replace("%credit", String.valueOf(credit) + (credit > 1 ? " credits" : "credit")));
        return true;
    }

    public void addPreviousMenu(ThreeDimensionGUI gui) {
        previousMenus.add(gui.getClass());
    }

    public void removePreviousMenu(ThreeDimensionGUI gui) {
        previousMenus.remove(gui.getClass());
    }

    public LinkedList<Class<? extends ThreeDimensionGUI>> getPreviousMenus() {
        return previousMenus;
    }

    public boolean hasPreviousMenu() {
        return !previousMenus.isEmpty();
    }
}
