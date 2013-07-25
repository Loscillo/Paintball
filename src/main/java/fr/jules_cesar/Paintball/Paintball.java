package fr.jules_cesar.Paintball;

import java.util.Locale;
import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.bukkitutils.command.CommandsRegistration;

public class Paintball extends JavaPlugin implements Listener{

	private static Arene arene = new Arene();
	private static Partie partie;
	private Logger log = getLogger();
	
	public void onEnable(){
		// Events
		getServer().getPluginManager().registerEvents(new PaintballListener(this), this);
		
		// Configuration
		if(!this.getDataFolder().exists()) this.getDataFolder().mkdir();
		arene = (Arene) new GsonUtil(log, getDataFolder().getPath()).lire("location", Arene.class);

		// Commandes
		CommandsRegistration register = new CommandsRegistration(this, Locale.FRANCE);
		register.register(new PaintballCommands(this));
	}
	
	public void onDisable(){
		new GsonUtil(log, getDataFolder().getPath()).ecrire("location", arene);
	}
	
	public static Arene getArene(){
		return arene;
	}
	
	public static Partie getPartie(){
		return partie;
	}
	
	public static void setPartie(Partie p){
		partie = p;
	}
}
