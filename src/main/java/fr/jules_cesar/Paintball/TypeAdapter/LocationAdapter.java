package fr.jules_cesar.Paintball.TypeAdapter;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class LocationAdapter extends TypeAdapter<Location> {
    public Location read(JsonReader reader) throws IOException {
      if(reader.peek() == JsonToken.NULL){
        reader.nextNull();
        return null;
      }
      reader.beginObject();
      reader.skipValue();
      World monde = Bukkit.getWorld(reader.nextString());
      reader.skipValue();
      int x = reader.nextInt();
      reader.skipValue();
      int y = reader.nextInt();
      reader.skipValue();
      int z = reader.nextInt();
      reader.endObject();
      return new Location(monde, x, y, z);
    }
    public void write(JsonWriter writer, Location value) throws IOException {
      if (value == null) {
        writer.nullValue();
        return;
      }
      String object = value.getWorld().getName() + "," + value.getBlockX() + "," + value.getBlockY() + "," + value.getBlockZ();
      writer.beginObject();
      writer.name("world");
      writer.value(value.getWorld().getName());
      writer.name("x");
      writer.value(value.getBlockX());
      writer.name("y");
      writer.value(value.getBlockY());
      writer.name("z");
      writer.value(value.getBlockZ());
      writer.endObject();
    }
  }
