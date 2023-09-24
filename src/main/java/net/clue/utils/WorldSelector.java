package net.clue.utils;

import javax.swing.*;
import java.io.File;
public class WorldSelector {

    public File worldFolder;

    public WorldSelector(JFrame parent, String title){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle(title);

        jFileChooser.setAcceptAllFileFilterUsed(false);
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setCurrentDirectory(new File(System.getenv("APPDATA") + "\\.minecraft\\saves\\"));
        int result = jFileChooser.showOpenDialog(parent);
        if(result != JFileChooser.APPROVE_OPTION){
            return;
        }
        worldFolder = jFileChooser.getSelectedFile();
    }

}
