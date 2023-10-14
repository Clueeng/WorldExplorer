package net.clue.utils;

import net.forthecrown.nbt.BinaryTags;
import net.forthecrown.nbt.CompoundTag;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class NbtEditor {


    // In the making, soon:tm:
    public File datFile;

    public NbtEditor(File datFile){
        this.datFile = datFile;
    }

    public void dothing(){
        try {
            FileInputStream fileInputStream = new FileInputStream(datFile);
            CompoundTag tag = BinaryTags.readCompressed(fileInputStream);
            boolean hardcore = tag.getBoolean("hardcore");
            System.out.println("Hardcore: " + hardcore);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
