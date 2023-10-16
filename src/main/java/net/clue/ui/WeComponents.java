package net.clue.ui;

import net.clue.Main;
import net.clue.utils.ColorUtil;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicSpinnerUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class WeComponents {

    public static JButton WeButton(String text, String font){
        JButton jButton = new JButton();

        jButton.setFont(Main.fonts.get(font));
        jButton.setFocusPainted(false);
        //jButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        jButton.setBorderPainted(false);
        jButton.setForeground(Color.WHITE);
        jButton.setBackground(StyleProperties.MAIN_COLOR_DARKER);
        UIManager.put("Button.select", new ColorUIResource(StyleProperties.ACCENT_MAIN_COLOR_DARKER));
        jButton.setText(text);
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButton.setForeground(StyleProperties.ACCENT_MAIN_COLOR_DARKER.darker());
                jButton.setBackground(StyleProperties.ACCENT_MAIN_COLOR_DARKER.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton.setForeground(StyleProperties.ACCENT_MAIN_COLOR_DARKER);
                jButton.setBackground(StyleProperties.MAIN_COLOR_DARKER);
                super.mouseExited(e);
            }
        });

        return jButton;
    }

    public static JCheckBox checkBox(){
        JCheckBox check = new JCheckBox();
        Icon checkedIcon = new ImageIcon(createCheckImage(StyleProperties.ACCENT_MAIN_COLOR_DARKER));
        Icon uncheckedIcon = new ImageIcon(createCheckImage(Color.WHITE));

        // Set the custom icons
        check.setSelectedIcon(checkedIcon);
        check.setIcon(uncheckedIcon);

        return check;
    }

    private static Image createCheckImage(Color color) {
        BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(color);
        g2.fillRect(0, 0, 20, 20);
        g2.setColor(Color.WHITE);
        g2.drawLine(3, 10, 8, 15);
        g2.drawLine(8, 15, 17, 6);
        g2.dispose();
        return image;
    }

    public static JButton box(String text, String font, int w, int h){
        JButton jButton = new JButton();

        jButton.setFont(Main.fonts.get(font));
        jButton.setFocusPainted(false);
        //jButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        jButton.setSize(32, 32);
        jButton.setPreferredSize(new Dimension(32, 32));
        jButton.setLocation(w, h);
        jButton.setBorderPainted(false);
        jButton.setForeground(Color.WHITE);
        jButton.setBackground(StyleProperties.MAIN_COLOR_DARKER);
        UIManager.put("Button.select", new ColorUIResource(StyleProperties.ACCENT_MAIN_COLOR_DARKER));
        jButton.setText(text);
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButton.setBackground(StyleProperties.ACCENT_MAIN_COLOR_DARKER.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton.setBackground(StyleProperties.MAIN_COLOR_DARKER);
                super.mouseExited(e);
            }
        });

        return jButton;
    }

    public static JLabel WeLabel(String text, String font){
        JLabel label = new JLabel();
        label.setFont(Main.fonts.get(font));
        label.setText(ColorUtil.text(text));

        return label;
    }

    public static JTextField WeTextField(String text, String font){
        JTextField label = new JTextField(text);
        label.setFont(Main.fonts.get(font));
        label.setMinimumSize(new Dimension(200, 20));
        //label.setText(text);

        return label;
    }

    public static JComboBox<String> WeComboBox(String[] keys, String font){
        JComboBox<String>comboBox = new JComboBox<>(keys);

        comboBox.setFont(Main.fonts.get(font));
        comboBox.setBackground(StyleProperties.MAIN_COLOR_DARKER);
        comboBox.setForeground(Color.WHITE);
        comboBox.setBorder(new CustomComboBorder(Color.WHITE, 2));

        return comboBox;
    }

    public static JSpinner WeSpinner(int v, int min, int max, int step, String font){
        SpinnerModel model = new SpinnerNumberModel(v, min, max, step);
        JSpinner spinner = new JSpinner(model);
        spinner.setFont(Main.fonts.get(font));
        spinner.setBackground(StyleProperties.MAIN_COLOR_DARKER);
        spinner.setForeground(Color.white);
        spinner.setBorder(new CustomComboBorder(Color.white, 2));
        //spinner.setUI(new ModernSpinnerUI());

        return spinner;
    }

    static class ModernSpinnerUI extends BasicSpinnerUI {
        @Override
        protected Component createNextButton() {
            JButton nextButton = (JButton) super.createNextButton();

            // Customize the next button
            nextButton.setText("▲");
            nextButton.setBackground(Color.LIGHT_GRAY);
            nextButton.setForeground(Color.BLACK);
            nextButton.setBorder(BorderFactory.createEmptyBorder());

            return nextButton;
        }

        @Override
        protected Component createPreviousButton() {
            JButton previousButton = (JButton) super.createPreviousButton();

            // Customize the previous button
            previousButton.setText("▼");
            previousButton.setBackground(Color.LIGHT_GRAY);
            previousButton.setForeground(Color.BLACK);
            previousButton.setBorder(BorderFactory.createEmptyBorder());

            return previousButton;
        }
    }

    static class CustomComboBorder extends AbstractBorder {
        private final Color borderColor;
        private final int borderLength;

        CustomComboBorder(Color borderColor, int borderLength) {
            this.borderColor = borderColor;
            this.borderLength = borderLength;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int arc = height / 2; // Adjust the arc size as needed
            RoundRectangle2D rect = new RoundRectangle2D.Float(x, y, width, height, arc, arc);

            Area area = new Area(rect);
            g2d.setColor(borderColor);

            // Adjust the border length
            area.subtract(new Area(new Rectangle(x + borderLength, y, width - 2 * borderLength, height)));
            g2d.fill(area);

            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(2, borderLength, 2, borderLength); // Adjust top and bottom insets as needed
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = borderLength;
            insets.top = insets.bottom = 2; // Adjust top and bottom insets as needed
            return insets;
        }
    }



}
