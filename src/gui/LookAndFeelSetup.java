package gui;

import javax.swing.*;
import java.awt.*;

public final class LookAndFeelSetup {
    private LookAndFeelSetup() {
    }

    public static void apply() {
        setupFlatLaf();
        setupDefaults();
    }

    private static void setupFlatLaf() {
        try {
            Class<?> flatLightLaf = Class.forName("com.formdev.flatlaf.FlatLightLaf");
            flatLightLaf.getMethod("setup").invoke(null);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("FlatLaf nao encontrado em lib/flatlaf.jar.", exception);
        }
    }

    private static void setupDefaults() {
        Font defaultFont = Ui.font(13, Font.PLAIN);
        UIManager.put("defaultFont", defaultFont);
        UIManager.put("Label.font", defaultFont);
        UIManager.put("Button.font", Ui.font(13, Font.BOLD));
        UIManager.put("TextField.font", defaultFont);
        UIManager.put("ComboBox.font", defaultFont);
        UIManager.put("Table.font", defaultFont);
        UIManager.put("TableHeader.font", Ui.font(13, Font.BOLD));

        UIManager.put("Component.arc", 10);
        UIManager.put("Button.arc", 10);
        UIManager.put("TextComponent.arc", 8);
        UIManager.put("ComboBox.arc", 8);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.width", 12);

        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Component.innerFocusWidth", 0);
        UIManager.put("Button.innerFocusWidth", 0);
        UIManager.put("Button.margin", new Insets(8, 14, 8, 14));

        UIManager.put("Panel.background", Ui.BACKGROUND);
        UIManager.put("Table.background", Ui.CARD);
        UIManager.put("Table.foreground", Ui.TEXT);
        UIManager.put("Table.selectionBackground", new Color(219, 245, 246));
        UIManager.put("Table.selectionForeground", Ui.TEXT);
        UIManager.put("Table.gridColor", Ui.BORDER);
        UIManager.put("Table.showHorizontalLines", true);
        UIManager.put("Table.showVerticalLines", true);
        UIManager.put("TableHeader.background", new Color(250, 251, 253));
        UIManager.put("TableHeader.foreground", Ui.TEXT);

        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("TextField.foreground", Ui.TEXT);
        UIManager.put("TextField.caretForeground", Ui.PRIMARY_DARK);
        UIManager.put("ComboBox.background", Color.WHITE);
        UIManager.put("ComboBox.foreground", Ui.TEXT);
    }
}
