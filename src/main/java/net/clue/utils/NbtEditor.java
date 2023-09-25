package net.clue.utils;

import dev.dewy.nbt.Nbt;
import dev.dewy.nbt.api.registry.TagTypeRegistry;
import dev.dewy.nbt.tags.collection.CompoundTag;

import java.io.File;
import java.io.IOException;

public class NbtEditor {


    // In the making, soon:tm:
    public File datFile;

    public NbtEditor(File datFile){
        this.datFile = datFile;
    }

    public void dothing(){
        Nbt nbt = new Nbt();

        try {
            CompoundTag t = nbt.fromFile(datFile);
            t.toJson(1, new TagTypeRegistry());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
