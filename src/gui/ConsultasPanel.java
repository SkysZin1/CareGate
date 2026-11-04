package gui;

import entities.Consulta;
import entities.Medico;
import entities.Paciente;
import service.ClinicaService;
import service.ServiceException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConsultasPanel extends JPanel {
    private final ClinicaService service;
    private final Runnable onDataChanged;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Data", "Hora", "Paciente", "Medico", "Especialidade", "Observacoes"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(tableModel);
    private final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
    private final JTextField filtroField = Ui.textField();
    private final JTextField dataFiltroField = Ui.textField();

    public ConsultasPanel(ClinicaService service, Runnable onDataChanged) {
        this.service = service;
        this.onDataChanged = onDataChanged;

        setLayout(new BorderLayout(0, 22));
        setBackground(Ui.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));
        add(createHeader(), BorderLayout.NORTH);
        add(createTableCard(), BorderLayout.CENTER);
        refresh();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setOpaque(false);
        header.add(Ui.title("Consultas"), BorderLayout.WEST);

        JPanel filters = new JPanel(new GridLayout(1, 2, 10, 0));
        filters.setOpaque(false);
        filtroField.putClientProperty("JTextField.placeholderText", "Buscar paciente, medico ou especialidade");
        dataFiltroField.putClientProperty("JTextField.placeholderText", "Filtrar data dd/MM/yyyy");
        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { aplicarFiltro(); }
            public void removeUpdate(DocumentEvent e) { aplicarFiltro(); }
            public void changedUpdate(DocumentEvent e) { aplicarFiltro(); }
        };
        filtroField.getDocument().addDocumentListener(listener);
        dataFiltroField.getDocument().addDocumentListener(listener);
        filters.add(filtroField);
        filters.add(dataFiltroField);
        header.add(filters, BorderLayout.EAST);
        return header;
    }

    private JPanel createTableCard() {
        JPanel card = Ui.card();
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createCompoundBorder(new Ui.RoundedBorder(Ui.BORDER, 12), BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = Ui.title("Historico de Consultas");
        title.setFont(Ui.font(18, Font.BOLD));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        JButton detalhes = Ui.secondaryButton("Detalhes");
        detalhes.addActionListener(e -> mostrarDetalhes());
        JButton editar = Ui.secondaryButton("Remarcar");
        editar.addActionListener(e -> editarSelecionada());
        JButton cancelar = Ui.secondaryButton("Cancelar");
        cancelar.addActionListener(e -> cancelarSelecionada());
        JButton relatorio = Ui.primaryButton("Relatorio");
        relatorio.addActionListener(e -> gerarRelatorio());
        actions.add(detalhes);
        actions.add(editar);
        actions.add(cancelar);
        actions.add(relatorio);

        header.add(title, BorderLayout.WEST);
        header.add(actions, BorderLayout.EAST);

        Ui.styleTable(table);
        table.setRowSorter(sorter);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Ui.BORDER));

        card.add(header, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    private void editarSelecionada() {
        Consulta consulta = consultaSelecionada();
        if (consulta == null) return;

        JComboBox<PacienteItem> pacienteCombo = new JComboBox<>();
        for (Paciente paciente : service.listarPacientes()) {
            PacienteItem item = new PacienteItem(paciente);
            pacienteCombo.addItem(item);
            if (paciente.getCpf().equals(consulta.getPaciente().getCpf())) pacienteCombo.setSelectedItem(item);
        }

        JComboBox<MedicoItem> medicoCombo = new JComboBox<>();
        for (Medico medico : service.listarMedicos()) {
            MedicoItem item = new MedicoItem(medico);
            medicoCombo.addItem(item);
            if (medico.getCRM().equals(consulta.getMedico().getCRM())) medicoCombo.setSelectedItem(item);
        }

        JTextField data = Ui.textField();
        JTextField hora = Ui.textField();
        JTextArea observacoes = Ui.textArea();
        data.setText(consulta.getDataConsulta().format(dateFormatter));
        hora.setText(consulta.getDataConsulta().format(timeFormatter));
        observacoes.setText(consulta.getDiagnostico());

        JPanel form = new JPanel(new GridLayout(0, 1, 6, 6));
        form.add(Ui.label("Paciente")); form.add(pacienteCombo);
        form.add(Ui.label("Medico")); form.add(medicoCombo);
        form.add(Ui.label("Data")); form.add(data);
        form.add(Ui.label("Hora")); form.add(hora);
        form.add(Ui.label("Observacoes")); form.add(new JScrollPane(observacoes));

        int result = JOptionPane.showConfirmDialog(this, form, "Editar consulta", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            PacienteItem pacienteItem = (PacienteItem) pacienteCombo.getSelectedItem();
            MedicoItem medicoItem = (MedicoItem) medicoCombo.getSelectedItem();
            service.atualizarConsulta(
                    consulta.getIdConsulta(),
                    pacienteItem.paciente.getCpf(),
                    medicoItem.medico.getCRM(),
                    LocalDate.parse(data.getText().trim(), dateFormatter),
                    LocalTime.parse(hora.getText().trim(), timeFormatter),
                    observacoes.getText()
            );
            onDataChanged.run();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data ou hora invalida.");
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void mostrarDetalhes() {
        Consulta consulta = consultaSelecionada();
        if (consulta == null) return;
        String detalhe = "ID: " + consulta.getIdConsulta() +
                "\nData: " + consulta.getDataConsulta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                "\nPaciente: " + consulta.getPaciente().getNome() + " (" + consulta.getPaciente().getCpf() + ")" +
                "\nMedico: " + consulta.getMedico().getNome() + " (" + consulta.getMedico().getCRM() + ")" +
                "\nEspecialidade: " + consulta.getMedico().getEspecialidade() +
                "\nObservacoes: " + consulta.getDiagnostico();
        JOptionPane.showMessageDialog(this, detalhe, "Detalhes da consulta", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cancelarSelecionada() {
        Consulta consulta = consultaSelecionada();
        if (consulta == null) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Cancelar a consulta " + consulta.getIdConsulta() + "?", "Confirmar cancelamento", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            service.cancelarConsulta(consulta.getIdConsulta());
            onDataChanged.run();
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void gerarRelatorio() {
        try {
            File file = service.gerarRelatorio();
            JOptionPane.showMessageDialog(this, "Relatorio gerado em:\n" + file.getAbsolutePath());
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private Consulta consultaSelecionada() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta na tabela.");
            return null;
        }
        int modelRow = table.convertRowIndexToModel(row);
        int id = (Integer) tableModel.getValueAt(modelRow, 0);
        return service.buscarConsulta(id);
    }

    private void aplicarFiltro() {
        String texto = filtroField.getText().trim().toLowerCase();
        String data = dataFiltroField.getText().trim();
        sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                boolean dataOk = data.isEmpty() || entry.getStringValue(1).contains(data);
                if (!dataOk) return false;
                if (texto.isEmpty()) return true;
                for (int i = 0; i < entry.getValueCount(); i++) {
                    if (entry.getStringValue(i).toLowerCase().contains(texto)) return true;
                }
                return false;
            }
        });
    }

    public void refresh() {
        tableModel.setRowCount(0);
        for (Consulta consulta : service.listarConsultas()) {
            tableModel.addRow(new Object[]{
                    consulta.getIdConsulta(),
                    consulta.getDataConsulta().format(dateFormatter),
                    consulta.getDataConsulta().format(timeFormatter),
                    consulta.getPaciente().getNome(),
                    consulta.getMedico().getNome(),
                    consulta.getMedico().getEspecialidade(),
                    consulta.getDiagnostico()
            });
        }
        aplicarFiltro();
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
