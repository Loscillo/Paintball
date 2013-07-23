package fr.jules_cesar.Paintball;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.jules_cesar.Paintball.Exception.*;

public class Arene {

	private Location bleu;
	private Location rouge;
	private Location spectateurs;
	private Partie partie;
	
	public void initialiserPartie(Player joueur) throws ArenaNotSet, ArenaAlreadyInGame{
		if(bleu == null || rouge == null || spectateurs == null) throw new ArenaNotSet();
		if(partie != null){
			if(partie.obtenirEtat() == 1) throw new ArenaAlreadyInGame();
			else joueur.sendMessage("L'arène est déjà initialisé. Tu peut rejoindre la partie avec /paintball join r/b");
		}
		this.partie = new Partie();
	}
	
	public void rejoindrePartie(Player joueur, String equipe) throws ArenaNotSet, ArenaAlreadyInGame{
		if(partie == null) initialiserPartie(joueur);
		else if(partie.obtenirEtat() == 1) throw new ArenaAlreadyInGame();
		else partie.ajouterJoueur(joueur, equipe);
	}
	
	public void demarrerPartie() throws ArenaNotInitialized, ArenaAlreadyInGame, ArenaMissingPlayers{
		if(partie == null) throw new ArenaNotInitialized();
		if(partie.obtenirEtat() == 1) throw new ArenaAlreadyInGame();
		if(partie.nombreJoueurs() < 2) throw new ArenaMissingPlayers();
		partie.demarrerPartie();
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
	
	public void teleporterBleu(Player joueur){
		joueur.teleport(bleu);
	}
	
	public void teleporterRouge(Player joueur){
		joueur.teleport(rouge);
	}
}
