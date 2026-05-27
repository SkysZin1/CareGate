// feito por Gustavo  - (25/05/26)
// editado por Miguel - (27/05/26)
package entities;

import interfaces.Agendavel;
import interfaces.Prescritor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicoClinico extends Medico implements Agendavel, Prescritor {

    public MedicoClinico(String nome, String CRM, String especialidade, Integer idade, Integer valorConsultaBase) {
        super(nome, CRM, especialidade, idade, valorConsultaBase);
    }

    @Override
    public Integer calcularValorConsulta() {
        return 150;
    }

    @Override
    public Integer obterTempoConsultaMin() {
        return 30;
    }

    @Override
    public Boolean podeAgendarConsulta(LocalDateTime Data) {
        return null;
    }

    @Override
    public String obterProtocoloAtendimento() {
        return "";
    }

     @Override
    public List<String> obterHorariosDisponiveis() {
        List<String> horarios = new ArrayList<>();
        return horarios;
    }

    @Override
    public Receita criarReceita(Consulta consulta) {
        System.out.println("Criando receita médica padrão...");
        return new Receita();
    }

    @Override
    public boolean podePrescrever(Medicamento medicamento) {
        return true;
    }
}
