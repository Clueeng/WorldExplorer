package net.clue.ui.impl;

import net.clue.ui.WEFrame;
import net.clue.ui.WeComponents;
import net.clue.utils.ColorUtil;
import net.clue.utils.FileUtil;
import net.clue.utils.WorldSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class AdvancementMenu {

    public AdvancementMenu(){
        JFrame frame = WEFrame.WorldFrame(300, 400, "Advancement Unlocker");
        FlowLayout l = new FlowLayout();
        l.setHgap(40);
        frame.setLayout(l);

        JLabel label = WeComponents.WeLabel(ColorUtil.text("§1Advancement Unlocker"), "medium");
        JLabel success = WeComponents.WeLabel(ColorUtil.text("§rUnchanged for now"), "small");

        JButton backToMenu = WeComponents.WeButton(ColorUtil.text("§1Go back to menu"), "small");
        backToMenu.addActionListener(e -> {
            new MainChoiceMenu();
            frame.setVisible(false);
        });

        JButton inject = WeComponents.WeButton(ColorUtil.text("§1Select world and unlock"), "medium");
        inject.addActionListener(e -> {
            WorldSelector s = new WorldSelector(frame, "Choose a World Folder");
            FileUtil f = new FileUtil();

            try {
                File file;
                String dl = "https://cdn.discordapp.com/attachments/1069028713385185381/1156654019834544279/c7f417aa-53b1-4a46-9ad2-29bdf809870e.json?ex=6515c17f&is=65146fff&hm=285853317d0c235b2fb4c124a1dae41eac2ebca352138ec8489ecf992db91a11&";
                for(File fileData : Objects.requireNonNull(new File(s.worldFolder + "\\advancements\\").listFiles())){
                    file = fileData;
                    System.out.println("Deleting old file");
                    fileData.delete();
                    System.out.println("Replaced new file with name " + file.getName() + " (" + file.getAbsolutePath() + ")");
                    f.downloadFromURL(dl, file);
                }

                success.setText(ColorUtil.text("§gChanged data!"));
            } catch (IOException ex) {
                success.setText("§rFailed to change data");
                throw new RuntimeException(ex);
            }
        });

        frame.add(label);
        frame.add(success);
        frame.add(inject);
        frame.add(backToMenu);


        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
