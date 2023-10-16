package net.clue.ui.impl.inv;

import net.clue.ui.WEFrame;
import net.clue.ui.WeComponents;
import net.clue.utils.ColorUtil;
import net.clue.utils.ItemBuilder;
import net.clue.utils.NBTUtil;
import net.clue.utils.NbtEditor;
import net.forthecrown.nbt.CompoundTag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemMenu {
    public ItemMenu(String slot, NBTUtil world){
        JFrame frame = WEFrame.WorldFrame(500, 220, "Item editor");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                frame.setVisible(false);
            }
        });

        FlowLayout l = new FlowLayout();
        l.setHgap(20);
        frame.setLayout(l);

        JLabel label = WeComponents.WeLabel(ColorUtil.text("§1Inventory Editor"), "medium");
        JLabel success = WeComponents.WeLabel(ColorUtil.text("§rUnchanged for now"), "small");

        JTextField itemName = WeComponents.WeTextField("item", "small");
        itemName.setPreferredSize(new Dimension(250, 30));
        JSpinner count = WeComponents.WeSpinner(1, 1, 64, 1, "medium");
        JTextField enchants = WeComponents.WeTextField("enchantment", "small");
        enchants.setPreferredSize(new Dimension(250, 30));
        JSpinner lvl = WeComponents.WeSpinner(5, 1, 255, 1, "medium");


        HashMap<String, Integer> ench = new HashMap<>();
        JButton applyEnch = WeComponents.WeButton(ColorUtil.text("§1Apply enchantment " + slot), "small");
        applyEnch.addActionListener(e -> {
            System.out.println("set enchantments " + enchants.getText() + ":" + (Integer) lvl.getValue());
            ench.put(enchants.getText(), (Integer) lvl.getValue());
        });

        JLabel count_text = WeComponents.WeLabel(ColorUtil.text("§fCount"), "medium");
        JLabel level = WeComponents.WeLabel(ColorUtil.text("§fLevel"), "medium");


        JTextField customName = WeComponents.WeTextField("custom name", "small");

        JCheckBox set_name = WeComponents.checkBox();
        set_name.addActionListener(e ->{
            customName.show(!customName.isShowing());
        });

        JButton setItem = WeComponents.WeButton(ColorUtil.text("§1Set item " + slot), "small");
        setItem.addActionListener(e -> {
            NBTUtil.Player player = world.getPlayer();
            System.out.println(player.getInventory());
            Integer v = (Integer) count.getValue();
            System.out.println("Slot " + Byte.parseByte(slot) + " name: " + itemName.getText() + " count: " + v.byteValue() + " enchs: " + player.getEnchants(ench));

            if(ench.isEmpty()){
                ItemBuilder item = new ItemBuilder("minecraft:"+itemName.getText(),  ((Integer) count.getValue()).byteValue(), Byte.parseByte(slot))
                        .withCustomName(customName.getText());
                player.addItem(item);
                //player.setItem(Byte.parseByte(slot), "minecraft:" + itemName.getText(), ((Integer) count.getValue()).byteValue());
            }else{

                ItemBuilder item = new ItemBuilder("minecraft:"+itemName.getText(),  ((Integer) count.getValue()).byteValue(), Byte.parseByte(slot))
                        .withEnchantments(ench);
                player.addItem(item);
                //player.setItem(Byte.parseByte(slot), "minecraft:" + itemName.getText(), ((Integer) count.getValue()).byteValue(), ench);
            }
            player.save();
            world.save();
            frame.setVisible(false);
        });

        JButton cancel = WeComponents.WeButton(ColorUtil.text("§1Cancel editing item " + slot), "small");
        cancel.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
        });

        frame.add(label, BorderLayout.PAGE_END);
        frame.add(success);
        frame.add(itemName);
        frame.add(count);
        frame.add(count_text);
        frame.add(enchants);
        frame.add(lvl);
        frame.add(level);
        frame.add(applyEnch);
        frame.add(set_name);
        frame.add(customName);
        frame.add(setItem);


        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
