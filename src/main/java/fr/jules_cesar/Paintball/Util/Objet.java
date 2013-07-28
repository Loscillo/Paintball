package fr.jules_cesar.Paintball.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.google.gson.internal.StringMap;

public class Objet {

	private int id, data, quantite;
	private Map<String, Object> itemMeta;
	
	public Objet(ItemStack objet){
		this.id = objet.getTypeId();
		this.data = objet.getDurability();
		this.quantite = objet.getAmount();
		if(objet.hasItemMeta()) itemMeta = objet.getItemMeta().serialize();
	}
	
	public ItemStack recuperer(){
		ItemStack item = new ItemStack(this.id, this.quantite, (short) this.data);
		item.setDurability((short) this.data);
		if(itemMeta != null){
			item.setItemMeta(deserializeItemMeta(item.getItemMeta(), itemMeta));
			if(itemMeta.containsKey("pages")) item.setItemMeta(deserializeBook((BookMeta) item.getItemMeta(), itemMeta));
			if(itemMeta.containsKey("color")) item.setItemMeta(deserializeLeatherArmor((LeatherArmorMeta) item.getItemMeta(), itemMeta));
			try{ if(itemMeta.containsKey("firework-effects")) item.setItemMeta(deserializeFirework((FireworkMeta) item.getItemMeta(), itemMeta)); } catch(Exception e){}
		}
		return item;
	}
	
	@SuppressWarnings("unchecked")
	private ItemMeta deserializeLeatherArmor(LeatherArmorMeta meta, Map<String, Object> args) {
		Map<String, Double> couleurs = (Map<String, Double>) args.get("color");
		meta.setColor(Color.fromRGB(couleurs.get("red").intValue(), couleurs.get("green").intValue(), couleurs.get("blue").intValue()));
		return meta;
	}
	
	@SuppressWarnings("unchecked")
	private ItemMeta deserializeFirework(FireworkMeta meta, Map<String, Object> args) {
		if(args.containsKey("power")) meta.setPower(((Double) args.get("power")).intValue());
		if(args.containsKey("firework-effects")){
			ArrayList<Map<String, Object>> temp = (ArrayList<Map<String, Object>>) args.get("firework-effects");
			Map<String, Object> effets = temp.get(0);
			Builder firework = FireworkEffect.builder();
			firework.flicker((Boolean) effets.get("flicker"));
			firework.trail((Boolean) effets.get("trail"));
			firework.withColor((ArrayList<Color>) effets.get("colors"));
			firework.with(FireworkEffect.Type.valueOf((String) effets.get("type")));
			meta.addEffect(firework.build());
		}
		return meta;
	}


	@SuppressWarnings("unchecked")
	private ItemMeta deserializeBook(BookMeta meta, Map<String, Object> args) {
		if(args.containsKey("title")) meta.setTitle((String) args.get("title"));
		if(args.containsKey("author")) meta.setAuthor((String) args.get("author"));
		ArrayList<String> pages = (ArrayList<String>) args.get("pages");
		for(String page : pages) meta.addPage(page);
		return meta;
	}

	@SuppressWarnings("unchecked")
	public static ItemMeta deserializeItemMeta(ItemMeta meta, Map<String, Object> args) {
		if(args.containsKey("display-name")) meta.setDisplayName((String) args.get("display-name"));
		if(args.containsKey("enchants")){
			Map<String, Double> enchantements = (Map<String, Double>) args.get("enchants");
			Set<String> liste = enchantements.keySet();
			for(String e : liste)
				meta.addEnchant(Enchantment.getByName(e), enchantements.get(e).intValue(), true);
		}
		return meta;
	}
}
