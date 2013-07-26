package fr.jules_cesar.Paintball.ScoreBoard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class TableauScore {
	private static ScoreboardManager manager;
	private static Scoreboard board;
	private static Objective objective;
	private static Team rouge;
	private static Team bleu;
	
	static{
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		rouge = board.registerNewTeam("Rouge");
		bleu = board.registerNewTeam("Bleu");
		objective = board.registerNewObjective("Vies", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("Vies restantes");
	}
	
	public static void ajouterVueSpectateur(Player joueur){
		joueur.setScoreboard(board);
	}
	
	public static void ajouterVueJoueur(Player joueur, String equipe){
		joueur.setScoreboard(board);
		if(equipe.equalsIgnoreCase("bleu")) bleu.addPlayer(joueur);
		else rouge.addPlayer(joueur);
		Score score = objective.getScore(joueur);
		score.setScore(4);
	}
	
	public static void retirerVue(Player joueur){
		joueur.setScoreboard(manager.getNewScoreboard());
	}
	
	public static void enleverVie(Player joueur){
		Score score = objective.getScore(joueur);
		int vie = score.getScore()-1;
		if(vie != 0) score.setScore(score.getScore()-1);
		else{
			rouge.removePlayer(joueur);
			bleu.removePlayer(joueur);
		}
	}
}
