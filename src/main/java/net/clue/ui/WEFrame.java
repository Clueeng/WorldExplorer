package net.clue.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import net.clue.Main;
import net.clue.utils.FontLoader;

import javax.swing.*;

public class WEFrame {

    public static JFrame WorldFrame(int w, int h, String title){
        JFrame frame = new JFrame(title);
        try{
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        }catch (Exception e){
            e.printStackTrace();
        }
        frame.setResizable(false);
        frame.setFont(Main.fonts.get("small"));

        frame.getContentPane().setBackground(StyleProperties.MAIN_COLOR);
        frame.getContentPane().setForeground(StyleProperties.MAIN_COLOR_DARKER);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(w, h);

        return frame;
    }

}
