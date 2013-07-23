package fr.jules_cesar.Paintball;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;

import fr.jules_cesar.Paintball.Exception.*;

@NestedCommands("paintball")
public class PaintballCommands implements Commands{

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
	}
}
