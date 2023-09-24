package net.clue.utils;

import com.google.gson.*;
import java.io.*;
import java.util.Objects;
public class FileUtil {

    public void maxStats(File worldFolder, String key, String value) throws IOException {
        System.out.println("Changing " + key + ":" + value);
        File statsFile = statsFile(worldFolder);
        FileReader f = new FileReader(statsFile);
        JsonObject jsonObject = new JsonParser().parse(f).getAsJsonObject();
        f.close();

        // Change the value of the "jump" key to 1000
        JsonObject customStats = jsonObject.getAsJsonObject("stats").getAsJsonObject("minecraft:custom");
        customStats.addProperty("minecraft:"+key, Integer.valueOf(value));

        // Write the updated JSON data back to the file
        FileWriter fileWriter = new FileWriter(statsFile);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(jsonObject, fileWriter);
        fileWriter.close();

        System.out.println("JSON file updated successfully.");

    }

    public File statsFile(File worldFolder){
        File statsFolder = new File(worldFolder + "\\stats\\");
        for(File f : Objects.requireNonNull(statsFolder.listFiles())){
            return f;
        }
        return null;
    }

}
