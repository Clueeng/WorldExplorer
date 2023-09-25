package net.clue.ui.impl;

import net.clue.ui.WEFrame;
import net.clue.ui.WeComponents;
import net.clue.utils.ColorUtil;
import net.clue.utils.WorldExplorer;
import net.clue.utils.WorldSelector;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class IconChangerMenu {
    File worldFolder, pngFile;

    public IconChangerMenu(){
        JFrame frame = WEFrame.WorldFrame(300, 400, "Icon Changer");
        frame.setLayout(new FlowLayout());

        JLabel label = WeComponents.WeLabel(ColorUtil.text("§gIcon Changer"), "big");
        JLabel mainLabel = WeComponents.WeLabel(ColorUtil.text("§rNo file loaded"), "small");


        JButton openDat = WeComponents.WeButton(ColorUtil.text("§fChoose .png file"), "medium");
        JButton selectedWorld = WeComponents.WeButton(ColorUtil.text("§rChoose a png file before"), "medium");
        openDat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WorldExplorer d = new WorldExplorer(frame, "png");
                pngFile = d.datFile;
                if(pngFile == null){
                    //mainLabel.setText("<html><font color='red'>Error loading file</font></html>");
                    System.out.println("Error loading png file: " + pngFile.getName());
                }else{
                    if(!pngFile.getName().contains(".png")){
                        System.out.println("Not a png file");
                        //mainLabel.setText("<html><font color='red'>Cannot load other than png" + "</font></html>");
                        return;
                    }
                    try {
                        BufferedImage bimg = ImageIO.read(pngFile);
                        int w, h;
                        w = bimg.getWidth();
                        h = bimg.getHeight();
                        if(w != 64 && h != 64){
                            mainLabel.setText(ColorUtil.text("§rImage isn't 64x64"));
                            //mainLabel.setText("<html><font color='red'>png file isn't 64x64" + "</font></html>");
                            System.out.println("Image must be in 64x64");
                            pngFile = null;
                            return;
                        }
                        mainLabel.setText(ColorUtil.text("§gSuccessfully loaded png file"));
                        //mainLabel.setText("<html><font color='green'>Successfully loaded png file" + "</font></html>");
                        System.out.println("png file loaded");
                    } catch (IOException ex) {
                        mainLabel.setText(ColorUtil.text("§rCould not read file"));
                        System.out.println("Could not read png file (corrupted?)");
                        throw new RuntimeException(ex);
                    }

                    selectedWorld.setText(ColorUtil.text("§gChoose world and apply"));
                    mainLabel.setText(ColorUtil.text("§gLoaded " + pngFile.getName()));
                    //mainLabel.setText("<html><font color='green'>Loaded " + pngFile.getName() + "</font></html>");
                }
            }
        });
        JLabel success = new JLabel("");
        success.setAlignmentY(500);
        success.setBounds(50, 300, 150, 50);

        selectedWorld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pngFile == null) return;
                WorldSelector s = new WorldSelector(frame, "Choose World");
                worldFolder = s.worldFolder;
                // Delete old file
                File oldIcon = new File(worldFolder.getAbsolutePath() + "\\icon.png");
                if(!oldIcon.delete()){
                    mainLabel.setText(ColorUtil.text("§rCouldn't delete " + oldIcon.getName()));
                    //mainLabel.setText("<html><font color='red'>Couldn't delete " + oldIcon.getName() + "</font></html>");
                    //System.out.println("Couldn't delete " + oldIcon.getAbsolutePath());
                }

                // Place new file
                File newIcon = pngFile;
                try {
                    /*System.out.println(newIcon.getAbsolutePath() + " : " + newIcon.getName());
                    System.out.println("Moved new icon");
                    newIcon.renameTo(new File(newIcon.getAbsolutePath().replaceAll(newIcon.getName(), "icon.png")));
                    System.out.println(newIcon.getAbsolutePath() + " : " + newIcon.getName());
                    Files.copy(Path.of(newIcon.toURI()), Path.of(oldIcon.toURI()), StandardCopyOption.REPLACE_EXISTING);
                    //Files.move(Path.of(newIcon.toURI()), Path.of(oldIcon.toURI()), StandardCopyOption.REPLACE_EXISTING);*/
                    // We rename the newIcon file
                    //boolean renamed = newIcon.renameTo(new File(newIcon.getAbsolutePath().replaceAll(newIcon.getName(), "icon.png")));

                    File pngFolder = new File(newIcon.getAbsolutePath().replaceAll(newIcon.getName(), ""));
                    File desiredName = new File(pngFolder + "\\icon.png");
                    Files.copy(newIcon.toPath(), desiredName.toPath());
                    //Files.move(newIcon.toPath(), desiredName.toPath());

                    Files.move(Path.of(desiredName.toURI()), Path.of(oldIcon.toURI()), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println(pngFolder.getAbsolutePath());

                    // We copy the png file
                    Files.copy(Path.of(newIcon.toURI()), Path.of(oldIcon.toURI()), StandardCopyOption.REPLACE_EXISTING);

                    mainLabel.setText(ColorUtil.text("§gNew icon was set"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        JButton backToMenu = WeComponents.WeButton(ColorUtil.text("§1Go back to menu"), "small");
        backToMenu.addActionListener(e -> {
            new MainChoiceMenu();
            frame.setVisible(false);
        });

        frame.add(label);
        frame.add((mainLabel));
        frame.add(openDat);
        frame.add(selectedWorld);

        frame.add(backToMenu);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}