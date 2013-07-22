package fr.jules_cesar.Paintball;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PaintballListener implements Listener{

	private Paintball paintball;
	
	public PaintballListener(Paintball p){
		this.paintball = p;
	}
	
	@EventHandler
	public void toucher(EntityDamageByEntityEvent event){
		this.paintball.getArene().toucher(event);
	}
	
	@EventHandler
	public void deconnexion(PlayerQuitEvent event){
		this.paintball.getArene().deconnexion(event.getPlayer());
	}
}
