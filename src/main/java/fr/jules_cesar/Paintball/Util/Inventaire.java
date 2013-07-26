package fr.jules_cesar.Paintball.Util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Inventaire {

	private Objet[] contenu;
	private Objet[] armure;
	
	public Inventaire(PlayerInventory inventaire){
		this.contenu = new Objet[36];
		this.armure = new Objet[4];
		ItemStack[] items = inventaire.getContents();
		for(int i = 0; i < items.length; i++){
			if(items[i] != null) this.contenu[i] = new Objet(items[i]);
			else contenu[i] = null;
		}
		items = inventaire.getArmorContents();
		for(int i = 0; i < items.length; i++){
			if(items[i] != null) this.armure[i] = new Objet(items[i]);
			else armure[i] = null;
		}
	}
	
	public ItemStack[] getItems(){
		ItemStack[] items = new ItemStack[36];
		for(int i = 0; i < contenu.length; i++){
			if(contenu[i] != null) items[i] = contenu[i].recuperer();
			else items[i] = new ItemStack(0);
		}
		return items;
	}
	
	public ItemStack[] getArmor(){
		ItemStack[] items = new ItemStack[4];
		for(int i = 0; i < armure.length; i++){
			if(armure[i] != null) items[i] = armure[i].recuperer();
			else items[i] = new ItemStack(0);
		}
		return items;
	}
}
