package fr.jules_cesar.Paintball;

import org.bukkit.Location;

public class Teleportation {

	private Location bleu;
	private Location rouge;
	private Location spectateur;
	
	public String toString(){
		return "Point de teleport bleu : " + bleu + "\nPoint de teleport rouge : " + rouge + "\nPoint de teleport spectateur : " + spectateur;
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
