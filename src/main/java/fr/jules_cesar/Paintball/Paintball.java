package fr.jules_cesar.Paintball;

import java.io.File;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.localization.Localization;
import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.jules_cesar.Paintball.Util.GsonUtil;
import fr.jules_cesar.Paintball.Util.InventoryUtil;

public class Paintball extends JavaPlugin implements Listener{

	private static Arene arene = new Arene();
	private static Partie partie;
	public static PluginMessages messages;
	
	public void onEnable(){
		// Events
		getServer().getPluginManager().registerEvents(new PaintballListener(), this);
		
		// Configuration
		if(!this.getDataFolder().exists()) this.getDataFolder().mkdir();
		if(!new File(this.getDataFolder().getPath() + "/inventories/").exists()) new File(this.getDataFolder().getPath() + "/inventories/").mkdir();
		arene = (Arene) new GsonUtil().lire("location", Arene.class);

		// Commandes
		CommandsRegistration register = new CommandsRegistration(this, Locale.FRANCE);
		register.register(new PaintballCommands());
		
		// Messages
        Localization localisation = new Localization(this, Locale.FRANCE);
        messages = localisation.get("messages");
	}
	
	public void onDisable(){
		new GsonUtil().ecrire("location", arene);
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
	
	public static File getFolder(){
		return Bukkit.getServer().getPluginManager().getPlugin("Paintball").getDataFolder();
	}
	
	public static void saveInventory(Player joueur, char equipe){
        try{
        	new InventoryUtil(joueur).ecrire();
        	joueur.getInventory().clear();
        	joueur.getInventory().addItem(new ItemStack(332, 128));
        }
        catch(Exception e){ joueur.sendMessage("Impossible de sauvegarder votre inventaire"); }
	}

	public static void loadInventoryIfNecessary(Player joueur){
		if(new File("plugins/Paintball/inventories/" + joueur.getName() + ".yml").exists()){
			InventoryUtil.lire(joueur);
		}
	}
}
