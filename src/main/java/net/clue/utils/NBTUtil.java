package net.clue.utils;

import net.forthecrown.nbt.BinaryTags;
import net.forthecrown.nbt.CompoundTag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NBTUtil {

    public File datFile;
    public FileInputStream inputStream;

    public String DATA = "Data";

    public NBTUtil(File datFile) {
        this.datFile = datFile;
        resetInputStream();
    }

    public CompoundTag getTag() {
        try {
            return BinaryTags.readCompressed(inputStream);
        }catch (Exception e) {
            return null;
        }
    }

    public void resetInputStream() {
        try {
            this.inputStream = new FileInputStream(datFile);
        } catch(FileNotFoundException fnfe) {
            System.err.println("FileNotFoundException in NBTUtil. Current Time Milis = " + System.currentTimeMillis());
            fnfe.printStackTrace();
        }
    }

    // 0 = not hardcore, 1 = hardcore
    public void setHardcore(int hardcore) {
        CompoundTag tag = getTag();
        CompoundTag data_tag = tag.getCompound(DATA);
        data_tag.putInt("hardcore", hardcore);
        tag.replace("Data", data_tag);
    }

    public void setSeed(long seed) {
        CompoundTag tag = getTag();
        CompoundTag data_tag = tag.getCompound("Data");
        data_tag.putLong("RandomSeed", seed);
        tag.replace("Data", data_tag);
    }

    public void allowCheats(boolean allow) {
        String targetName = "allowCommands";
        CompoundTag tag = getTag();
        CompoundTag data_tag = tag.getCompound("Data");
        data_tag.putInt(targetName, allow ? 1 : 0);
        tag.replace("Data", data_tag);
    }

    public void borderSize(double size) {
        String targetName = "BorderSize";
        CompoundTag tag = getTag();
        CompoundTag data_tag = tag.getCompound("Data");
        data_tag.putDouble(targetName, size);
        tag.replace("Data", data_tag);
    }

}
