package net.clue.utils;

import net.clue.Main;
import net.clue.ui.StyleProperties;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {

    public static Font loadFont(String fontFileName, float fontSize, String name) {
        try (InputStream inputStream = FontLoader.class.getResourceAsStream("/fonts/" + fontFileName)) {
            if (inputStream != null) {
                Font f = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(fontSize);
                System.out.println("Added font as " + name);
                Main.fonts.put(name, f);
                return f;
            } else {
                System.err.println("Font file not found: " + fontFileName);
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
