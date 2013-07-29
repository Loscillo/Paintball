package fr.jules_cesar.Paintball.Util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YmlUtil {

    private FileConfiguration Config = null;
    private File ConfigFile = null;

    public YmlUtil(File dataFolder, String filename){
    	ConfigFile = new File(dataFolder, filename + ".yml");
    	Config = YamlConfiguration.loadConfiguration(ConfigFile);
    }

    public void set(String name, Object object){
    	Config.set(name, object);
    }

    public Object get(String name){
    	return Config.get(name);
    }

    public void save() throws IOException{
	Config.save(ConfigFile);
    }

    public void delete(){
    	ConfigFile.delete();
    }

    public static boolean exists(File dataFolder, String filename){
    	return new File(dataFolder, filename + ".yml").exists();
    }
}
