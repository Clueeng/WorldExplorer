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
        inject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WorldSelector s = new WorldSelector(frame, "Choose a World Folder");
                FileUtil f = new FileUtil();

                try {
                    File file;
                    String dl = "https://download1507.mediafire.com/dn0uzl84hbggD7wkJT5ak8dcKKIHH3xUYkAzBpchgTtYbv1Jve2fcCHRjftACZ62HOK9pT1bkg0F-MGXrJXMyS_XfUEfI5cRfea-ij915tV_7GPwObO0g6fSvT8BB2S5bLmiqCSYg3wF_RAX_w7lYs883fnMCkZvM70oPRS9Yil-/396r3d1ubkqy3pq/stats.json";
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
