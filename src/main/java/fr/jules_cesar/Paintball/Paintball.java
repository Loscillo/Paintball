package fr.jules_cesar.Paintball;

import java.io.File;
import java.util.Locale;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.jules_cesar.Paintball.Util.GsonUtil;
import fr.jules_cesar.Paintball.Util.Inventaire;

public class Paintball extends JavaPlugin implements Listener{

	private static Arene arene = new Arene();
	private static Partie partie;
	
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
	
	public static void saveInventory(Player joueur, char equipe){
		new GsonUtil().ecrire("/inventories/" + joueur.getName(), new Inventaire(joueur.getInventory()));
		joueur.getInventory().clear();
		joueur.getInventory().addItem(new ItemStack(332, 128));
	}

	public static void loadInventoryIfNecessary(Player joueur){
		if(new File("plugins/Paintball/inventories/" + joueur.getName() + ".json").exists()){
			Inventaire inventaire = (Inventaire) new GsonUtil().lire("/inventories/" + joueur.getName(), Inventaire.class);
			joueur.getInventory().setContents(inventaire.getItems());
			joueur.getInventory().setArmorContents(inventaire.getArmor());
			new File("plugins/Paintball/inventories/" + joueur.getName() + ".json").delete();
		}
	}
}
