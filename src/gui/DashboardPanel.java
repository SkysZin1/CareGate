package gui;

import entities.Clinica;
import entities.Consulta;
import entities.Gravacao;
import entities.Medico;
import entities.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;

public class DashboardPanel extends JPanel {
    private final Clinica clinica;
    private final Gravacao gravacao;
    private final Runnable onDataChanged;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private final JLabel consultasHojeValue = new JLabel("0");
    private final JLabel pacientesValue = new JLabel("0");
    private final JLabel proximasValue = new JLabel("0");
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Data", "Hora", "Paciente", "Medico", "Especialidade", "Situacao"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JComboBox<PacienteItem> pacienteCombo = new JComboBox<>();
    private final JComboBox<MedicoItem> medicoCombo = new JComboBox<>();
    private final JTextField especialidadeField = Ui.textField();
    private final JTextField dataField = Ui.textField();
    private final JComboBox<String> horaCombo = new JComboBox<>(new String[]{
            "08:00", "09:00", "10:30", "11:30", "14:00", "15:00", "16:30", "17:30"
    });
    private final JComboBox<String> duracaoCombo = new JComboBox<>(new String[]{
            "30 minutos", "45 minutos", "50 minutos"
    });
    private final JTextArea observacoesArea = Ui.textArea();

    public DashboardPanel(Clinica clinica, Gravacao gravacao, Runnable onDataChanged) {
        this.clinica = clinica;
        this.gravacao = gravacao;
        this.onDataChanged = onDataChanged;

        setLayout(new BorderLayout());
        setBackground(Ui.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));
        add(createMainContent(), BorderLayout.CENTER);
        refresh();
    }

    private JPanel createMainContent() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 22));
        wrapper.setOpaque(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(Ui.title("Dashboard"), BorderLayout.WEST);
        wrapper.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(22, 22));
        center.setOpaque(false);
        center.add(createCards(), BorderLayout.NORTH);

        JPanel lower = new JPanel(new BorderLayout(22, 0));
        lower.setOpaque(false);
        lower.add(createConsultasCard(), BorderLayout.CENTER);
        lower.add(createNovaConsultaCard(), BorderLayout.EAST);
        center.add(lower, BorderLayout.CENTER);

        wrapper.add(center, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createCards() {
        JPanel cards = new JPanel(new GridLayout(1, 3, 18, 0));
        cards.setOpaque(false);
        cards.add(statCard("Consultas de Hoje", consultasHojeValue, "Agendadas para hoje", new Color(10, 92, 176)));
        cards.add(statCard("Pacientes Ativos", pacientesValue, "Total de pacientes ativos", Ui.SUCCESS));
        cards.add(statCard("Proximas Consultas", proximasValue, "Proximos 7 dias", new Color(10, 92, 176)));
        return cards;
    }

    private JPanel statCard(String title, JLabel value, String subtitle, Color accent) {
        JPanel card = Ui.card();
        card.setLayout(new BorderLayout(18, 0));
        card.setBorder(BorderFactory.createCompoundBorder(new Ui.RoundedBorder(Ui.BORDER, 12), BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JLabel icon = new JLabel(" ");
        icon.setPreferredSize(new Dimension(72, 72));
        icon.setOpaque(true);
        icon.setBackground(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 22));
        icon.setBorder(new Ui.RoundedBorder(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 35), 16));

        JPanel texts = new JPanel();
        texts.setOpaque(false);
        texts.setLayout(new BoxLayout(texts, BoxLayout.Y_AXIS));

        JLabel titleLabel = Ui.label(title);
        titleLabel.setFont(Ui.font(15, Font.BOLD));

        value.setFont(Ui.font(30, Font.BOLD));
        value.setForeground(accent);

        JLabel subtitleLabel = Ui.label(subtitle);
        subtitleLabel.setForeground(Ui.MUTED);

        texts.add(titleLabel);
        texts.add(Box.createVerticalStrut(5));
        texts.add(value);
        texts.add(Box.createVerticalStrut(4));
        texts.add(subtitleLabel);

        card.add(icon, BorderLayout.WEST);
        card.add(texts, BorderLayout.CENTER);
        return card;
    }

    private JPanel createConsultasCard() {
        JPanel card = Ui.card();
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createCompoundBorder(new Ui.RoundedBorder(Ui.BORDER, 12), BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = Ui.title("Proximas Consultas");
        title.setFont(Ui.font(18, Font.BOLD));
        JButton atualizar = Ui.secondaryButton("Atualizar");
        atualizar.addActionListener(e -> refresh());
        header.add(title, BorderLayout.WEST);
        header.add(atualizar, BorderLayout.EAST);

        JTable table = new JTable(tableModel);
        Ui.styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Ui.BORDER));

        card.add(header, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    private JPanel createNovaConsultaCard() {
        JPanel card = Ui.card();
        card.setPreferredSize(new Dimension(335, 0));
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createCompoundBorder(new Ui.RoundedBorder(Ui.BORDER, 12), BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        JLabel title = Ui.title("Nova Consulta");
        title.setFont(Ui.font(18, Font.BOLD));
        card.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        especialidadeField.setEditable(false);
        dataField.setText(LocalDate.now().format(dateFormatter));
        medicoCombo.addActionListener(e -> atualizarEspecialidadeSelecionada());

        addField(form, gbc, "Paciente", pacienteCombo);
        addField(form, gbc, "Medico", medicoCombo);
        addField(form, gbc, "Especialidade", especialidadeField);

        JPanel dateTime = new JPanel(new GridLayout(1, 2, 12, 0));
        dateTime.setOpaque(false);
        dateTime.add(wrappedField("Data", dataField));
        dateTime.add(wrappedField("Hora", horaCombo));
        gbc.gridy++;
        form.add(dateTime, gbc);

        addField(form, gbc, "Duracao", duracaoCombo);
        addField(form, gbc, "Observacoes", new JScrollPane(observacoesArea));

        JButton salvar = Ui.primaryButton("Salvar");
        salvar.addActionListener(e -> salvarConsulta());
        gbc.gridy++;
        form.add(salvar, gbc);

        card.add(form, BorderLayout.CENTER);
        return card;
    }

    private void addField(JPanel form, GridBagConstraints gbc, String label, JComponent component) {
        gbc.gridy++;
        JLabel labelComponent = Ui.label(label);
        form.add(labelComponent, gbc);
        gbc.gridy++;
        component.setPreferredSize(new Dimension(120, component instanceof JScrollPane ? 72 : 38));
        form.add(component, gbc);
    }

    private JPanel wrappedField(String label, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setOpaque(false);
        panel.add(Ui.label(label), BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private void salvarConsulta() {
        PacienteItem pacienteItem = (PacienteItem) pacienteCombo.getSelectedItem();
        MedicoItem medicoItem = (MedicoItem) medicoCombo.getSelectedItem();
        if (pacienteItem == null || medicoItem == null) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente e um medico.");
            return;
        }

        try {
            LocalDate data = LocalDate.parse(dataField.getText().trim(), dateFormatter);
            LocalTime hora = LocalTime.parse((String) horaCombo.getSelectedItem(), timeFormatter);
            int id = gravacao.getIdUltimaConsulta() + 1;
            String observacoes = observacoesArea.getText().trim();
            Consulta consulta = new Consulta(medicoItem.medico, pacienteItem.paciente, LocalDateTime.of(data, hora), observacoes, id);
            clinica.addConsulta(consulta);
            gravacao.salvarNovaConsulta(consulta);
            observacoesArea.setText("");
            JOptionPane.showMessageDialog(this, "Consulta agendada com sucesso.");
            onDataChanged.run();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data invalida. Use o formato dd/MM/aaaa.");
        }
    }

    private void atualizarEspecialidadeSelecionada() {
        MedicoItem item = (MedicoItem) medicoCombo.getSelectedItem();
        especialidadeField.setText(item == null ? "" : item.medico.getEspecialidade());
    }

    public void refresh() {
        refreshComboBoxes();
        refreshCards();
        refreshTable();
    }

    private void refreshComboBoxes() {
        pacienteCombo.removeAllItems();
        for (Paciente paciente : clinica.listarPacientes()) {
            pacienteCombo.addItem(new PacienteItem(paciente));
        }

        medicoCombo.removeAllItems();
        for (Medico medico : clinica.listarMedicos()) {
            medicoCombo.addItem(new MedicoItem(medico));
        }
        atualizarEspecialidadeSelecionada();
    }

    private void refreshCards() {
        LocalDate hoje = LocalDate.now();
        long consultasHoje = clinica.listarConsultas().stream()
                .filter(consulta -> consulta.getDataConsulta().toLocalDate().equals(hoje))
                .count();
        long proximosSeteDias = clinica.listarConsultas().stream()
                .filter(consulta -> {
                    LocalDate data = consulta.getDataConsulta().toLocalDate();
                    return !data.isBefore(hoje) && !data.isAfter(hoje.plusDays(7));
                })
                .count();

        consultasHojeValue.setText(String.valueOf(consultasHoje));
        pacientesValue.setText(String.valueOf(clinica.listarPacientes().size()));
        proximasValue.setText(String.valueOf(proximosSeteDias));
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Consulta> consultas = clinica.listarConsultas();
        consultas.sort(Comparator.comparing(Consulta::getDataConsulta));
        for (Consulta consulta : consultas) {
            tableModel.addRow(new Object[]{
                    consulta.getDataConsulta().format(dateFormatter),
                    consulta.getDataConsulta().format(timeFormatter),
                    consulta.getPaciente().getNome(),
                    consulta.getMedico().getNome(),
                    consulta.getMedico().getEspecialidade(),
                    "Confirmada"
            });
        }
    }

    private static class PacienteItem {
        private final Paciente paciente;

        PacienteItem(Paciente paciente) {
            this.paciente = paciente;
        }

        @Override
        public String toString() {
            return paciente.getNome() + " - " + paciente.getCpf();
        }
    }

    private static class MedicoItem {
        private final Medico medico;

        MedicoItem(Medico medico) {
            this.medico = medico;
        }

        @Override
        public String toString() {
            return medico.getNome() + " - " + medico.getCRM();
        }
    }
}
