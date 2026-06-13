package gui;

import entities.Clinica;
import entities.Consulta;
import entities.Gravacao;
import entities.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class ConsultasPanel extends JPanel {
    private final Clinica clinica;
    private final Gravacao gravacao;
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

    public ConsultasPanel(Clinica clinica, Gravacao gravacao, Runnable onDataChanged) {
        this.clinica = clinica;
        this.gravacao = gravacao;
        this.onDataChanged = onDataChanged;

        setLayout(new BorderLayout(0, 22));
        setBackground(Ui.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));
        add(createHeader(), BorderLayout.NORTH);
        add(createTableCard(), BorderLayout.CENTER);
        refresh();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(Ui.title("Consultas"), BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        JButton atualizar = Ui.secondaryButton("Atualizar");
        atualizar.addActionListener(e -> refresh());
        JButton cancelar = Ui.primaryButton("Cancelar Consulta");
        cancelar.setBackground(new Color(204, 66, 66));
        cancelar.addActionListener(e -> cancelarConsultaSelecionada());
        actions.add(atualizar);
        actions.add(cancelar);
        header.add(actions, BorderLayout.EAST);
        return header;
    }

    private JPanel createTableCard() {
        JPanel card = Ui.card();
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createCompoundBorder(new Ui.RoundedBorder(Ui.BORDER, 12), BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        JLabel title = Ui.title("Historico de Consultas");
        title.setFont(Ui.font(18, Font.BOLD));
        card.add(title, BorderLayout.NORTH);

        Ui.styleTable(table);
        table.setAutoCreateRowSorter(true);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Ui.BORDER));

        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    private void cancelarConsultaSelecionada() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta na tabela.");
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        int id = (Integer) tableModel.getValueAt(modelRow, 0);
        Consulta consulta = clinica.getConsultaByIdConsulta(id);
        if (consulta == null) {
            JOptionPane.showMessageDialog(this, "Consulta nao encontrada.");
            refresh();
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Cancelar a consulta " + id + "?", "Confirmar cancelamento", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Paciente paciente = consulta.getPaciente();
        clinica.removeConsulta(id);
        paciente.removeConsulta(id);
        gravacao.removerConsultaDoArquivo(id);
        onDataChanged.run();
    }

    public void refresh() {
        tableModel.setRowCount(0);
        List<Consulta> consultas = clinica.listarConsultas();
        consultas.sort(Comparator.comparing(Consulta::getDataConsulta));
        for (Consulta consulta : consultas) {
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
    }
}
