package fr.jules_cesar.Paintball;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.jules_cesar.Paintball.Exception.ArenaAlreadyInGame;
import fr.jules_cesar.Paintball.Exception.ArenaNotSet;

public class Arene {

	private Location bleu;
	private Location rouge;
	private Location spectateurs;
	private Partie partie;
	
	public void initialiserPartie() throws ArenaNotSet, ArenaAlreadyInGame{
		if(bleu == null || rouge == null || spectateurs == null) throw new ArenaNotSet();
		if(partie != null) throw new ArenaAlreadyInGame();
	}
	
	public void toucher(EntityDamageByEntityEvent event){
		if(this.partie != null && this.partie.obtenirEtat() == 1 && event.getEntityType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.SNOWBALL)){
			Snowball boule = (Snowball) event.getDamager();
			if(this.partie.estJoueur((Player) event.getEntity()) && this.partie.estJoueur((Player) boule.getShooter()))
				partie.toucherJoueur((Player) event.getEntity(), (Player) boule.getShooter());
		}
	}

	public void deconnexion(Player joueur) {
		if(this.partie != null && this.partie.estPresent(joueur)){
				partie.deconnexion(joueur, (this.partie.estJoueur(joueur)));
		}
	}
	
	public void teleporterSpectateur(Player joueur){
		joueur.teleport(spectateurs);
	}
}
