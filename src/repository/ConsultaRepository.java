package repository;

import entities.Clinica;
import entities.Consulta;
import entities.Medico;
import entities.Paciente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {
    private final Database database;

    public ConsultaRepository(Database database) {
        this.database = database;
    }

    public boolean temDados() {
        String sql = "SELECT 1 FROM consultas LIMIT 1";
        try (Connection conn = database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erro ao consultar consultas: " + e.getMessage());
            return false;
        }
    }

    public List<Consulta> listar(Clinica clinica) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT id, cpf, crm, data_hora, diagnostico FROM consultas ORDER BY data_hora";
        try (Connection conn = database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Paciente paciente = clinica.getPacienteByCPF(rs.getString("cpf"));
                Medico medico = clinica.getMedicoByCRM(rs.getString("crm"));
                if (paciente == null || medico == null) continue;
                consultas.add(new Consulta(
                        medico,
                        paciente,
                        LocalDateTime.parse(rs.getString("data_hora")),
                        rs.getString("diagnostico"),
                        rs.getInt("id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar consultas do banco: " + e.getMessage());
        }
        return consultas;
    }

    public void salvar(Consulta consulta) {
        String sql = "INSERT OR REPLACE INTO consultas (id, cpf, crm, data_hora, diagnostico) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, consulta.getIdConsulta());
            ps.setString(2, consulta.getPaciente().getCpf());
            ps.setString(3, consulta.getMedico().getCRM());
            ps.setString(4, consulta.getDataConsulta().toString());
            ps.setString(5, consulta.getDiagnostico());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar consulta no banco: " + e.getMessage());
        }
    }

    public void remover(int id) {
        String sql = "DELETE FROM consultas WHERE id = ?";
        try (Connection conn = database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao remover consulta do banco: " + e.getMessage());
        }
    }

    public int ultimoId() {
        String sql = "SELECT COALESCE(MAX(id), 0) AS ultimo_id FROM consultas";
        try (Connection conn = database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("ultimo_id");
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ultimo ID de consulta: " + e.getMessage());
        }
        return 0;
    }

    public void migrarTxt(File arquivo) {
        if (!arquivo.exists()) return;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String sql = "INSERT OR REPLACE INTO consultas (id, cpf, crm, data_hora, diagnostico) VALUES (?, ?, ?, ?, ?)";
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo));
             Connection conn = database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(",", 5);
                if (dados.length < 4) continue;

                LocalDate data = LocalDate.parse(dados[3].trim(), formatter);
                ps.setInt(1, Integer.parseInt(dados[0].trim()));
                ps.setString(2, dados[1].trim());
                ps.setString(3, dados[2].trim());
                ps.setString(4, data.atStartOfDay().toString());
                ps.setString(5, dados.length > 4 ? dados[4].trim() : "");
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Erro ao migrar consultas.txt: " + e.getMessage());
        }
    }
}
