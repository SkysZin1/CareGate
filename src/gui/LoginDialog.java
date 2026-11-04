package gui;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private boolean authenticated;
    private final JTextField userField = Ui.textField();
    private final JPasswordField passwordField = new JPasswordField();

    private LoginDialog(Frame owner) {
        super(owner, "Login - CareGate", true);
        setSize(360, 260);
        setResizable(false);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Ui.BACKGROUND);
        setIconImages(AppIcon.createImages());

        JPanel content = Ui.card();
        content.setLayout(new GridBagLayout());
        content.setBorder(BorderFactory.createCompoundBorder(new Ui.RoundedBorder(Ui.BORDER, 12), BorderFactory.createEmptyBorder(18, 20, 18, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel title = Ui.title("CareGate");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        content.add(title, gbc);

        JLabel subtitle = Ui.label("Acesso administrativo");
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setForeground(Ui.MUTED);
        gbc.gridy++;
        content.add(subtitle, gbc);

        gbc.gridy++;
        content.add(Ui.label("Usuario"), gbc);
        gbc.gridy++;
        userField.setText("admin");
        content.add(userField, gbc);

        gbc.gridy++;
        content.add(Ui.label("Senha"), gbc);
        gbc.gridy++;
        passwordField.setFont(Ui.font(13, Font.PLAIN));
        passwordField.setBorder(new Ui.RoundedBorder(Ui.BORDER, 8));
        passwordField.setText("admin");
        content.add(passwordField, gbc);

        JButton entrar = Ui.primaryButton("Entrar");
        entrar.addActionListener(e -> autenticar());
        getRootPane().setDefaultButton(entrar);
        gbc.gridy++;
        content.add(entrar, gbc);

        add(content, BorderLayout.CENTER);
    }

    public static boolean showLogin() {
        LoginDialog dialog = new LoginDialog(null);
        dialog.setVisible(true);
        return dialog.authenticated;
    }

    private void autenticar() {
        String usuario = userField.getText().trim();
        String senha = new String(passwordField.getPassword());
        if ("admin".equals(usuario) && "admin".equals(senha)) {
            authenticated = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario ou senha invalidos.");
        }
    }
}
