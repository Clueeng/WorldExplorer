package net.clue.ui.impl;

import net.clue.ui.WEFrame;
import net.clue.ui.WeComponents;
import net.clue.ui.impl.inv.InventoryMenu;
import net.clue.utils.*;
import net.forthecrown.nbt.BinaryTags;
import net.forthecrown.nbt.CompoundTag;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class NBTMenu {
    public NBTUtil world;
    public NBTMenu(){
        JFrame frame = WEFrame.WorldFrame(300, 400, "Stats Changer");
        FlowLayout l = new FlowLayout();
        l.setHgap(40);
        frame.setLayout(l);

        JLabel label = WeComponents.WeLabel(ColorUtil.text("§1NBT Editor"), "medium");
        JLabel success = WeComponents.WeLabel(ColorUtil.text("§rUnchanged for now"), "small");

        JButton backToMenu = WeComponents.WeButton(ColorUtil.text("§1Go back to menu"), "small");
        backToMenu.addActionListener(e -> {
            new MainChoiceMenu();
            frame.setVisible(false);
        });

        JButton chooseWorld = WeComponents.WeButton(ColorUtil.text("§1Select world"), "medium");
        chooseWorld.addActionListener(e -> {
            WorldSelector s = new WorldSelector(frame, "Choose a dat File");
            world = new NBTUtil(new File(s.worldFolder.getAbsolutePath() + "\\level.dat"));
            success.setText(ColorUtil.text("§gGot dat file!"));
        });

        JButton allowCheats = WeComponents.WeButton(ColorUtil.text("§pEnable cheats"), "medium");
        allowCheats.addActionListener(e -> {
            success.setText(ColorUtil.text("§gEnabled cheats"));
            NBTUtil wrld = new NBTUtil(world.datFile);
            wrld.allowCheats(true);
            wrld.save();
        });


        JButton debug = WeComponents.WeButton(ColorUtil.text("§pDebug"), "medium");
        debug.addActionListener(e -> {
            NBTUtil wrld = new NBTUtil(world.datFile);
            NBTUtil.Player player = wrld.getPlayer();
            //System.out.println(player.getInventory());
            HashMap<String, Integer> enchants = new HashMap<>();
            enchants.put("sharpness", 10);
            enchants.put("luck_of_the_sea", 100);
            ItemBuilder item_0 = new ItemBuilder("minecraft:stick", (byte) 32, (byte) 0);
            item_0.withEnchantments(enchants)
                    .withDamage(20);
            player.addItem(item_0);

            ItemBuilder newitem = player.getItem((byte)0);
            System.out.println(newitem.getID());
            System.out.println(newitem.getEnchantments());
            System.out.println(newitem.getDamage());

            player.addItem(newitem.changeSlot((byte) 1));
            player.save();
            wrld.save();
        });

        JButton editInv = WeComponents.WeButton(ColorUtil.text("§rEdit Inventory"), "medium");
        editInv.addActionListener(e ->{
            if(world.datFile == null){
                return;
            }
            new InventoryMenu(world);
        });

        JButton save = WeComponents.WeButton(ColorUtil.text("§gSave Changes"), "big");
        save.addActionListener(e -> {
            NBTUtil wrld = new NBTUtil(world.datFile);
            NBTUtil.Player player = wrld.getPlayer();
            player.save();
            wrld.save();
            wrld.closeStream();
        });


        frame.add(label);
        frame.add(success);
        frame.add(chooseWorld);
        frame.add(allowCheats);
        frame.add(editInv);
        frame.add(debug);
        frame.add(save);
        frame.add(backToMenu);


        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
