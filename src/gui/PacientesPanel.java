package gui;

import entities.Clinica;
import entities.Gravacao;
import entities.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PacientesPanel extends JPanel {
    private final Clinica clinica;
    private final Gravacao gravacao;
    private final Runnable onDataChanged;

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Nome", "CPF", "Telefone", "Endereco", "Consultas"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable table = new JTable(tableModel);
    private final JTextField nomeField = Ui.textField();
    private final JTextField cpfField = Ui.textField();
    private final JTextField telefoneField = Ui.textField();
    private final JTextField enderecoField = Ui.textField();

    public PacientesPanel(Clinica clinica, Gravacao gravacao, Runnable onDataChanged) {
        this.clinica = clinica;
        this.gravacao = gravacao;
        this.onDataChanged = onDataChanged;

        setLayout(new BorderLayout(0, 22));
        setBackground(Ui.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));
        add(createHeader(), BorderLayout.NORTH);
        add(createBody(), BorderLayout.CENTER);
        refresh();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(Ui.title("Pacientes"), BorderLayout.WEST);
        return header;
    }

    private JPanel createBody() {
        JPanel body = new JPanel(new BorderLayout(22, 0));
        body.setOpaque(false);
        body.add(createTableCard(), BorderLayout.CENTER);
        body.add(createFormCard(), BorderLayout.EAST);
        return body;
    }

    private JPanel createTableCard() {
        JPanel card = Ui.card();
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createCompoundBorder(new Ui.RoundedBorder(Ui.BORDER, 12), BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = Ui.title("Lista de Pacientes");
        title.setFont(Ui.font(18, Font.BOLD));
        JButton remover = Ui.secondaryButton("Remover selecionado");
        remover.addActionListener(e -> removerPacienteSelecionado());
        header.add(title, BorderLayout.WEST);
        header.add(remover, BorderLayout.EAST);

        Ui.styleTable(table);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Ui.BORDER));

        card.add(header, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    private JPanel createFormCard() {
        JPanel card = Ui.card();
        card.setPreferredSize(new Dimension(335, 0));
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createCompoundBorder(new Ui.RoundedBorder(Ui.BORDER, 12), BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        JLabel title = Ui.title("Novo Paciente");
        title.setFont(Ui.font(18, Font.BOLD));
        card.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addField(form, gbc, "Nome", nomeField);
        addField(form, gbc, "CPF", cpfField);
        addField(form, gbc, "Telefone", telefoneField);
        addField(form, gbc, "Endereco", enderecoField);

        JButton salvar = Ui.primaryButton("Salvar Paciente");
        salvar.addActionListener(e -> salvarPaciente());
        gbc.gridy++;
        form.add(salvar, gbc);

        card.add(form, BorderLayout.CENTER);
        return card;
    }

    private void addField(JPanel form, GridBagConstraints gbc, String label, JComponent component) {
        gbc.gridy++;
        form.add(Ui.label(label), gbc);
        gbc.gridy++;
        component.setPreferredSize(new Dimension(120, 38));
        form.add(component, gbc);
    }

    private void salvarPaciente() {
        String nome = nomeField.getText().trim();
        String cpf = cpfField.getText().trim();
        String telefone = telefoneField.getText().trim();
        String endereco = enderecoField.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF sao obrigatorios.");
            return;
        }

        if (!cpf.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this, "CPF deve ter 11 digitos, sem pontos ou tracos.");
            return;
        }

        if (clinica.getPacienteByCPF(cpf) != null) {
            JOptionPane.showMessageDialog(this, "Ja existe um paciente com esse CPF.");
            return;
        }

        Paciente paciente = new Paciente(nome, endereco, cpf, telefone);
        clinica.addPaciente(paciente);
        gravacao.salvarNovoPaciente(paciente);
        limparCampos();
        JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso.");
        onDataChanged.run();
    }

    private void removerPacienteSelecionado() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente na tabela.");
            return;
        }
        int modelRow = table.convertRowIndexToModel(row);
        String cpf = (String) tableModel.getValueAt(modelRow, 1);
        int confirm = JOptionPane.showConfirmDialog(this, "Remover o paciente " + cpf + "?", "Confirmar remocao", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        clinica.removePaciente(cpf);
        gravacao.removerPacienteDoArquivo(cpf);
        onDataChanged.run();
    }

    private void limparCampos() {
        nomeField.setText("");
        cpfField.setText("");
        telefoneField.setText("");
        enderecoField.setText("");
    }

    public void refresh() {
        tableModel.setRowCount(0);
        for (Paciente paciente : clinica.listarPacientes()) {
            tableModel.addRow(new Object[]{
                    paciente.getNome(),
                    paciente.getCpf(),
                    paciente.getTelefone(),
                    paciente.getEndereco(),
                    paciente.listarConsultas().size()
            });
        }
    }
}
