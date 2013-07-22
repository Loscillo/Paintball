package fr.jules_cesar.Paintball;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;

import fr.jules_cesar.Paintball.Exception.*;

@NestedCommands("paintball")
public class PaintballCommands implements Commands{

	@Command(name = "init")
	public void initialiser(Player joueur){
		try{
			Paintball.getArene().initialiserPartie();
		}
		catch(ArenaNotSet e){ joueur.sendMessage(ChatColor.RED + "L'arene n'est pas configure."); }
		catch(ArenaAlreadyInGame e){ joueur.sendMessage(ChatColor.RED + "Une partie est deja en cours."); }
	}
}
