// feito por Gustavo  - (25/05/26)

package entities;

import java.time.LocalDateTime;

public abstract class Medico {
private String nome, CRM, especialidade;
private Integer idade, valorConsultaBase;


    public Medico(String nome, String CRM, String especialidade, Integer idade, Integer valorConsultaBase) {
        this.nome = nome;
        this.CRM = CRM;
        this.especialidade = especialidade;
        this.idade = idade;
        this.valorConsultaBase = valorConsultaBase;
    }

    // Métod0 abstrato que cada subclasse implementará dizendo quem ela é
    public abstract String paraTexto();


    public Medico() {
    }

    public abstract Integer calcularValorConsulta();

    public abstract Integer obterTempoConsultaMin();

    public abstract Boolean podeAgendarConsulta(LocalDateTime Data);

    public abstract String obterProtocoloAtendimento();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCRM() {
        return CRM;
    }

    public void setCRM(String CRM) {
        this.CRM = CRM;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Integer getValorConsultaBase() {
        return valorConsultaBase;
    }

    public void setValorConsultaBase(Integer valorConsultaBase) {
        this.valorConsultaBase = valorConsultaBase;
    }
}
