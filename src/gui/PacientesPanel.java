package gui;

import entities.Consulta;
import entities.Paciente;
import service.ClinicaService;
import service.ServiceException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class PacientesPanel extends JPanel {
    private final ClinicaService service;
    private final Runnable onDataChanged;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Nome", "CPF", "Telefone", "Endereco", "Consultas"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(tableModel);
    private final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
    private final JTextField filtroField = Ui.textField();

    private final JTextField nomeField = Ui.textField();
    private final JTextField cpfField = Ui.textField();
    private final JTextField telefoneField = Ui.textField();
    private final JTextField enderecoField = Ui.textField();

    public PacientesPanel(ClinicaService service, Runnable onDataChanged) {
        this.service = service;
        this.onDataChanged = onDataChanged;

        setLayout(new BorderLayout(0, 22));
        setBackground(Ui.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));
        add(createHeader(), BorderLayout.NORTH);
        add(createBody(), BorderLayout.CENTER);
        refresh();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setOpaque(false);
        header.add(Ui.title("Pacientes"), BorderLayout.WEST);
        filtroField.putClientProperty("JTextField.placeholderText", "Buscar paciente, CPF ou telefone");
        filtroField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { aplicarFiltro(); }
            public void removeUpdate(DocumentEvent e) { aplicarFiltro(); }
            public void changedUpdate(DocumentEvent e) { aplicarFiltro(); }
        });
        header.add(filtroField, BorderLayout.EAST);
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
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);
        JButton detalhes = Ui.secondaryButton("Detalhes");
        detalhes.addActionListener(e -> mostrarDetalhes());
        JButton editar = Ui.secondaryButton("Editar");
        editar.addActionListener(e -> editarSelecionado());
        JButton remover = Ui.secondaryButton("Remover");
        remover.addActionListener(e -> removerSelecionado());
        buttons.add(detalhes);
        buttons.add(editar);
        buttons.add(remover);
        header.add(title, BorderLayout.WEST);
        header.add(buttons, BorderLayout.EAST);

        Ui.styleTable(table);
        table.setRowSorter(sorter);
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
        try {
            service.cadastrarPaciente(nomeField.getText(), enderecoField.getText(), cpfField.getText(), telefoneField.getText());
            limparCampos();
            JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso.");
            onDataChanged.run();
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void editarSelecionado() {
        Paciente paciente = pacienteSelecionado();
        if (paciente == null) return;

        JTextField nome = Ui.textField();
        JTextField telefone = Ui.textField();
        JTextField endereco = Ui.textField();
        nome.setText(paciente.getNome());
        telefone.setText(paciente.getTelefone());
        endereco.setText(paciente.getEndereco());

        JPanel form = new JPanel(new GridLayout(0, 1, 6, 6));
        form.add(Ui.label("CPF: " + paciente.getCpf()));
        form.add(Ui.label("Nome")); form.add(nome);
        form.add(Ui.label("Telefone")); form.add(telefone);
        form.add(Ui.label("Endereco")); form.add(endereco);

        int result = JOptionPane.showConfirmDialog(this, form, "Editar paciente", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            service.atualizarPaciente(paciente.getCpf(), nome.getText(), endereco.getText(), telefone.getText());
            onDataChanged.run();
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void mostrarDetalhes() {
        Paciente paciente = pacienteSelecionado();
        if (paciente == null) return;

        StringBuilder detalhe = new StringBuilder();
        detalhe.append("Nome: ").append(paciente.getNome())
                .append("\nCPF: ").append(paciente.getCpf())
                .append("\nTelefone: ").append(paciente.getTelefone())
                .append("\nEndereco: ").append(paciente.getEndereco())
                .append("\nConsultas: ").append(paciente.listarConsultas().size())
                .append("\n\nHistorico:");

        if (paciente.listarConsultas().isEmpty()) {
            detalhe.append("\nSem consultas cadastradas.");
        } else {
            for (Consulta consulta : paciente.listarConsultas()) {
                detalhe.append("\n#").append(consulta.getIdConsulta())
                        .append(" - ").append(consulta.getDataConsulta().format(dateFormatter))
                        .append(" - ").append(consulta.getMedico().getNome())
                        .append(" (").append(consulta.getMedico().getEspecialidade()).append(")");
            }
        }

        JOptionPane.showMessageDialog(this, detalhe.toString(), "Detalhes do paciente", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removerSelecionado() {
        Paciente paciente = pacienteSelecionado();
        if (paciente == null) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Remover o paciente " + paciente.getCpf() + "?", "Confirmar remocao", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            service.removerPaciente(paciente.getCpf());
            onDataChanged.run();
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private Paciente pacienteSelecionado() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente na tabela.");
            return null;
        }
        int modelRow = table.convertRowIndexToModel(row);
        String cpf = (String) tableModel.getValueAt(modelRow, 1);
        return service.buscarPaciente(cpf);
    }

    private void aplicarFiltro() {
        String filtro = filtroField.getText().trim();
        if (filtro.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(filtro)));
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        cpfField.setText("");
        telefoneField.setText("");
        enderecoField.setText("");
    }

    public void refresh() {
        tableModel.setRowCount(0);
        for (Paciente paciente : service.listarPacientes()) {
            tableModel.addRow(new Object[]{
                    paciente.getNome(),
                    paciente.getCpf(),
                    paciente.getTelefone(),
                    paciente.getEndereco(),
                    paciente.listarConsultas().size()
            });
        }
        aplicarFiltro();
    }
}
