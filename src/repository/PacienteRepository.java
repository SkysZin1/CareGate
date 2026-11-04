package repository;

import entities.Paciente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {
    private final Database database;

    public PacienteRepository(Database database) {
        this.database = database;
    }

    public boolean temDados() {
        String sql = "SELECT 1 FROM pacientes LIMIT 1";
        try (Connection conn = database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erro ao consultar pacientes: " + e.getMessage());
            return false;
        }
    }

    public List<Paciente> listar() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT nome, endereco, cpf, telefone FROM pacientes ORDER BY nome";
        try (Connection conn = database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pacientes.add(new Paciente(
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("cpf"),
                        rs.getString("telefone")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar pacientes do banco: " + e.getMessage());
        }
        return pacientes;
    }

    public void salvar(Paciente paciente) {
        String sql = "INSERT OR REPLACE INTO pacientes (cpf, nome, endereco, telefone) VALUES (?, ?, ?, ?)";
        try (Connection conn = database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paciente.getCpf());
            ps.setString(2, paciente.getNome());
            ps.setString(3, paciente.getEndereco());
            ps.setString(4, paciente.getTelefone());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar paciente no banco: " + e.getMessage());
        }
    }

    public void remover(String cpf) {
        String sql = "DELETE FROM pacientes WHERE cpf = ?";
        try (Connection conn = database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf.trim());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao remover paciente do banco: " + e.getMessage());
        }
    }

    public void migrarTxt(File arquivo) {
        if (!arquivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                Paciente paciente = converterLinhaParaPaciente(linha);
                if (paciente != null) salvar(paciente);
            }
        } catch (IOException e) {
            System.out.println("Erro ao migrar pacientes.txt: " + e.getMessage());
        }
    }

    private Paciente converterLinhaParaPaciente(String linha) {
        if (linha.contains("|")) {
            String[] dados = linha.split("\\|", 4);
            if (dados.length < 4) return null;
            return new Paciente(dados[0].trim(), dados[1].trim(), dados[2].trim(), dados[3].trim());
        }

        String[] dados = linha.split(",");
        if (dados.length < 4) return null;

        String nome = dados[0].trim();
        String telefone = dados[dados.length - 1].trim();
        String cpf = dados[dados.length - 2].trim();
        StringBuilder enderecoBuilder = new StringBuilder();
        for (int i = 1; i < dados.length - 2; i++) {
            if (enderecoBuilder.length() > 0) enderecoBuilder.append(",");
            enderecoBuilder.append(dados[i].trim());
        }
        return new Paciente(nome, enderecoBuilder.toString(), cpf, telefone);
    }
}
