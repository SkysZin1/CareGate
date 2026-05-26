package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MedicoCirurgiao extends Medico {

    public MedicoCirurgiao(String nome, String CRM, String especialidade, Integer idade, Integer valorConsultaBase) {
        super(nome, CRM, especialidade, idade, valorConsultaBase);
    }

    @Override
    public Integer calcularValorConsulta() {
        return 350;
    }

    @Override
    public Integer obterTempoConsultaMin() {
        return 45;
    }

    @Override
    public Boolean podeAgendarConsulta(LocalDateTime Data) {
        return null;
    }

    @Override
    public String obterProtocoloAtendimento() {
        return "Diagnosticar, avaliar e realizar intervenções cirúrgicas";
    }
}
