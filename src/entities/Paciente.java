// feito por Davi - (25/05/26)
// editado por miguel - (27/05/26)
package entities;
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

    public void addConsulta(Consulta consulta){
        this.consultas.add(consulta);
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
