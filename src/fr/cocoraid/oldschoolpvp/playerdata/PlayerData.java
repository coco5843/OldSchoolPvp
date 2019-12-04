package fr.cocoraid.oldschoolpvp.playerdata;

import fr.cocoraid.oldschoolpvp.OldSchoolPvp;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;

public abstract class PlayerData {

    private static OldSchoolPvp instance = OldSchoolPvp.getInstance();

    protected UUID uuid;
    protected File file;
    private Player player;
    public PlayerData(Player player) {
        this.uuid = player.getUniqueId();
        this.player = player;
        this.file = getPlayerFile(player);
    }

    // ici on set les fields en fonction de la class name, je dois chopper le path qui correspond au nom de la class
    public void cache() {
        try {
            FileConfiguration c = YamlConfiguration.loadConfiguration(file);
            String dataType = getClass().getSimpleName().replace("Data","");
            ConfigurationSection section = c.getConfigurationSection(dataType);
            for (String s : section.getKeys(false)) {
                Field f = getClass().getDeclaredField(s);
                f.setAccessible(true);
                f.set(this,c.get(dataType + "." + s));
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public PlayerData uncache() {
        try {
            FileConfiguration c = YamlConfiguration.loadConfiguration(file);
            for (Field field : getClass().getDeclaredFields()) {
                field.setAccessible(true);
                c.set(getClass().getSimpleName().replace("Data","") + "." + field.getName(), field.get(this));
            }
            c.save(file);
        } catch(IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }


    protected void set(String type, Object o) {
        try {
            FileConfiguration c = YamlConfiguration.loadConfiguration(file);
            c.set(type, o);
            c.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private File getPlayerFile(UUID uuid) {
        return new File("plugins/OldSchoolPvp/database", uuid.toString() + ".yml");
    }

    private File getPlayerFile(Player p) {
        return getPlayerFile(p.getUniqueId());
    }

}
