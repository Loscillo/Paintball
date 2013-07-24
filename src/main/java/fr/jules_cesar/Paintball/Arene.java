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
	private Location spectateur;
	
	public void initialiserPartie(Player joueur) throws ArenaNotSet, ArenaAlreadyInGame{
		if(bleu == null || rouge == null || spectateur == null) throw new ArenaNotSet();
		if(Paintball.getPartie() != null){
			if(Paintball.getPartie().obtenirEtat() == 1) throw new ArenaAlreadyInGame();
			else joueur.sendMessage("L'arene est deja initialise. Tu peut rejoindre la partie avec /paintball join r/b");
		}
		Paintball.setPartie(new Partie());
	}
	
	public void rejoindrePartie(Player joueur, String equipe) throws ArenaNotSet, ArenaAlreadyInGame{
		if(Paintball.getPartie() == null) initialiserPartie(joueur);
		else if(Paintball.getPartie().obtenirEtat() == 1) throw new ArenaAlreadyInGame();
		else Paintball.getPartie().ajouterJoueur(joueur, equipe);
	}
	
	public void demarrerPartie() throws ArenaNotInitialized, ArenaAlreadyInGame, ArenaMissingPlayers{
		if(Paintball.getPartie() == null) throw new ArenaNotInitialized();
		if(Paintball.getPartie().obtenirEtat() == 1) throw new ArenaAlreadyInGame();
		if(Paintball.getPartie().nombreJoueurs() < 2) throw new ArenaMissingPlayers();
		Paintball.getPartie().demarrerPartie();
	}
	
	public void toucher(EntityDamageByEntityEvent event){
		if(Paintball.getPartie() != null && Paintball.getPartie().obtenirEtat() == 1 && event.getEntityType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.SNOWBALL)){
			Snowball boule = (Snowball) event.getDamager();
			if(Paintball.getPartie().estJoueur((Player) event.getEntity()) && Paintball.getPartie().estJoueur((Player) boule.getShooter()))
				Paintball.getPartie().toucherJoueur((Player) event.getEntity(), (Player) boule.getShooter());
		}
	}

	public void deconnexion(Player joueur) {
		if(Paintball.getPartie() != null && Paintball.getPartie().estPresent(joueur)){
			Paintball.getPartie().deconnexion(joueur, (Paintball.getPartie().estJoueur(joueur)));
		}
	}
	
	public void teleporterSpectateur(Player joueur){
		joueur.teleport(spectateur);
	}
	
	public void teleporterBleu(Player joueur){
		joueur.teleport(bleu);
	}
	
	public void teleporterRouge(Player joueur){
		joueur.teleport(rouge);
	}

	public void rejoindreSpectateurs(Player joueur) throws ArenaNotSet, ArenaNotInitialized {
		if(bleu == null || rouge == null || spectateur == null) throw new ArenaNotSet();
		if(Paintball.getPartie() == null) throw new ArenaNotInitialized();
		teleporterSpectateur(joueur);
		Paintball.getPartie().ajouterSpectateur(joueur);
	}
	
	public void finPartie(){
		Paintball.setPartie(null);
	}
	
	public void setBleu(Location loc){
		bleu = loc;
	}
	
	public void setRouge(Location loc){
		rouge = loc;
	}
	
	public void setSpectateur(Location loc){
		spectateur = loc;
	}
	
	public Location getBleu(){
		return bleu;
	}
	
	public Location getRouge(){
		return rouge;
	}
	
	public Location getSpectateur(){
		return spectateur;
	}
}
