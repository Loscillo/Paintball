package fr.jules_cesar.Paintball;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.jules_cesar.Paintball.ScoreBoard.TableauScore;

public class PaintballListener implements Listener{
	
	private Paintball plugin;
	
	public PaintballListener(Paintball p){
		this.plugin = p;
	}
	
	@EventHandler
	public void toucher(EntityDamageByEntityEvent event){
		Paintball.getArene().toucher(event);
	}
	
	@EventHandler
	public void deconnexion(PlayerQuitEvent event){
		Paintball.getArene().deconnexion(event.getPlayer());
	}
}
