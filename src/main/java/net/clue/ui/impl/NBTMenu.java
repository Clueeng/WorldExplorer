package net.clue.ui.impl;

import net.clue.ui.WEFrame;
import net.clue.ui.WeComponents;
import net.clue.utils.ColorUtil;
import net.clue.utils.FileUtil;
import net.clue.utils.NbtEditor;
import net.clue.utils.WorldSelector;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class NBTMenu {

    public NBTMenu(){
        JFrame frame = WEFrame.WorldFrame(300, 400, "Stats Changer");
        FlowLayout l = new FlowLayout();
        l.setHgap(40);
        frame.setLayout(l);

        JLabel label = WeComponents.WeLabel(ColorUtil.text("§1NBT Editor"), "medium");
        JLabel success = WeComponents.WeLabel(ColorUtil.text("§rUnchanged for now"), "small");

        JButton backToMenu = WeComponents.WeButton(ColorUtil.text("§1Go back to menu"), "small");
        backToMenu.addActionListener(e -> {
            new MainChoiceMenu();
            frame.setVisible(false);
        });

        JButton inject = WeComponents.WeButton(ColorUtil.text("§1Select world and inject"), "medium");
        inject.addActionListener(e -> {
            WorldSelector s = new WorldSelector(frame, "Choose a dat File");
            NbtEditor nbt = new NbtEditor(new File(s.worldFolder.getAbsolutePath() + "\\level.dat"));
            nbt.dothing();
            success.setText(ColorUtil.text("§gChanged data!"));
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
