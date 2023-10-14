package net.clue.ui.impl;

import net.clue.Main;
import net.clue.ui.WEFrame;
import net.clue.ui.WeComponents;
import net.clue.utils.ColorUtil;
import net.clue.utils.FileUtil;
import net.clue.utils.WorldSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class StatsMenu {

    public StatsMenu(){
        JFrame frame = WEFrame.WorldFrame(300, 400, "Stats Changer");
        FlowLayout l = new FlowLayout();
        l.setHgap(40);
        frame.setLayout(l);

        JLabel label = WeComponents.WeLabel(ColorUtil.text("§1Statistics Changer"), "medium");
        JLabel success = WeComponents.WeLabel(ColorUtil.text("§rUnchanged for now"), "small");

        JButton backToMenu = WeComponents.WeButton(ColorUtil.text("§1Go back to menu"), "small");
        backToMenu.addActionListener(e -> {
            new MainChoiceMenu();
            frame.setVisible(false);
        });

        String[] keys = {"jump", "play_time", "sprint_one_cm", "walk_one_cm", "total_world_time", "leave_game", "sneak_time", "damage_taken"};
        JComboBox<String> comboBox = WeComponents.WeComboBox(keys, "small");

        JSpinner value = WeComponents.WeSpinner(0, 0, Integer.MAX_VALUE, 1000, "medium");

        JButton inject = WeComponents.WeButton(ColorUtil.text("§1Select world and inject"), "medium");

        inject.addActionListener(e -> {
            String valuee = String.valueOf(value.getValue());
            WorldSelector s = new WorldSelector(frame, "Choose a World Folder");
            System.out.println(valuee + " ");
            FileUtil f = new FileUtil();

            try {
                f.maxStats(s.worldFolder, Objects.requireNonNull(comboBox.getSelectedItem()).toString(), valuee);
                success.setText(ColorUtil.text("§gChanged data!"));
            } catch (IOException ex) {
                success.setText("§rFailed to change data");
                throw new RuntimeException(ex);
            }
        });

        frame.add(label);
        frame.add(success);
        frame.add(comboBox);
        frame.add(value);
        frame.add(inject);
        frame.add(backToMenu);


        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
