package net.clue.ui.impl.inv;

import net.clue.ui.WEFrame;
import net.clue.ui.WeComponents;
import net.clue.ui.impl.MainChoiceMenu;
import net.clue.utils.ColorUtil;
import net.clue.utils.NBTUtil;
import net.clue.utils.NbtEditor;
import net.clue.utils.WorldSelector;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InventoryMenu {
    public InventoryMenu(NBTUtil world){
        JFrame frame = WEFrame.WorldFrame(500, 800, "Inventory editor");
        FlowLayout l = new FlowLayout();
        l.setHgap(20);
        frame.setLayout(l);

        JLabel label = WeComponents.WeLabel(ColorUtil.text("§1Inventory Editor"), "medium");
        JLabel success = WeComponents.WeLabel(ColorUtil.text("§rUnchanged for now"), "small");

        JButton backToMenu = WeComponents.WeButton(ColorUtil.text("§1Go back to menu"), "small");
        backToMenu.addActionListener(e -> {

            frame.setVisible(false);
        });
        List<JButton> slots = new ArrayList<>();


        int slot_ = 0;
        for(int wid = 0; wid < 9; wid++){
            for(int hei = 4; hei > 0; hei--){
                JButton slot = WeComponents.box((ColorUtil.text(String.valueOf(slot_))), "small", wid*32, hei*32);
                slot.addActionListener(e ->{
                    String sl = trim(slot.getText());
                    System.out.println("Hi item " + sl);
                    new ItemMenu(sl, world);
                });
                slot_++;
                frame.add(slot, BorderLayout.AFTER_LINE_ENDS);
            }
        }
        frame.add(label, BorderLayout.PAGE_END);

        frame.add(success);
        frame.add(backToMenu);


        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    String trim(String s){
        Pattern pattern = Pattern.compile("<html>(\\d+)</font></html>");

        // Create a matcher to find the pattern in the HTML text
        Matcher matcher = pattern.matcher(s);

        // Check if the pattern is found
        if (matcher.find()) {
            // Extract and return the matched number
            return matcher.group(1);
        }

        // Return an empty string if no match is found
        return "";
    }
}
