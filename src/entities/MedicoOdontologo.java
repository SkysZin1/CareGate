// feito por Gustavo  - (25/05/26)

package entities;

import interfaces.Faturavel;
import java.time.LocalTime;
import java.util.Arrays;

public class MedicoOdontologo extends Medico implements Faturavel {


    public MedicoOdontologo(String nome, String CRM, String especialidade, Integer idade, Integer valorConsultaBase) {
        super(nome, CRM, especialidade, idade, valorConsultaBase);
    }

    public MedicoOdontologo() {
    }

    @Override
    public double getValorConsulta() {
        return getValorConsultaBase();
    }

    @Override
    public String getTipoMedico() {
        return "Odontólogo";
    }

    @Override
    public String paraTexto() {
        return "Odontologo, " + getNome() + ", " + getCRM() + ", " + getEspecialidade() + ", " + getIdade() + ", " + getValorConsultaBase();
    }

    @Override
    public Integer calcularValorConsulta() {
        return 180;
    }

    @Override
    public String obterProtocoloAtendimento() {
        return "Prevenir, diagnosticar e tratar problemas que afetam os dentes, a gengiva, a língua, o céu da boca e os ossos da face";
    }

    @Override
    protected AgendaMedico criarAgendaPadrao() {
        return new AgendaMedico("Noite", Arrays.asList(
                LocalTime.of(17, 0),
                LocalTime.of(18, 0),
                LocalTime.of(19, 0),
                LocalTime.of(20, 0)
        ));
    }

    @Override
    public String getNome() {
        return super.getNome();
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
    }

    @Override
    public String getCRM() {
        return super.getCRM();
    }

    @Override
    public void setCRM(String CRM) {
        super.setCRM(CRM);
    }

    @Override
    public String getEspecialidade() {
        return super.getEspecialidade();
    }

    @Override
    public void setEspecialidade(String especialidade) {
        super.setEspecialidade(especialidade);
    }

    @Override
    public Integer getIdade() {
        return super.getIdade();
    }

    @Override
    public void setIdade(Integer idade) {
        super.setIdade(idade);
    }

    @Override
    public Integer getValorConsultaBase() {
        return super.getValorConsultaBase();
    }

    @Override
    public void setValorConsultaBase(Integer valorConsultaBase) {
        super.setValorConsultaBase(valorConsultaBase);
    }

}
