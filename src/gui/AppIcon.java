package gui;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public final class AppIcon {
    private AppIcon() {
    }

    public static Image createImage(int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        GradientPaint background = new GradientPaint(0, 0, Ui.SIDEBAR, size, size, Ui.SIDEBAR_DARK);
        g2.setPaint(background);
        g2.fillRoundRect(0, 0, size, size, size / 4, size / 4);

        g2.setColor(new Color(255, 255, 255, 40));
        g2.fillOval(size / 8, size / 10, size / 2, size / 2);

        Stroke heartStroke = new BasicStroke(Math.max(3f, size * 0.055f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(heartStroke);
        g2.setColor(Color.WHITE);

        GeneralPath heart = new GeneralPath();
        heart.moveTo(size * 0.50, size * 0.74);
        heart.curveTo(size * 0.20, size * 0.53, size * 0.15, size * 0.32, size * 0.31, size * 0.24);
        heart.curveTo(size * 0.42, size * 0.18, size * 0.50, size * 0.27, size * 0.50, size * 0.27);
        heart.curveTo(size * 0.50, size * 0.27, size * 0.58, size * 0.18, size * 0.69, size * 0.24);
        heart.curveTo(size * 0.85, size * 0.32, size * 0.80, size * 0.53, size * 0.50, size * 0.74);
        g2.draw(heart);

        Stroke pulseStroke = new BasicStroke(Math.max(2f, size * 0.045f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(pulseStroke);
        GeneralPath pulse = new GeneralPath();
        pulse.moveTo(size * 0.26, size * 0.48);
        pulse.lineTo(size * 0.39, size * 0.48);
        pulse.lineTo(size * 0.45, size * 0.38);
        pulse.lineTo(size * 0.53, size * 0.59);
        pulse.lineTo(size * 0.60, size * 0.48);
        pulse.lineTo(size * 0.74, size * 0.48);
        g2.draw(pulse);

        g2.dispose();
        return image;
    }

    public static List<Image> createImages() {
        int[] sizes = {16, 24, 32, 48, 64, 128, 256};
        List<Image> images = new ArrayList<>();
        for (int size : sizes) {
            images.add(createImage(size));
        }
        return images;
    }
}
