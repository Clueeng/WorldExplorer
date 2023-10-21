package net.clue.utils;

import net.forthecrown.nbt.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

public class NBTUtil {
    //
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

    public CompoundTag getDataTag(){
        return tag.getCompound("Data");
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
        getDataTag().putInt("hardcore", hardcore);
    }
    public int getDifficulty(){
        return getDataTag().getInt("Difficulty");
    }
    public void setDifficulty(int difficulty) {
        getDataTag().putInt("Difficulty", difficulty);
    }

    public void setSeed(long seed) {
        getDataTag().putLong("RandomSeed", seed);
    }

    public void allowCheats(boolean allow) {
        getDataTag().putInt("allowCommands", allow ? 1 : 0);
    }
    public void closeStream() {
        try {
            System.out.println("Closed I_stream");
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

    public String getLevelName(){
        return getDataTag().getString("LevelName");
    }
    public void setLevelName(String levelName){
        getDataTag().putString("LevelName", levelName);
    }

    public Player getPlayer(){
        return new Player();
    }
    public class Player{
        CompoundTag player = getDataTag().getCompound("Player");
        public double[] getPosition(){
            return new double[] {
                    player.get("Pos").asList().get(0).asNumber().doubleValue(),
                    player.get("Pos").asList().get(1).asNumber().doubleValue(),
                    player.get("Pos").asList().get(2).asNumber().doubleValue()
            };
        }
        public void setPosition(double x, double y, double z) {
            BinaryTag posTag = player.get("Pos");
            ListTag newPos = BinaryTags.listTag();
            newPos.add(BinaryTags.doubleTag(x));
            newPos.add(BinaryTags.doubleTag(y));
            newPos.add(BinaryTags.doubleTag(z));
            player.replace("Pos", posTag, newPos);
        }

        public ListTag getInventory(){
            BinaryTag inventoryTag = player.get("Inventory");
            return inventoryTag.asList();
        }

        public List<Map<String, Object>> getInventoryAsList() {
            List<Map<String, Object>> inventoryList = new ArrayList<>();
            ListTag inventory = getInventory();

            for (int i = 0; i < inventory.size(); i++) {
                CompoundTag itemTag = inventory.get(i).asCompound();
                Map<String, Object> itemData = new HashMap<>();

                for (String key : itemTag.keySet()) {
                    Object value = itemTag.get(key);
                    itemData.put(key, value);
                }

                inventoryList.add(itemData);
            }

            return inventoryList;
        }
//    public ItemBuilder getItem(byte slot) {
//        List<Map<String, Object>> inventoryList = getInventoryAsList();
//
//        for (Map<String, Object> inv : inventoryList) {
//            Object slotObj = inv.get("Slot");
//
//            if (slotObj instanceof ByteTag) {
//                ByteTag slotTag = (ByteTag) slotObj;
//
//                if (slotTag.byteValue() == slot) {
//                    String id = inv.get("id").toString();
//                    byte count = ((ByteTag) inv.get("Count")).byteValue();
//
//                    Map<String, Object> tag = (Map<String, Object>) inv.get("tag");
//                    List<Map<String, Object>> enchantments = null;
//
//                    if (tag != null) {
//                        enchantments = (List<Map<String, Object>>) tag.get("Enchantments");
//                    }
//
//                    ItemBuilder item = new ItemBuilder(id, count, slot);
//
//                    if (enchantments != null) {
//                        for (Map<String, Object> enchantment : enchantments) {
//                            String enchantId = enchantment.get("id").toString();
//                            int level = ((ShortTag)enchantment.get("lvl")).intValue();
//                            HashMap<String, Integer> ench = new HashMap<>();
//                            ench.put(enchantId, level);
//                            item.withEnchantments(ench);
//                        }
//                    }
//
//                    return item;
//                }
//            }
//        }
//
//        return null;
//    }

        public ItemBuilder getItem(byte slot) {
            List<Map<String, Object>> inventoryList = getInventoryAsList();

            for (Map<String, Object> inv : inventoryList) {
                Object slotObj = inv.get("Slot");

                if (slotObj instanceof ByteTag) {
                    ByteTag slotTag = (ByteTag) slotObj;

                    if (slotTag.byteValue() == slot) {
                        String id = inv.get("id").toString();
                        byte count = ((ByteTag) inv.get("Count")).byteValue();

                        Map<String, Object> tag = (Map<String, Object>) inv.get("tag");
                        int damage = 0; // Initialize damage

                        if (tag != null) {
                            Object damageObj = tag.get("Damage");
                            if (damageObj instanceof ByteTag) {
                                damage = ((ByteTag) damageObj).byteValue();
                            }
                        }

                        List<Map<String, Object>> enchantments = null;

                        if (tag != null) {
                            enchantments = (List<Map<String, Object>>) tag.get("Enchantments");
                        }

                        ItemBuilder item = new ItemBuilder(id, count, slot).withDamage(damage);

                        if (enchantments != null) {
                            for (Map<String, Object> enchantment : enchantments) {
                                String enchantId = enchantment.get("id").toString();
                                int level = ((ShortTag) enchantment.get("lvl")).intValue();
                                HashMap<String, Integer> ench = new HashMap<>();
                                ench.put(enchantId, level);
                                item.withEnchantments(ench);
                            }
                        }

                        return item;
                    }
                }
            }

            return null;
        }


        public void clearInventory(){
            getInventory().clear();
        }

        /*public void setItem(byte slot, String id, byte count){
            HashMap<String, BinaryTag> map = new HashMap<>();
            map.put("Slot", BinaryTags.byteTag(slot));
            map.put("id", BinaryTags.stringTag(id));
            map.put("Count", BinaryTags.byteTag(count));

            CompoundTag tag1 = BinaryTags.compoundTag(map);
            getInventory().add(tag1);
        }

        public void setItem(byte slot, String id, byte count, HashMap<String, Object> displayTag, HashMap<String, Integer> enchantments){
            HashMap<String, BinaryTag> map = new HashMap<>();
            map.put("Slot", BinaryTags.byteTag(slot));
            map.put("id", BinaryTags.stringTag(id));
            map.put("Count", BinaryTags.byteTag(count));

            // Add enchantments to the item tag
            if (enchantments != null && !enchantments.isEmpty()) {
                ListTag enchantmentList = BinaryTags.listTag();
                enchantmentList.addAll(getEnchants(enchantments));
                map.put("tag", BinaryTags.compoundTag(Collections.singletonMap("Enchantments", enchantmentList)));
            }
            if(displayTag != null && !displayTag.isEmpty()){
                //
                ListTag displayList = BinaryTags.listTag();
                displayList.addAll(getTags(displayTag));
                map.put("tag", BinaryTags.compoundTag(Collections.singletonMap("display", displayList)));
            }

            CompoundTag tag1 = BinaryTags.compoundTag(map);
            getInventory().add(tag1);
        }
        public List<CompoundTag> getTags(HashMap<String, Object> tags) {
            List<CompoundTag> tagsList = new ArrayList<>();

            for (Map.Entry<String, Object> entry : tags.entrySet()) {
                String tag = entry.getKey();
                Object tagV = entry.getValue();
                System.out.println(tag + " : " + tagV);

                // Create an enchantment CompoundTag for each entry
                CompoundTag enchantment = BinaryTags.compoundTag();

                enchantment.put(tag, BinaryTags.shortTag((short) tagV));
                tagsList.add(enchantment);
            }

            return tagsList;
        }*/

        public List<CompoundTag> getEnchants(HashMap<String, Integer> enchants) {
            List<CompoundTag> enchantments = new ArrayList<>();

            for (Map.Entry<String, Integer> entry : enchants.entrySet()) {
                String enchantName = entry.getKey();
                int enchantLevel = entry.getValue();
                System.out.println(enchantName + " : " + enchantLevel);

                // Create an enchantment CompoundTag for each entry
                CompoundTag enchantment = BinaryTags.compoundTag(
                        Collections.singletonMap("id", BinaryTags.stringTag("minecraft:" + enchantName))
                );

                enchantment.put("lvl", BinaryTags.shortTag((short) enchantLevel));
                enchantments.add(enchantment);
            }

            return enchantments;
        }
        /*public void setItem(byte slot, String id, byte count, HashMap<String, Integer> enchantments) {
            HashMap<String, BinaryTag> map = new HashMap<>();
            map.put("Slot", BinaryTags.byteTag(slot));
            map.put("id", BinaryTags.stringTag(id));
            map.put("Count", BinaryTags.byteTag(count));

            // Add enchantments to the item tag
            if (enchantments != null && !enchantments.isEmpty()) {
                ListTag enchantmentList = BinaryTags.listTag();
                enchantmentList.addAll(getEnchants(enchantments));
                map.put("tag", BinaryTags.compoundTag(Collections.singletonMap("Enchantments", enchantmentList)));
            }

            CompoundTag tag1 = BinaryTags.compoundTag(map);
            getInventory().add(tag1);
        }*/

        public void removeItem(byte slot) {
            List<Map<String, Object>> inventoryList = getInventoryAsList();

            for (int i = 0; i < inventoryList.size(); i++) {
                Map<String, Object> inv = inventoryList.get(i);

                if (inv.containsKey("Slot") && inv.get("Slot") instanceof ByteTag) {
                    byte itemSlot = ((ByteTag) inv.get("Slot")).byteValue();

                    if (itemSlot == slot) {
                        inventoryList.remove(i); // Remove the item with the matching slot
                        return;
                    }
                }
            }
        }

        public void addItem(ItemBuilder b){
            getInventory().add(b.createTag());
        }

        public int getGamemode(){
            return player.getInt("playerGameType");
        }
        public void setGamemode(int gm){
            player.putInt("playerGameType", gm);
        }

        public Object debugPos(){
            return player.get("Pos");
        }

        public int getAbsorption(){
            return player.getInt("AbsorptionAmount");
        }
        public void setAbsorption(int ab){
            player.putInt("AbsorptionAmount", ab);
        }

        public boolean isInvulnerable(){
            return player.getInt("Invulnerable") == 1;
        }
        public void setInvulnerable(boolean inv){
            player.putInt("Invulnerable", inv ? 1 : 0);
        }

        public int getScore(){
            return player.getInt("Score");
        }
        public void setScore(int sc){
            player.putInt("Score", sc);
        }
        public int getXpLevel(){
            return player.getInt("XpLevel");
        }
        public void setXpLevel(int sc){
            player.putInt("XpLevel", sc);
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

    }
}
