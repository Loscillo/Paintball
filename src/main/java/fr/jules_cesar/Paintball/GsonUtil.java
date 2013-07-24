public class GsonUtil{

  private Gson gson;
  
  public Gson(){
    gson = new GsonBuilder()
    .registerTypeAdapter(Location.class, new LocationAdapter())
    .setPrettyPrinting()
    .create();
  }
  
  public void ecrire(String fichier, String contenu){
    try{
      FileWriter fichier = new FileWriter(getDataFolder().getPath() + "/" + fichier + ".json");
      fichier.write(contenu);
      fichier.close();
    }
    catch (IOException e) { getLogger().warning("Erreur d'ecriture du fichier " + fichier + ".json"); }
  }

  public Object lire(String fichier, Class classe){
    try {
      BufferedReader br = new BufferedReader(new FileReader(getDataFolder().getPath() + "/" + fichier + ".json"));
      Object objet = gson.fromJson(br, classe);
      br.close();
    }
    catch (FileNotFoundException e) { getLogger().warning("Le fichier de configuration n'est pas disponible. Le plugin ne sera pas actif."); } 
    catch (IOException e) { getLogger().warning("Erreur de lecture du fichier de configuration. Le plugin ne sera pas actif."); }
  }
  
}
