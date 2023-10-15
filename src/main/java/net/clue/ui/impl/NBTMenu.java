package net.clue.ui.impl;

import net.clue.ui.WEFrame;
import net.clue.ui.WeComponents;
import net.clue.utils.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class NBTMenu {
    public NbtEditor world;
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

        JButton chooseWorld = WeComponents.WeButton(ColorUtil.text("§1Select world"), "medium");
        chooseWorld.addActionListener(e -> {
            WorldSelector s = new WorldSelector(frame, "Choose a dat File");
            world = new NbtEditor(new File(s.worldFolder.getAbsolutePath() + "\\level.dat"));
            success.setText(ColorUtil.text("§gGot dat file!"));
        });

        JButton allowCheats = WeComponents.WeButton(ColorUtil.text("§pEnable cheats"), "medium");
        allowCheats.addActionListener(e -> {
            success.setText(ColorUtil.text("§gEnabled cheats"));
            NBTUtil wrld = new NBTUtil(world.datFile);
            wrld.allowCheats(true);
            wrld.save();
        });


        JButton debug = WeComponents.WeButton(ColorUtil.text("§pDebug"), "medium");
        debug.addActionListener(e -> {
            NBTUtil wrld = new NBTUtil(world.datFile);
            NBTUtil.Player player = wrld.getPlayer();
            player.setHealth(100);
            player.save();
            wrld.save();
        });


        frame.add(label);
        frame.add(success);
        frame.add(chooseWorld);
        frame.add(allowCheats);
        frame.add(debug);
        frame.add(backToMenu);


        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
