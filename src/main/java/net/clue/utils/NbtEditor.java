package net.clue.utils;

import net.forthecrown.nbt.BinaryTags;
import net.forthecrown.nbt.CompoundTag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class NbtEditor {


    // In the making, soon:tm:
    public File datFile;

    public NbtEditor(File datFile){
        this.datFile = datFile;
    }

    public void changeDifficulty(int difficulty){
        try{
            FileInputStream input = new FileInputStream(datFile);
            CompoundTag tag = BinaryTags.readCompressed(input);
            CompoundTag data_tag = tag.getCompound("Data");
            data_tag.putInt("Difficulty", difficulty);

            input.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getDifficulty(){
        try{
            FileInputStream input = new FileInputStream(datFile);
            CompoundTag tag = BinaryTags.readCompressed(input);
            CompoundTag data_tag = tag.getCompound("Data");
            return data_tag.getInt("Difficulty");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void changeHardcore(boolean hardcore){
        try{
            FileInputStream input = new FileInputStream(datFile);
            CompoundTag tag = BinaryTags.readCompressed(input);
            CompoundTag data_tag = tag.getCompound("Data");
            boolean old_value = data_tag.getBoolean("Hardcore");
            int old_value_int = data_tag.getInt("Hardcore");

            //data_tag.putBoolean("Hardcore", hardcore);
            input.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
