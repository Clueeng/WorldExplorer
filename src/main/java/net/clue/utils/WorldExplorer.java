package net.clue.utils;

import javax.swing.*;
import java.io.File;

public class WorldExplorer {
    public File datFile;
    public WorldExplorer(JFrame parent, String fileType){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setAcceptAllFileFilterUsed(false);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setCurrentDirectory(new File(System.getenv("APPDATA") + "\\.minecraft\\saves\\"));
        int result = jFileChooser.showOpenDialog(parent);
        if(result != JFileChooser.APPROVE_OPTION || !jFileChooser.getSelectedFile().getName().contains("."+fileType)){
            return;
        }
        datFile = jFileChooser.getSelectedFile();
    }
}
