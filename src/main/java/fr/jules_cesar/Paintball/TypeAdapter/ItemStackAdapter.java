package fr.jules_cesar.Paintball.TypeAdapter;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class ItemStackAdapter extends TypeAdapter<ItemStack> {
    public ItemStack read(JsonReader reader) throws IOException {
      if(reader.peek() == JsonToken.NULL){
        reader.nextNull();
        return null;
      }
      reader.beginObject();
      reader.skipValue();
      int id = reader.nextInt();
      reader.skipValue();
      int data = reader.nextInt();
      reader.skipValue();
      int dommage = reader.nextInt();
      reader.skipValue();
      int quantite = reader.nextInt();
      reader.endObject();
      return new ItemStack(id, quantite, dommage, data);
    }
    public void write(JsonWriter writer, ItemStack value) throws IOException {
      if (value == null) {
        writer.nullValue();
        return;
      }
      writer.beginObject();
      writer.name("id");
      writer.value(value.getTypeId());
      writer.name("data");
      writer.value(0); // En attente
      writer.name("dommage");
      writer.value(value.getDurability());
      writer.name("quantite");
      writer.value(value.getAmount());
      writer.endObject();
    }
  }
