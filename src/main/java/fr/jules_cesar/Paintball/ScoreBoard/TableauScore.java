package fr.jules_cesar.Paintball.ScoreBoard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class TableauScore {
	public static ScoreboardManager manager;
	public static Scoreboard board;
	public static Score score;
	public static Objective objective;
	
	static{
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		objective = board.registerNewObjective("lives", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("Classement");
	}
	
	public static void ajouterVue(Player joueur){
		joueur.setScoreboard(board);
		System.out.println("ajout");
	}
	
	
}
