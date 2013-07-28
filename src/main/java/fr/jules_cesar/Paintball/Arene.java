package fr.jules_cesar.Paintball;

import org.bukkit.Bukkit;
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
		else if(Paintball.getPartie() != null){
			if(Paintball.getPartie().obtenirEtat() == 1) throw new ArenaAlreadyInGame();
			else joueur.sendMessage(Paintball.messages.get("init.already"));
		}
		else{
			Paintball.setPartie(new Partie());
			Bukkit.getServer().broadcastMessage(Paintball.messages.get("init") + "\n" + Paintball.messages.get("init.team1") + "\n" + Paintball.messages.get("init.team2"));
		}
	}
	
	public void rejoindrePartie(Player joueur, String equipe) throws ArenaNotSet, ArenaAlreadyInGame, PlayerAlreadyInGame{
		if(Paintball.getPartie() == null) initialiserPartie(joueur);
		if(Paintball.getPartie().obtenirEtat() == 1) throw new ArenaAlreadyInGame();
		if(Paintball.getPartie().estJoueur(joueur)) throw new PlayerAlreadyInGame();
		joueur.teleport(spectateur);
		Paintball.getPartie().ajouterJoueur(joueur, equipe);
		if(equipe.equalsIgnoreCase("rouge")) Paintball.getPartie().annoncer(Paintball.messages.get("game.join.red", joueur.getName()));
		else Paintball.getPartie().annoncer(Paintball.messages.get("game.join.blue", joueur.getName()));
	}
	
	public void demarrerPartie(Player joueur) throws ArenaNotInitialized, ArenaAlreadyInGame, ArenaMissingPlayers, PlayerNotInGame{
		if(Paintball.getPartie() == null) throw new ArenaNotInitialized();
		if(Paintball.getPartie().obtenirEtat() == 1) throw new ArenaAlreadyInGame();
		if(!Paintball.getPartie().estJoueur(joueur)) throw new PlayerNotInGame();
		if(Paintball.getPartie().nombreJoueurs() < 2) throw new ArenaMissingPlayers();
		Paintball.getPartie().demarrerPartie();
	}
	
	public void toucher(EntityDamageByEntityEvent event){
		if(Paintball.getPartie() != null && Paintball.getPartie().obtenirEtat() == 1 && event.getEntityType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.SNOWBALL)){
			Snowball boule = (Snowball) event.getDamager();
			if(Paintball.getPartie().estJoueur((Player) event.getEntity()) && Paintball.getPartie().estJoueur((Player) boule.getShooter())){
				Paintball.getPartie().toucherJoueur((Player) event.getEntity(), (Player) boule.getShooter());
				event.setCancelled(true);
				if(Paintball.getPartie().nombreJoueursBleu() == 0 || Paintball.getPartie().nombreJoueursRouge() == 0) finPartie();
			}
		}
	}

	public void deconnexion(Player joueur) {
		if(Paintball.getPartie() != null && Paintball.getPartie().estPresent(joueur)){
			Paintball.getPartie().deconnexion(joueur, (Paintball.getPartie().estJoueur(joueur)));
			if(Paintball.getPartie().nombreJoueursBleu() == 0 || Paintball.getPartie().nombreJoueursRouge() == 0) finPartie();
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

	public void rejoindreSpectateurs(Player joueur) throws ArenaNotSet, ArenaNotInitialized, PlayerAlreadyInGame {
		if(bleu == null || rouge == null || spectateur == null) throw new ArenaNotSet();
		if(Paintball.getPartie() == null) throw new ArenaNotInitialized();
		if(Paintball.getPartie().estJoueur(joueur)) throw new PlayerAlreadyInGame();
		joueur.teleport(spectateur);
		Paintball.getPartie().ajouterSpectateur(joueur);
	}
	
	public void finPartie(){
		Paintball.getPartie().finPartie();
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

	public void quitter(Player joueur) throws PlayerNotInGame, ArenaAlreadyInGame {
		if(Paintball.getPartie() != null){
			Partie p = Paintball.getPartie();
			if(!p.estPresent(joueur)) throw new PlayerNotInGame();
			else if(p.estJoueur(joueur)){
				if(p.obtenirEtat() != 0) throw new ArenaAlreadyInGame();
				p.retirerJoueur(joueur, true);
			}
			else p.retirerSpectateur(joueur);
		}
	}
}
