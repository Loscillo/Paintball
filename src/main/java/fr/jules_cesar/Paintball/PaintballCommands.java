package fr.jules_cesar.Paintball;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.jules_cesar.Paintball.Exception.ArenaAlreadyInGame;
import fr.jules_cesar.Paintball.Exception.ArenaMissingPlayers;
import fr.jules_cesar.Paintball.Exception.ArenaNotInitialized;
import fr.jules_cesar.Paintball.Exception.ArenaNotSet;
import fr.jules_cesar.Paintball.Exception.PlayerAlreadyInGame;
import fr.jules_cesar.Paintball.Exception.PlayerNotInGame;

@NestedCommands("paintball")
public class PaintballCommands implements Commands{

	@Command(name = "aide")
	public void aide(Player joueur){
		joueur.chat("/help shop");
	}
	
	@Command(name = "init")
	public void initialiser(Player joueur){
		try{
			Paintball.getArene().initialiserPartie(joueur);
		}
		catch(ArenaNotSet e){ joueur.sendMessage(ChatColor.RED + "L'arene n'est pas configure."); }
		catch(ArenaAlreadyInGame e){ joueur.sendMessage(ChatColor.RED + "Une partie est deja en cours."); }
	}
	
	@Command(name = "join", flags = "br")
	public void rejoindre(Player joueur, CommandArgs args){
		try{
			if(args.hasFlag('b')) Paintball.getArene().rejoindrePartie(joueur, "bleu");
			else if(args.hasFlag('r')) Paintball.getArene().rejoindrePartie(joueur, "rouge");
		}
		catch(ArenaNotSet e){ joueur.sendMessage(ChatColor.RED + "L'arene n'est pas configure."); }
		catch(ArenaAlreadyInGame e){ joueur.sendMessage(ChatColor.RED + "Une partie est deja en cours."); }
		catch(PlayerAlreadyInGame e){ joueur.sendMessage(ChatColor.RED + "Vous etes deja dans la partie !"); }
	}
	
	@Command(name = "start")
	public void demarrer(Player joueur){
		try {
			Paintball.getArene().demarrerPartie(joueur);
		}
		catch (ArenaNotInitialized e) {
			joueur.sendMessage("Aucune partie n'est initialise. Utilisez /paintball init pour initialiser l'arene.");
		}
		catch (ArenaAlreadyInGame e) {
			joueur.sendMessage("Une partie est deja en cours, vous ne pouvez pas en demarrer une !");
		}
		catch (ArenaMissingPlayers e) {
			joueur.sendMessage("Il faut au minimum deux joueurs pour demarrer la partie.");
		} catch (PlayerNotInGame e) {
			joueur.sendMessage("Seul les joueurs peuvent demarrer la partie.");
		}
	}
	
	@Command(name = "spectateur")
	public void spectateur(Player joueur){
		try {
			Paintball.getArene().rejoindreSpectateurs(joueur);
		}
		catch(ArenaNotSet e){ joueur.sendMessage(ChatColor.RED + "L'arene n'est pas configure."); }
		catch (ArenaNotInitialized e) { joueur.sendMessage(ChatColor.RED + "Il n'y a aucune partie en cours."); }
		catch (PlayerAlreadyInGame e) { joueur.sendMessage(ChatColor.RED + "Vous ne pouvez pas etre spectateur en etant deja joueur !"); }
	}
	
	@Command(name = "setbleu")
	public void setBleu(Player joueur){
		Paintball.getArene().setBleu(joueur.getLocation());
		joueur.sendMessage("Le point bleu est mis a jour.");
	}
	
	@Command(name = "setrouge")
	public void setRouge(Player joueur){
		Paintball.getArene().setRouge(joueur.getLocation());
		joueur.sendMessage("Le point rouge est mis a jour.");
	}
	
	@Command(name = "setspectateur")
	public void setSpectateur(Player joueur){
		Paintball.getArene().setSpectateur(joueur.getLocation());
		joueur.sendMessage("Le point spectateur est mis a jour.");
	}
	
	@Command(name = "quitter")
	public void quitter(Player joueur){
		try {
			Paintball.getArene().quitter(joueur);
		}
		catch (PlayerNotInGame e) { joueur.sendMessage("Vous n'etes pas dans l'arene."); }
		catch (ArenaAlreadyInGame e) { joueur.sendMessage("Vous ne pouvez pas quitter l'arene en cours de partie."); }
	}
	
	@Command(name = "save")
	public void save(Player joueur){
		Paintball.saveInventory(joueur);
	}
	
	@Command(name = "load")
	public void load(Player joueur){
		Paintball.loadInventoryIfNecessary(joueur);
	}
	
	@Command(name = "list")
	public void list(Player joueur){
		// A implementer
	}
}
