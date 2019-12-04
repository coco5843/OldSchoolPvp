package fr.cocoraid.oldschoolpvp.language;

import fr.cocoraid.oldschoolpvp.OldSchoolPvp;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class LanguageLoader {

    private static Map<String, Language> languages = new HashMap<>();

    public LanguageLoader(OldSchoolPvp instance) {

        //Load language folder
        if (!instance.getDataFolder().exists())
            instance.getDataFolder().mkdir();

        File databaseFolder = new File(instance.getDataFolder(), "language");
        if(!databaseFolder.exists())
            databaseFolder.mkdirs();

        //Create Default language if not exist
        File file = getLanguageFile("English");
        if (!file.exists()) {
            try {
                FileConfiguration c = YamlConfiguration.loadConfiguration(file);
                file.createNewFile();
                Language language = new Language();
                for (Field field : language.getClass().getDeclaredFields()) {
                    c.set(field.getName(), field.get(language));
                }
                c.save(file);

            }
            catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //Load all language found
        File[] files = new File("plugins/OldSchoolPvp/language").listFiles();
        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".yml")) {
                try {
                    FileConfiguration c = YamlConfiguration.loadConfiguration(f);
                    Language lang = new Language();
                    for (Field field : lang.getClass().getDeclaredFields()) {
                        field.set(lang, c.get(field.getName()));
                    }
                    languages.put(f.getName().replace(".yml", ""), lang);
                    c.save(f);
                } catch (IOException | IllegalAccessException  e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File getLanguageFile(String language) {
        return new File("plugins/OldSchoolPvp/language", language + ".yml");
    }

    public static Language getLanguage(String lang) {
        return getLanguages().get(lang);
    }

    public static Map<String, Language> getLanguages() {
        return languages;
    }
}
