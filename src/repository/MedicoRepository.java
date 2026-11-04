package repository;

import entities.Medico;
import entities.MedicoCirurgiao;
import entities.MedicoClinico;
import entities.MedicoOdontologo;

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

public class MedicoRepository {
    private final Database database;

    public MedicoRepository(Database database) {
        this.database = database;
    }

    public boolean temDados() {
        String sql = "SELECT 1 FROM medicos LIMIT 1";
        try (Connection conn = database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erro ao consultar medicos: " + e.getMessage());
            return false;
        }
    }

    public List<Medico> listar() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT tipo, nome, crm, especialidade, idade, valor_consulta FROM medicos ORDER BY nome";
        try (Connection conn = database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Medico medico = criarMedico(
                        rs.getString("tipo"),
                        rs.getString("nome"),
                        rs.getString("crm"),
                        rs.getString("especialidade"),
                        rs.getInt("idade"),
                        rs.getInt("valor_consulta")
                );
                if (medico != null) medicos.add(medico);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar medicos do banco: " + e.getMessage());
        }
        return medicos;
    }

    public void salvar(Medico medico) {
        String sql = "INSERT OR REPLACE INTO medicos (crm, tipo, nome, especialidade, idade, valor_consulta) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medico.getCRM());
            ps.setString(2, tipoDoMedico(medico));
            ps.setString(3, medico.getNome());
            ps.setString(4, medico.getEspecialidade());
            ps.setInt(5, medico.getIdade());
            ps.setInt(6, medico.getValorConsultaBase());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar medico no banco: " + e.getMessage());
        }
    }

    public void remover(String crm) {
        String sql = "DELETE FROM medicos WHERE crm = ?";
        try (Connection conn = database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, crm.trim());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao remover medico do banco: " + e.getMessage());
        }
    }

    public void migrarTxt(File arquivo) {
        if (!arquivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(",", 6);
                if (dados.length < 6) continue;
                Medico medico = criarMedico(
                        dados[0].trim(),
                        dados[1].trim(),
                        dados[2].trim(),
                        dados[3].trim(),
                        Integer.parseInt(dados[4].trim()),
                        Integer.parseInt(dados[5].trim())
                );
                if (medico != null) salvar(medico);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao migrar medicos.txt: " + e.getMessage());
        }
    }

    public static Medico criarMedico(String tipo, String nome, String crm, String especialidade, int idade, int valor) {
        switch (tipo) {
            case "Clinico":
                return new MedicoClinico(nome, crm, especialidade, idade, valor);
            case "Cirurgiao":
                return new MedicoCirurgiao(nome, crm, especialidade, idade, valor);
            case "Odontologo":
                return new MedicoOdontologo(nome, crm, especialidade, idade, valor);
            default:
                return null;
        }
    }

    public static String tipoDoMedico(Medico medico) {
        if (medico instanceof MedicoCirurgiao) return "Cirurgiao";
        if (medico instanceof MedicoOdontologo) return "Odontologo";
        return "Clinico";
    }
}
