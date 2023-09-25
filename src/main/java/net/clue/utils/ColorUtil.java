package net.clue.utils;

import java.util.Hashtable;
import java.util.Map;

public class ColorUtil {

    public static String text(String text){
        Map<String, String> colors = new Hashtable<>();
        colors.put("§g", "<font color='green'>");
        colors.put("§1", "<font color='gray'>");
        colors.put("§b", "<font color='blue'>");
        colors.put("§y", "<font color='yellow'>");
        colors.put("§r", "<font color='red'>");
        colors.put("§p", "<font color='purple'>");
        colors.put("§d", "<font color='magenta'>");
        colors.put("§f", "<font color='white'>");
        colors.put("§n", "<br/>");
        if(text.contains("§")){
            for(Map.Entry<String, String> entry : colors.entrySet()){
                text = text.replace(entry.getKey(), entry.getValue());
            }
        }

        return "<html>"+text+"</font></html>";
    }

}