package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Medico {
String nome, CRM, especialidade;
Integer idade, valorConsultaBase;

    public Medico(String nome, String CRM, String especialidade, Integer idade, Integer valorConsultaBase) {
        this.nome = nome;
        this.CRM = CRM;
        this.especialidade = especialidade;
        this.idade = idade;
        this.valorConsultaBase = valorConsultaBase;
    }

    public abstract Integer calcularValorConsulta();

    public abstract Integer obterTempoConsultaMin();

    public abstract Boolean podeAgendarConsulta(LocalDateTime Data);

    public abstract String obterProtocoloAtendimento();

}
