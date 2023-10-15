package net.clue.utils;

import net.forthecrown.nbt.*;

import java.io.*;

public class NBTUtil {

    public File datFile;
    public FileInputStream inputStream;
    CompoundTag tag;

    public String DATA = "Data";

    public NBTUtil(File datFile) {
        this.datFile = datFile;
        resetInputStream(datFile);
        try {
            tag = BinaryTags.readCompressed(inputStream);
        } catch (IOException e) {
            System.out.println("Oops");
            throw new RuntimeException(e);
        }
    }

    public CompoundTag getTag() {
        return tag;
    }

    public void resetInputStream(File datFile) {
        try {
            this.inputStream = new FileInputStream(datFile);
        } catch(FileNotFoundException fnfe) {
            System.err.println("FileNotFoundException in NBTUtil. Current Time Milis = " + System.currentTimeMillis());
            fnfe.printStackTrace();
        }
    }

    // 0 = not hardcore, 1 = hardcore
    public void setHardcore(int hardcore) {
        getTag().getCompound("Data").putInt("hardcore", hardcore);
    }
    public int getDifficulty(){
        return getTag().getCompound("Data").getInt("Difficulty");
    }
    public void setDifficulty(int difficulty) {
        getTag().getCompound("Data").putInt("Difficulty", difficulty);
    }

    public void setSeed(long seed) {
        getTag().getCompound("Data").putLong("RandomSeed", seed);
    }

    public void allowCheats(boolean allow) {
        getTag().getCompound("Data").putInt("allowCommands", allow ? 1 : 0);
    }
    public void closeStream() {
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void save(){
        try {
            FileOutputStream fOut = new FileOutputStream(datFile);
            BinaryTags.writeCompressed(fOut, getTag());
            fOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        closeStream();
    }

    public void borderSize(double size) {
        String targetName = "BorderSize";
        CompoundTag tag = getTag();
        CompoundTag data_tag = tag.getCompound("Data");
        data_tag.putDouble(targetName, size);
    }
    public Player getPlayer(){
        return new Player();
    }
    public class Player{
        CompoundTag player = getTag().getCompound("Data").getCompound("Player");
        public Player(){
            CompoundTag player = getTag().getCompound("Data").getCompound("Player");
        }
        public void setPosition(long x, long y, long z){
            player.putLongArray("Pos", x, y, z);
        }
        public long[] getPosition(){
            return player.getLongArray("Pos");
        }

        public int getHealth(){
            return player.getInt("Health");
        }
        public int getMaxHealth() {
            CompoundTag attributes = player.getCompound("Attributes");
            for (BinaryTag attributeTag : attributes.getList("Attributes", TagTypes.compoundType())) {
                String name = attributeTag.asCompound().getString("Name");
                if ("generic.maxHealth".equals(name)) {
                    return (int) attributeTag.asCompound().getDouble("Base");
                }
            }
            return 20; // Default max health value
        }

        public void setMaxHealth(double health) {
            CompoundTag attributes = player.getCompound("Attributes");
            for (BinaryTag attributeTag : attributes.getList("Attributes", TagTypes.compoundType())) {
                String name = attributeTag.asCompound().getString("Name");
                if ("generic.maxHealth".equals(name)) {
                    attributeTag.asCompound().putDouble("Base", health);
                }
            }
        }

        public ListTag getAttributes(){
            return player.getCompound("Attributes").getList("Attributes");
        }

        public void setHealth(int h){
            player.putInt("Health", h);
        }

        public void save(){
            try {
                FileOutputStream fOut = new FileOutputStream(datFile);
                BinaryTags.writeCompressed(fOut, getTag());
                fOut.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
