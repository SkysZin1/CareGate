package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MedicoOdontologo extends Medico {


    public MedicoOdontologo(String nome, String CRM, String especialidade, Integer idade, Integer valorConsultaBase) {
        super(nome, CRM, especialidade, idade, valorConsultaBase);
    }

    @Override
    public Integer calcularValorConsulta() {
        return 180;
    }

    @Override
    public Integer obterTempoConsultaMin() {
        return 50;
    }

    @Override
    public Boolean podeAgendarConsulta(LocalDateTime Data) {
        return null;
    }

    @Override
    public String obterProtocoloAtendimento() {
        return "";
    }
}
