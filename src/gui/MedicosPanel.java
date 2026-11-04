package gui;

import entities.Medico;
import service.ClinicaService;
import service.ServiceException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.regex.Pattern;

public class MedicosPanel extends JPanel {
    private final ClinicaService service;
    private final Runnable onDataChanged;
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Nome", "CRM", "Especialidade", "Tipo", "Idade", "Valor"}, 0
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
    private final JTextField crmField = Ui.textField();
    private final JTextField especialidadeField = Ui.textField();
    private final JTextField idadeField = Ui.textField();
    private final JTextField valorField = Ui.textField();
    private final JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Clinico", "Cirurgiao", "Odontologo"});

    public MedicosPanel(ClinicaService service, Runnable onDataChanged) {
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
        header.add(Ui.title("Medicos"), BorderLayout.WEST);

        filtroField.putClientProperty("JTextField.placeholderText", "Buscar medico, CRM ou especialidade");
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
        JLabel title = Ui.title("Lista de Medicos");
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
        table.setAutoCreateRowSorter(false);
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

        JLabel title = Ui.title("Novo Medico");
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
        addField(form, gbc, "CRM", crmField);
        addField(form, gbc, "Especialidade", especialidadeField);
        addField(form, gbc, "Tipo", tipoCombo);

        JPanel numbers = new JPanel(new GridLayout(1, 2, 12, 0));
        numbers.setOpaque(false);
        numbers.add(wrappedField("Idade", idadeField));
        numbers.add(wrappedField("Valor", valorField));
        gbc.gridy++;
        form.add(numbers, gbc);

        JButton salvar = Ui.primaryButton("Salvar Medico");
        salvar.addActionListener(e -> salvarMedico());
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

    private JPanel wrappedField(String label, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setOpaque(false);
        panel.add(Ui.label(label), BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private void salvarMedico() {
        try {
            service.cadastrarMedico(
                    (String) tipoCombo.getSelectedItem(),
                    nomeField.getText(),
                    crmField.getText(),
                    especialidadeField.getText(),
                    Integer.parseInt(idadeField.getText().trim()),
                    Integer.parseInt(valorField.getText().trim())
            );
            limparCampos();
            JOptionPane.showMessageDialog(this, "Medico cadastrado com sucesso.");
            onDataChanged.run();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade e valor devem ser numeros inteiros.");
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void editarSelecionado() {
        Medico medico = medicoSelecionado();
        if (medico == null) return;

        JTextField nome = Ui.textField();
        JTextField especialidade = Ui.textField();
        JTextField idade = Ui.textField();
        JTextField valor = Ui.textField();
        JComboBox<String> tipo = new JComboBox<>(new String[]{"Clinico", "Cirurgiao", "Odontologo"});

        nome.setText(medico.getNome());
        especialidade.setText(medico.getEspecialidade());
        idade.setText(String.valueOf(medico.getIdade()));
        valor.setText(String.valueOf(medico.getValorConsultaBase()));
        tipo.setSelectedItem(service.tipoDoMedico(medico));

        JPanel form = new JPanel(new GridLayout(0, 1, 6, 6));
        form.add(Ui.label("CRM: " + medico.getCRM()));
        form.add(Ui.label("Nome")); form.add(nome);
        form.add(Ui.label("Especialidade")); form.add(especialidade);
        form.add(Ui.label("Tipo")); form.add(tipo);
        form.add(Ui.label("Idade")); form.add(idade);
        form.add(Ui.label("Valor")); form.add(valor);

        int result = JOptionPane.showConfirmDialog(this, form, "Editar medico", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            service.atualizarMedico(
                    medico.getCRM(),
                    (String) tipo.getSelectedItem(),
                    nome.getText(),
                    especialidade.getText(),
                    Integer.parseInt(idade.getText().trim()),
                    Integer.parseInt(valor.getText().trim())
            );
            onDataChanged.run();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade e valor devem ser numeros inteiros.");
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void mostrarDetalhes() {
        Medico medico = medicoSelecionado();
        if (medico == null) return;
        String detalhe = "Nome: " + medico.getNome() +
                "\nCRM: " + medico.getCRM() +
                "\nTipo: " + service.tipoDoMedico(medico) +
                "\nEspecialidade: " + medico.getEspecialidade() +
                "\nIdade: " + medico.getIdade() +
                "\nValor: R$ " + medico.getValorConsultaBase() +
                "\nTempo medio: " + medico.obterTempoConsultaMin() + " minutos" +
                "\n\nProtocolo:\n" + medico.obterProtocoloAtendimento();
        JOptionPane.showMessageDialog(this, detalhe, "Detalhes do medico", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removerSelecionado() {
        Medico medico = medicoSelecionado();
        if (medico == null) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Remover o medico " + medico.getCRM() + "?", "Confirmar remocao", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            service.removerMedico(medico.getCRM());
            onDataChanged.run();
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private Medico medicoSelecionado() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um medico na tabela.");
            return null;
        }
        int modelRow = table.convertRowIndexToModel(row);
        String crm = (String) tableModel.getValueAt(modelRow, 1);
        return service.buscarMedico(crm);
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
        crmField.setText("");
        especialidadeField.setText("");
        idadeField.setText("");
        valorField.setText("");
        tipoCombo.setSelectedIndex(0);
    }

    public void refresh() {
        tableModel.setRowCount(0);
        for (Medico medico : service.listarMedicos()) {
            tableModel.addRow(new Object[]{
                    medico.getNome(),
                    medico.getCRM(),
                    medico.getEspecialidade(),
                    service.tipoDoMedico(medico),
                    medico.getIdade(),
                    medico.getValorConsultaBase()
            });
        }
        aplicarFiltro();
    }
}
