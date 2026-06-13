package gui;

import entities.Clinica;
import entities.Gravacao;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CareGateFrame extends JFrame {
    private final Clinica clinica;
    private final Gravacao gravacao;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel content = new JPanel(cardLayout);
    private final Map<String, SidebarButton> navigationButtons = new LinkedHashMap<>();

    private DashboardPanel dashboardPanel;
    private MedicosPanel medicosPanel;
    private PacientesPanel pacientesPanel;
    private ConsultasPanel consultasPanel;

    public CareGateFrame(Clinica clinica, Gravacao gravacao) {
        super("CareGate - Sistema de Gestao de Clinica");
        this.clinica = clinica;
        this.gravacao = gravacao;

        setSize(1280, 760);
        setMinimumSize(new Dimension(1100, 680));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImages(AppIcon.createImages());
        setLayout(new BorderLayout());
        getContentPane().setBackground(Ui.BACKGROUND);

        add(createSidebar(), BorderLayout.WEST);
        add(createContent(), BorderLayout.CENTER);
        showPage("Dashboard");
    }

    private JPanel createContent() {
        content.setBackground(Ui.BACKGROUND);

        dashboardPanel = new DashboardPanel(clinica, gravacao, this::refreshAll);
        medicosPanel = new MedicosPanel(clinica, gravacao, this::refreshAll);
        pacientesPanel = new PacientesPanel(clinica, gravacao, this::refreshAll);
        consultasPanel = new ConsultasPanel(clinica, gravacao, this::refreshAll);

        content.add(dashboardPanel, "Dashboard");
        content.add(medicosPanel, "Medicos");
        content.add(pacientesPanel, "Pacientes");
        content.add(consultasPanel, "Consultas");
        return content;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new GradientSidebar();
        sidebar.setPreferredSize(new Dimension(235, 0));
        sidebar.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(BorderFactory.createEmptyBorder(34, 18, 18, 18));

        JPanel brandRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        brandRow.setOpaque(false);
        brandRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel logo = new JLabel(new ImageIcon(AppIcon.createImage(42)));
        JLabel brand = new JLabel("CareGate");
        brand.setFont(Ui.font(30, Font.BOLD));
        brand.setForeground(Color.WHITE);
        brandRow.add(logo);
        brandRow.add(brand);

        JLabel subtitle = new JLabel("Gestao de Clinica");
        subtitle.setFont(Ui.font(13, Font.PLAIN));
        subtitle.setForeground(new Color(225, 247, 248));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        top.add(brandRow);
        top.add(Box.createVerticalStrut(6));
        top.add(subtitle);
        top.add(Box.createVerticalStrut(28));

        JPanel nav = new JPanel();
        nav.setOpaque(false);
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setAlignmentX(Component.LEFT_ALIGNMENT);

        addNavButton(nav, "Dashboard", "Dashboard");
        addNavButton(nav, "Medicos", "Medicos");
        addNavButton(nav, "Pacientes", "Pacientes");
        addNavButton(nav, "Consultas", "Consultas");
        top.add(nav);

        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setBorder(BorderFactory.createEmptyBorder(18, 18, 28, 18));

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(190, 230, 232));
        footer.add(separator);
        footer.add(Box.createVerticalStrut(18));

        JLabel app = new JLabel("CareGate");
        app.setFont(Ui.font(13, Font.BOLD));
        app.setForeground(Color.WHITE);
        JLabel version = new JLabel("Sistema de Gestao de Clinica");
        version.setFont(Ui.font(12, Font.PLAIN));
        version.setForeground(new Color(225, 247, 248));
        JLabel number = new JLabel("Versao 2.0");
        number.setFont(Ui.font(12, Font.PLAIN));
        number.setForeground(new Color(225, 247, 248));

        footer.add(app);
        footer.add(Box.createVerticalStrut(4));
        footer.add(version);
        footer.add(Box.createVerticalStrut(2));
        footer.add(number);

        sidebar.add(top, BorderLayout.NORTH);
        sidebar.add(footer, BorderLayout.SOUTH);
        return sidebar;
    }

    private void addNavButton(JPanel nav, String label, String pageName) {
        SidebarButton button = new SidebarButton(label);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        button.addActionListener(e -> showPage(pageName));

        navigationButtons.put(pageName, button);
        nav.add(button);
        nav.add(Box.createVerticalStrut(10));
    }

    private void showPage(String pageName) {
        cardLayout.show(content, pageName);
        for (Map.Entry<String, SidebarButton> entry : navigationButtons.entrySet()) {
            boolean selected = entry.getKey().equals(pageName);
            entry.getValue().setSelectedState(selected);
        }
        refreshAll();
    }

    private void refreshAll() {
        if (dashboardPanel != null) dashboardPanel.refresh();
        if (medicosPanel != null) medicosPanel.refresh();
        if (pacientesPanel != null) pacientesPanel.refresh();
        if (consultasPanel != null) consultasPanel.refresh();
    }

    private static class GradientSidebar extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            GradientPaint paint = new GradientPaint(0, 0, Ui.SIDEBAR, 0, getHeight(), Ui.SIDEBAR_DARK);
            g2.setPaint(paint);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }

    private static class SidebarButton extends JButton {
        private boolean selectedState;

        SidebarButton(String text) {
            super(text);
            setFont(Ui.font(15, Font.BOLD));
            setForeground(Color.WHITE);
            setHorizontalAlignment(SwingConstants.LEFT);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 18));
        }

        void setSelectedState(boolean selectedState) {
            this.selectedState = selectedState;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (selectedState) {
                g2.setColor(new Color(236, 250, 250));
            } else if (getModel().isRollover()) {
                g2.setColor(new Color(255, 255, 255, 35));
            } else {
                g2.setColor(new Color(255, 255, 255, 12));
            }
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

            FontMetrics metrics = g2.getFontMetrics(getFont());
            int textY = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
            g2.setFont(getFont());
            g2.setColor(selectedState ? Ui.PRIMARY_DARK : Color.WHITE);
            g2.drawString(getText(), 18, textY);
            g2.dispose();
        }
    }
}
