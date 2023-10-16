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