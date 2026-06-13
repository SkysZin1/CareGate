package gui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public final class Ui {
    public static final Color BACKGROUND = new Color(246, 248, 251);
    public static final Color SIDEBAR = new Color(0, 131, 143);
    public static final Color SIDEBAR_DARK = new Color(0, 109, 119);
    public static final Color PRIMARY = new Color(0, 137, 146);
    public static final Color PRIMARY_DARK = new Color(0, 105, 112);
    public static final Color SUCCESS = new Color(36, 166, 97);
    public static final Color TEXT = new Color(17, 24, 39);
    public static final Color MUTED = new Color(91, 101, 120);
    public static final Color BORDER = new Color(220, 225, 232);
    public static final Color CARD = Color.WHITE;

    private Ui() {
    }

    public static Font font(int size, int style) {
        return new Font("Segoe UI", style, size);
    }

    public static JPanel card() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD);
        panel.setBorder(new RoundedBorder(BORDER, 12));
        return panel;
    }

    public static JButton primaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(font(14, Font.BOLD));
        button.setForeground(Color.WHITE);
        button.setBackground(SUCCESS);
        button.setBorder(new RoundedBorder(new Color(31, 142, 84), 10));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 42));
        return button;
    }

    public static JButton secondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(font(13, Font.PLAIN));
        button.setForeground(TEXT);
        button.setBackground(Color.WHITE);
        button.setBorder(new RoundedBorder(BORDER, 9));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static JTextField textField() {
        JTextField field = new JTextField();
        field.setFont(font(13, Font.PLAIN));
        field.setBorder(new RoundedBorder(BORDER, 8));
        field.setPreferredSize(new Dimension(120, 38));
        return field;
    }

    public static JTextArea textArea() {
        JTextArea area = new JTextArea(4, 20);
        area.setFont(font(13, Font.PLAIN));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new RoundedBorder(BORDER, 8));
        return area;
    }

    public static JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(font(13, Font.PLAIN));
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setFont(font(22, Font.BOLD));
        label.setForeground(TEXT);
        return label;
    }

    public static void styleTable(JTable table) {
        table.setFont(font(13, Font.PLAIN));
        table.setForeground(TEXT);
        table.setGridColor(new Color(230, 234, 240));
        table.setRowHeight(44);
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        table.setSelectionBackground(new Color(225, 245, 246));
        table.setSelectionForeground(TEXT);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(font(13, Font.BOLD));
        header.setForeground(TEXT);
        header.setBackground(new Color(250, 251, 253));
        header.setBorder(BorderFactory.createLineBorder(BORDER));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        table.setDefaultRenderer(Object.class, renderer);
    }

    public static class RoundedBorder extends AbstractBorder {
        private final Color color;
        private final int radius;

        RoundedBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(8, 10, 8, 10);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.top = 8;
            insets.left = 10;
            insets.bottom = 8;
            insets.right = 10;
            return insets;
        }
    }
}
