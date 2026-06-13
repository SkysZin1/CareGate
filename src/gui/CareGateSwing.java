package gui;

import application.Inicializacao;
import entities.Clinica;
import entities.Gravacao;

import javax.swing.*;

public class CareGateSwing {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // If the system look and feel is unavailable, Swing uses the default one.
            }

            Clinica clinica = new Clinica();
            Gravacao gravacao = new Gravacao();
            Inicializacao.Inicializar(clinica, gravacao);

            CareGateFrame frame = new CareGateFrame(clinica, gravacao);
            frame.setVisible(true);
        });
    }
}
