package net.clue.utils;

import com.google.gson.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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

    public void downloadFromURL(String urlStr, File file) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:102.0) Gecko/20100101 Firefox/102.0");
        urlConnection.setRequestProperty("Content-Type", "application/json");

        ReadableByteChannel rbc = Channels.newChannel(urlConnection.getURL().openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

}
