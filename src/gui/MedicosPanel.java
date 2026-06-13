package gui;

import entities.Clinica;
import entities.Gravacao;
import entities.Medico;
import entities.MedicoCirurgiao;
import entities.MedicoClinico;
import entities.MedicoOdontologo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MedicosPanel extends JPanel {
    private final Clinica clinica;
    private final Gravacao gravacao;
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
    private final JTextField nomeField = Ui.textField();
    private final JTextField crmField = Ui.textField();
    private final JTextField especialidadeField = Ui.textField();
    private final JTextField idadeField = Ui.textField();
    private final JTextField valorField = Ui.textField();
    private final JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Clinico", "Cirurgiao", "Odontologo"});

    public MedicosPanel(Clinica clinica, Gravacao gravacao, Runnable onDataChanged) {
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
        header.add(Ui.title("Medicos"), BorderLayout.WEST);
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
        JButton remover = Ui.secondaryButton("Remover selecionado");
        remover.addActionListener(e -> removerMedicoSelecionado());
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
        String nome = nomeField.getText().trim();
        String crm = crmField.getText().trim();
        String especialidade = especialidadeField.getText().trim();
        String tipo = (String) tipoCombo.getSelectedItem();

        if (nome.isEmpty() || crm.isEmpty() || especialidade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha nome, CRM e especialidade.");
            return;
        }

        if (clinica.getMedicoByCRM(crm) != null) {
            JOptionPane.showMessageDialog(this, "Ja existe um medico com esse CRM.");
            return;
        }

        try {
            int idade = Integer.parseInt(idadeField.getText().trim());
            int valor = Integer.parseInt(valorField.getText().trim());
            Medico medico;
            if ("Cirurgiao".equals(tipo)) {
                medico = new MedicoCirurgiao(nome, crm, especialidade, idade, valor);
            } else if ("Odontologo".equals(tipo)) {
                medico = new MedicoOdontologo(nome, crm, especialidade, idade, valor);
            } else {
                medico = new MedicoClinico(nome, crm, especialidade, idade, valor);
            }

            clinica.addMedico(medico);
            gravacao.salvarNovoMedico(medico);
            limparCampos();
            JOptionPane.showMessageDialog(this, "Medico cadastrado com sucesso.");
            onDataChanged.run();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade e valor devem ser numeros inteiros.");
        }
    }

    private void removerMedicoSelecionado() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um medico na tabela.");
            return;
        }
        int modelRow = table.convertRowIndexToModel(row);
        String crm = (String) tableModel.getValueAt(modelRow, 1);
        int confirm = JOptionPane.showConfirmDialog(this, "Remover o medico " + crm + "?", "Confirmar remocao", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        clinica.removeMedico(crm);
        gravacao.removerMedicoDoArquivo(crm);
        onDataChanged.run();
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
        for (Medico medico : clinica.listarMedicos()) {
            tableModel.addRow(new Object[]{
                    medico.getNome(),
                    medico.getCRM(),
                    medico.getEspecialidade(),
                    tipoDoMedico(medico),
                    medico.getIdade(),
                    medico.getValorConsultaBase()
            });
        }
    }

    private String tipoDoMedico(Medico medico) {
        if (medico instanceof MedicoCirurgiao) return "Cirurgiao";
        if (medico instanceof MedicoOdontologo) return "Odontologo";
        return "Clinico";
    }
}
