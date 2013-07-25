package fr.jules_cesar.Paintball.TypeAdapter;

import java.io.IOException;

import org.bukkit.Bukkit;

import net.minecraft.server.v1_5_R3.ItemStack;
import net.minecraft.server.v1_5_R3.PlayerInventory;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import fr.jules_cesar.Paintball.GsonUtil;

public class PlayerInventoryAdapter extends TypeAdapter<PlayerInventory>{

	public PlayerInventory read(JsonReader reader) throws IOException {
		if(reader.peek() == JsonToken.NULL){
	        reader.nextNull();
	        return null;
	    }
		return null;
	}

	@Override
	public void write(JsonWriter writer, PlayerInventory value) throws IOException {
		if (value == null) {
	        writer.nullValue();
	        return;
	    }
		GsonUtil gson = new GsonUtil(Bukkit.getPluginManager().getPlugin("Paintball").getLogger(), Bukkit.getPluginManager().getPlugin("Paintball").getDataFolder().getPath());
		ItemStack[] inventaire = value.items;
		writer.beginArray();
		for(int i = 0; i < inventaire.length; i++){
			writer.beginObject();
			writer.value(gson.toJson(inventaire[i]));
			writer.endObject();
		}
		writer.endArray();
	}

}
