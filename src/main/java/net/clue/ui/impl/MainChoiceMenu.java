package net.clue.ui.impl;

import net.clue.Main;
import net.clue.ui.StyleProperties;
import net.clue.ui.WEFrame;
import net.clue.ui.WeComponents;
import net.clue.utils.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainChoiceMenu {

    public MainChoiceMenu(){
        JFrame frame = WEFrame.WorldFrame(300, 400, "Choosing");
        frame.setLayout(new FlowLayout());

        JLabel label = WeComponents.WeLabel(ColorUtil.text("§1Please select a util"), "big");
        JButton stats = WeComponents.WeButton(ColorUtil.text("§rStats changer"), "medium");
        JButton iconChanger = WeComponents.WeButton(ColorUtil.text("§gIcon changer"), "medium");
        JButton advUnlock = WeComponents.WeButton(ColorUtil.text("§yUnlock Achievements"), "medium");

        stats.addActionListener(e -> {
            new StatsMenu();
            frame.setVisible(false);
        });
        iconChanger.addActionListener(e -> {
            new IconChangerMenu();
            frame.setVisible(false);
        });
        advUnlock.addActionListener(e -> {
            new AdvancementMenu();
            frame.setVisible(false);
        });

        frame.add(label);
        frame.add(stats);
        frame.add(iconChanger);
        frame.add(advUnlock);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
