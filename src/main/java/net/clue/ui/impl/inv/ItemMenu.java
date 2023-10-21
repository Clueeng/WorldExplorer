package net.clue.ui.impl.inv;

import net.clue.ui.WEFrame;
import net.clue.ui.WeComponents;
import net.clue.utils.ColorUtil;
import net.clue.utils.ItemBuilder;
import net.clue.utils.NBTUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

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

        NBTUtil.Player pl = world.getPlayer();

        FlowLayout l = new FlowLayout();
        l.setHgap(20);
        frame.setLayout(l);

        JLabel label = WeComponents.WeLabel(ColorUtil.text("§1Inventory Editor"), "medium");
        JLabel success = WeComponents.WeLabel(ColorUtil.text("§rUnchanged for now"), "small");

        JTextField itemName = WeComponents.WeTextField(pl.getItem(Byte.parseByte(slot)).cleanName() == null ? "item" : pl.getItem(Byte.parseByte(slot)).cleanName(), "small");
        itemName.setPreferredSize(new Dimension(250, 30));
        JSpinner count = WeComponents.WeSpinner(1, 1, 64, 1, "medium");
        JTextField enchants = WeComponents.WeTextField(pl.getItem(Byte.parseByte(slot)).getEnchantments() == null ? "enchantment" : pl.getItem(Byte.parseByte(slot)).clean_mc(pl.getItem(Byte.parseByte(slot)).getEnchantments().keySet().iterator().next()), "small");
        enchants.setPreferredSize(new Dimension(250, 30));
        JSpinner lvl = WeComponents.WeSpinner(5, 1, 255, 1, "medium");


        HashMap<String, Integer> ench = new HashMap<>();
        /*JButton applyEnch = WeComponents.WeButton(ColorUtil.text("§1Apply enchantment " + slot), "small");
        applyEnch.addActionListener(e -> {
            System.out.println("set enchantments " + enchants.getText() + ":" + (Integer) lvl.getValue());
            ench.put(enchants.getText(), (Integer) lvl.getValue());
        });*/

        JLabel count_text = WeComponents.WeLabel(ColorUtil.text("§fCount"), "medium");
        JLabel level = WeComponents.WeLabel(ColorUtil.text("§fLevel"), "medium");

        JComboBox<String> enchants_ = WeComponents.WeComboBox(pl.getItem(Byte.parseByte(slot)).getEnchantments().keySet().toArray(new String[0]), "medium");
        JButton removeEnchant = WeComponents.WeButton(ColorUtil.text("§fRemove enchantment"), "medium");
        JButton addEnchant = WeComponents.WeButton(ColorUtil.text("§fAdd enchantment"), "medium");

        enchants_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // I need to get the selected enchant name and its level. And put those in the text fields
                String enchant_name = enchants_.getSelectedItem().toString();
                int enchant_level = pl.getItem(Byte.parseByte(slot)).getEnchantmentLevel(enchant_name);
                System.out.println(enchant_name + " : " + enchant_level);
                enchants.setText(ItemBuilder.cleanName(enchant_name));
                lvl.setValue(enchant_level);


            }
        });

        removeEnchant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // I need to remove the enchants that's in the text-field from the list and the item and combo list
                //System.out.println(enchants.getText());
                pl.getItem(Byte.parseByte(slot)).removeEnchantment("minecraft:"+enchants.getText());
                if(ench.get("minecraft:"+enchants.getText()) != null){
                    ench.remove("minecraft:"+enchants.getText());
                }
                if(ench.get(enchants.getText()) != null){
                    ench.remove(enchants.getText());
                }
                enchants_.removeItem("minecraft:"+enchants.getText());
                if(enchants_.getSelectedItem() != null){
                    enchants_.removeItem(enchants.getText());
                }

                // Removes from the item
                /*String selectedEnchantment = (String) enchants_.getSelectedItem();
                if (pl.getItem(Byte.parseByte(slot)) != null && selectedEnchantment != null) {
                    pl.getItem(Byte.parseByte(slot)).getEnchantments().remove(selectedEnchantment);
                }
                // Removes from the jcombo box
                enchants_.removeItem(enchants_.getSelectedItem());*/
            }
        });

        addEnchant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // I need to add the enchants that's in the text-field to the list and the item
                System.out.println("set enchantments " + enchants.getText() + ":" + (Integer) lvl.getValue());
                ench.put(enchants.getText(), (Integer) lvl.getValue());
                enchants_.addItem("minecraft:"+enchants.getText());


                System.out.println(pl.getInventory());
                Integer v = (Integer) count.getValue();
                System.out.println("Slot " + Byte.parseByte(slot) + " name: " + itemName.getText() + " count: " + v.byteValue() + " enchs: " + pl.getEnchants(ench));
                pl.removeItem(Byte.parseByte(slot));
                if(ench.isEmpty()){
                    ItemBuilder item = new ItemBuilder("minecraft:"+itemName.getText(),  ((Integer) count.getValue()).byteValue(), Byte.parseByte(slot));
                    pl.addItem(item);
                    //player.setItem(Byte.parseByte(slot), "minecraft:" + itemName.getText(), ((Integer) count.getValue()).byteValue());
                }else{

                    ItemBuilder item = new ItemBuilder("minecraft:"+itemName.getText(),  ((Integer) count.getValue()).byteValue(), Byte.parseByte(slot))
                            .withEnchantments(ench);
                    pl.addItem(item);
                    //player.setItem(Byte.parseByte(slot), "minecraft:" + itemName.getText(), ((Integer) count.getValue()).byteValue(), ench);
                }
                pl.save();
                world.save();
            }
        });
        JButton setItem = WeComponents.WeButton(ColorUtil.text("§1Set item " + slot), "small");
        setItem.addActionListener(e -> {
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
        frame.add(enchants_);
        frame.add(addEnchant);
        frame.add(removeEnchant);

        frame.add(setItem);


        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
