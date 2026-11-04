package gui;

import application.Inicializacao;
import entities.Clinica;
import entities.Gravacao;
import service.ClinicaService;

import javax.swing.*;

public class CareGateSwing {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LookAndFeelSetup.apply();

            if (!LoginDialog.showLogin()) {
                return;
            }

            Clinica clinica = new Clinica();
            Gravacao gravacao = new Gravacao();
            Inicializacao.Inicializar(clinica, gravacao);
            ClinicaService service = new ClinicaService(clinica, gravacao);

            CareGateFrame frame = new CareGateFrame(service);
            frame.setVisible(true);
        });
    }
}
