package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MedicoClinico extends Medico {

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
        return "Diagnostica e trata doenças não cirúrgicas, solicita exames, prescreve medicamentos e, quando necessário, encaminha o paciente para um médico especialista";
    }
}
