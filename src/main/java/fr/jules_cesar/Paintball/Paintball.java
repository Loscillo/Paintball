package fr.jules_cesar.Paintball;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Paintball extends JavaPlugin implements Listener{

	private static Arene arene = new Arene();
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(new PaintballListener(this), this);
	}
	
	public static Arene getArene(){
		return arene;
	}
}
