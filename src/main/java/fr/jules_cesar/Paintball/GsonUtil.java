package fr.jules_cesar.Paintball;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import net.minecraft.server.v1_5_R3.PlayerInventory;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.jules_cesar.Paintball.TypeAdapter.*;

public class GsonUtil{

  private Gson gson;
  private Logger log;
  private String chemin;
  
  public GsonUtil(Logger log, String chemin){
    gson = new GsonBuilder()
    .registerTypeAdapter(Location.class, new LocationAdapter())
    .registerTypeAdapter(ItemStack.class, new ItemStackAdapter())
    .registerTypeAdapter(PlayerInventory.class, new PlayerInventoryAdapter())
    .setPrettyPrinting()
    .create();
    this.log = log;
    this.chemin = chemin;
  }
  
  public void ecrire(String nom, Object objet){
    try{
      FileWriter fichier = new FileWriter(chemin + "/" + nom + ".json");
      fichier.write(gson.toJson(objet));
      fichier.close();
    }
    catch (IOException e) { log.warning("Erreur d'ecriture du fichier " + nom + ".json"); }
  }

  public <T> Object lire(String nom, Class<T> classe){
    try {
      BufferedReader br = new BufferedReader(new FileReader(chemin  + "/" + nom + ".json"));
      Object objet = gson.fromJson(br, classe);
      br.close();
      return objet;
    }
    catch (FileNotFoundException e) { log.warning("Erreur de lecture du fichier " + nom + ".json"); return null; } 
    catch (IOException e) { log.warning("Erreur de lecture du fichier " + nom + ".json"); return null; }
  }
  
  public String toJson(Object objet){
	  return gson.toJson(objet);
  }
  
}
