package fr.jules_cesar.Paintball;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.jules_cesar.Paintball.TypeAdapter.LocationAdapter;

public class Paintball extends JavaPlugin implements Listener{

	private static Arene arene = new Arene();
	private static Teleportation teleport = new Teleportation();
	
	public void onEnable(){
		// Events
		getServer().getPluginManager().registerEvents(new PaintballListener(), this);
		
		// Configuration
		if(!this.getDataFolder().exists()) this.getDataFolder().mkdir();
		Gson gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationAdapter()).setPrettyPrinting().create();
		try {
			BufferedReader br = new BufferedReader(new FileReader(getDataFolder().getPath() + "/location.json"));
			teleport = gson.fromJson(br, Teleportation.class);
			br.close();
		} catch (FileNotFoundException e) {
			getLogger().warning("Le fichier de configuration n'est pas disponible. Le plugin ne sera pas actif.");
		} catch (IOException e) {
			getLogger().warning("Erreur de lecture du fichier de configuration. Le plugin ne sera pas actif.");
		}
		getLogger().info(teleport.toString());
		
		// Commandes
		CommandsRegistration register = new CommandsRegistration(this, Locale.FRANCE);
		register.register(new PaintballCommands());
	}
	
	public void onDisable(){
		Gson gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationAdapter()).setPrettyPrinting().create();
		FileWriter writer;
		try {
			writer = new FileWriter(getDataFolder().getPath() + "/location.json");
			writer.write(gson.toJson(teleport));
			writer.close();
		} catch (IOException e) {
			getLogger().warning("Erreur d'ecriture du fichier de configuration. Le plugin risque de ne pas etre actif au prochain lancement.");
		}
	}
	
	public static Arene getArene(){
		return arene;
	}
	
	public static Teleportation getTeleport(){
		return teleport;
	}
}
