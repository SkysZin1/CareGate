// feito por Davi - (25/05/26)
// editado por miguel - (27/05/26)
package entities;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private String nome, endereco, cpf, telefone;

    private List<Consulta> consultas = new ArrayList<>();

    public Paciente(String nome, String endereco, String cpf, String telefone) {
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return nome + "|" + endereco + "|" + cpf + "|" + telefone;
    }

    public void addConsulta(Consulta consulta){
        this.consultas.add(consulta);
    }

    public void removeConsulta(int idConsulta) {
        consultas.removeIf(consulta -> consulta.getIdConsulta() == idConsulta);
    }

    public void getHistoricoConsulta() {
        System.out.printf("%-15s %-15s %-15s %-18s %-15s%n", "id", "Médico", "Paciente", "Data/Horário", "Diagnostico");
        System.out.println("--------------------------------------------------------------------------------");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Consulta consulta : consultas){
            String dataFormatada = consulta.getDataConsulta().format(formatter);
            System.out.printf("%-15s %-15s %-15s %-18s %-15s%n", consulta.getIdConsulta(), consulta.getMedico().getNome(), consulta.getPaciente().getNome(), dataFormatada, consulta.getDiagnostico());
        }
    }

}
