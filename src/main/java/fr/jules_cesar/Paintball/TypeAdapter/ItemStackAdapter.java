package fr.jules_cesar.Paintball.TypeAdapter;

import java.io.IOException;

import net.minecraft.server.v1_5_R3.ItemStack;

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
      return new ItemStack(id, quantite, (short) dommage);
    }
    public void write(JsonWriter writer, ItemStack value) throws IOException {
      if (value == null) {
        writer.nullValue();
        return;
      }
      writer.beginObject();
      writer.name("id");
      writer.value(value.id);
      writer.name("data");
      writer.value(0); // En attente
      writer.name("dommage");
      writer.value(value.id);
      writer.name("quantite");
      writer.value(value.count);
      writer.endObject();
    }
  }
