package fr.jules_cesar.Paintball.Util;

import java.io.IOException;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.jules_cesar.Paintball.Paintball;

public class InventoryUtil {

	private String nom;
	private ItemStack[] contenu;
	private ItemStack[] armure;
	
	
	public InventoryUtil(Player p){
		this.nom = p.getName();
		this.contenu = p.getInventory().getContents();
		this.armure = p.getInventory().getArmorContents();
	}
	
	public void ecrire() throws IOException{
		YmlUtil fichier = new YmlUtil(Paintball.getFolder(), "/inventories/" + nom);
    	fichier.set("inventory", contenu);
        fichier.set("armor", armure);
    	fichier.save();
	}
	
	public static void lire(Player p){
		YmlUtil fichier = new YmlUtil(Paintball.getFolder(), "/inventories/" + p.getName());
        Object a = fichier.get("inventory");
        Object b = fichier.get("armor");
        fichier.delete();
        if(a == null || b == null) return;
        ItemStack[] inventory = null;
        ItemStack[] armor = null;
        if (a instanceof ItemStack[]){
              inventory = (ItemStack[]) a;
        } else if (a instanceof List){
                List lista = (List) a;
                inventory = (ItemStack[]) lista.toArray(new ItemStack[0]);
        }
        if (b instanceof ItemStack[]){
                armor = (ItemStack[]) b;
          } else if (b instanceof List){
              List listb = (List) b;
              armor = (ItemStack[]) listb.toArray(new ItemStack[0]);
          }
        p.getInventory().clear();
        p.getInventory().setContents(inventory);
        p.getInventory().setArmorContents(armor);
	}
}
