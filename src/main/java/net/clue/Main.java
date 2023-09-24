package net.clue;

import net.clue.ui.StyleProperties;
import net.clue.ui.impl.MainChoiceMenu;
import net.clue.utils.FontLoader;

import java.awt.*;
import java.util.Hashtable;

import static net.clue.utils.FontLoader.loadFont;

public class Main {
    public static Hashtable<String, Font> fonts = new Hashtable<>();
    public static void main(String[] args) {
        System.out.println("Initializing fonts");

        loadFont("Mooli-Regular.ttf", StyleProperties.SMALL_TEXT_SIZE, "small");
        loadFont("Mooli-Regular.ttf", StyleProperties.BIGGER_TEXT_SIZE, "medium");
        loadFont("Mooli-Regular.ttf", StyleProperties.BIG_TEXT_SIZE, "big");

        new MainChoiceMenu();

    }
}