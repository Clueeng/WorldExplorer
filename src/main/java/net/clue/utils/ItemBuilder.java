package net.clue.utils;
import net.forthecrown.nbt.BinaryTag;
import net.forthecrown.nbt.BinaryTags;
import net.forthecrown.nbt.CompoundTag;
import net.forthecrown.nbt.ListTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
    private CompoundTag itemTag;

    public ItemBuilder(String id, byte count, byte slot) {
        // Create the initial item tag
        itemTag = BinaryTags.compoundTag();
        itemTag.put("id", BinaryTags.stringTag(id));
        itemTag.put("Count", BinaryTags.byteTag(count));
        itemTag.put("Slot", BinaryTags.byteTag(slot));
    }

    public ItemBuilder changeSlot(byte slot){
        itemTag.put("Slot", BinaryTags.byteTag(slot));
        return this;
    }

    public ItemBuilder withCustomName(String customName) {
        // Create or update the display tag for the custom name
        if(itemTag.getCompound("tag").isEmpty() || itemTag.getCompound("tag") == null){
            itemTag.put("tag", BinaryTags.compoundTag());
        }

        CompoundTag tag = itemTag.getCompound("tag");

        CompoundTag displayTag = itemTag.getCompound("tag").getCompound("display");
        if(tag.getCompound("display").isEmpty()){

        }
        displayTag.put("Name", BinaryTags.stringTag(customName));
        return this;
    }

    public ItemBuilder withJsonCustomName(String jsonCustomName) {
        // Create or update the display tag for the JSON-formatted custom name
        if (!itemTag.contains("tag")) {
            itemTag.put("tag", BinaryTags.compoundTag());
        }
        CompoundTag tag = itemTag.getCompound("tag");
        if (!tag.contains("display")) {
            tag.put("display", BinaryTags.compoundTag());
        }
        CompoundTag display = tag.getCompound("display");
        display.put("Name", BinaryTags.stringTag(jsonCustomName));
        return this;
    }

    public ItemBuilder withDamage(int damage) {
        // Create or update the damage tag
        if (!itemTag.contains("tag")) {
            itemTag.put("tag", BinaryTags.compoundTag());
        }
        itemTag.getCompound("tag").put("Damage", BinaryTags.intTag(damage));
        return this;
    }



    public int getDamage(){
        if (!itemTag.contains("tag")) {
            itemTag.put("tag", BinaryTags.compoundTag());
        }
        return itemTag.getCompound("tag").get("Damage").asNumber().intValue();
    }
    public int getCount(){
        if (!itemTag.contains("tag")) {
            itemTag.put("tag", BinaryTags.compoundTag());
        }
        return itemTag.getCompound("tag").get("Count").asNumber().intValue();
    }

    public String getID(){
        if (!itemTag.contains("tag")) {
            itemTag.put("tag", BinaryTags.compoundTag());
        }
        return itemTag.get("id").asString().toNbtString();
    }
    public String cleanName(){
        String[] parts = getID().split(":");
        if (parts.length == 2) {
            return parts[1].replaceAll("'", "");
        } else {
            return getID(); // Return the original string if no colon is found
        }
    }
    public static String cleanName(String id){
        String[] parts = id.split(":");
        if (parts.length == 2) {
            return parts[1].replaceAll("'", "");
        } else {
            return id; // Return the original string if no colon is found
        }
    }
    public String clean_mc(String mc){
        String[] parts = mc.split(":");
        if (parts.length == 2) {
            return parts[1];
        } else {
            return mc; // Return the original string if no colon is found
        }
    }

    public ItemBuilder withEnchantments(HashMap<String, Integer> enchantments) {
        // Create or update the enchantments tag
        if (!itemTag.contains("tag")) {
            itemTag.put("tag", BinaryTags.compoundTag());
        }

        CompoundTag tag = itemTag.getCompound("tag");
        if (!tag.contains("Enchantments")) {
            ListTag enchantmentList = BinaryTags.listTag(); // Create a new list tag

            for (Map.Entry<String, Integer> entry : enchantments.entrySet()) {
                CompoundTag enchantMap = BinaryTags.compoundTag();
                enchantMap.put("id", BinaryTags.stringTag(entry.getKey()));
                enchantMap.put("lvl", BinaryTags.shortTag(entry.getValue().shortValue()));
                enchantmentList.add(enchantMap); // Add each enchantment to the list
            }

            tag.put("Enchantments", enchantmentList);
        }

        return this;
    }
    public ItemBuilder removeEnchantment(String enchantName) {
        if (itemTag.contains("tag")) {
            CompoundTag tag = itemTag.getCompound("tag");

            if (tag.contains("Enchantments")) {
                ListTag enchantmentList = tag.getList("Enchantments");

                // Find and remove the enchantment with the specified name
                for (BinaryTag enchantTag : enchantmentList) {
                    if (enchantTag instanceof CompoundTag) {
                        CompoundTag enchantment = (CompoundTag) enchantTag;
                        String id = enchantment.getString("id");

                        if (id.equals(enchantName)) {
                            enchantmentList.remove(enchantTag);
                            break; // Exit the loop after removing the enchantment
                        }
                    }
                }

                // If the list is empty, remove the "Enchantments" tag
                if (enchantmentList.isEmpty()) {
                    tag.remove("Enchantments");
                }
            }
        }

        return this;
    }

    public int getEnchantmentLevel(String enchantmentName) {
        List<HashMap<String, Integer>> enchantmentsList = getAllEnchantments();

        for (HashMap<String, Integer> enchantment : enchantmentsList) {
            System.out.println(enchantment +"hii");
            if (enchantment.containsKey(enchantmentName)) {
                return enchantment.get(enchantmentName);
            }
        }

        return 0; // Return 0 if the enchantment is not found
    }

    public List<HashMap<String, Integer>> getAllEnchantments() {
        List<HashMap<String, Integer>> enchantmentsList = new ArrayList<>();

        if (itemTag.contains("tag")) {
            CompoundTag tag = itemTag.getCompound("tag");

            if (tag.contains("Enchantments")) {
                ListTag enchantmentList = tag.getList("Enchantments");

                for (BinaryTag enchantTag : enchantmentList) {
                    if (enchantTag instanceof CompoundTag) {
                        CompoundTag enchantment = (CompoundTag) enchantTag;
                        String enchantId = enchantment.getString("id");
                        int level = enchantment.getShort("lvl");

                        HashMap<String, Integer> enchantmentMap = new HashMap<>();
                        enchantmentMap.put(enchantId, level);

                        enchantmentsList.add(enchantmentMap);
                    }
                }
            }
        }

        return enchantmentsList;
    }

    public HashMap<String, Integer> getEnchantments() {
        HashMap<String, Integer> enchantments = new HashMap<>();

        if (itemTag.contains("tag")) {
            CompoundTag tag = itemTag.getCompound("tag");

            if (tag.contains("Enchantments")) {
                ListTag enchantmentList = tag.getList("Enchantments");

                for (BinaryTag enchantTag : enchantmentList) {
                    if (enchantTag instanceof CompoundTag) {
                        CompoundTag enchantment = (CompoundTag) enchantTag;
                        String enchantId = enchantment.getString("id");
                        int level = enchantment.getShort("lvl");
                        enchantments.put(enchantId, level);
                    }
                }
            }
        }

        return enchantments;
    }

    private List<CompoundTag> getEnchants(HashMap<String, Integer> enchants) {
        List<CompoundTag> enchantments = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : enchants.entrySet()) {
            CompoundTag enchantMap = BinaryTags.compoundTag();
            enchantMap.put("id", BinaryTags.stringTag(entry.getKey()));
            enchantMap.put("lvl", BinaryTags.shortTag(entry.getValue().shortValue()));
            enchantments.add(enchantMap);
        }
        return enchantments;
    }

    public CompoundTag createTag() {
        return itemTag;
    }
}