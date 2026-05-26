// feito por Gustavo  - (25/05/26)

package entities;

import java.time.LocalDateTime;

public class MedicoOdontologo extends Medico {


    public MedicoOdontologo(String nome, String CRM, String especialidade, Integer idade, Integer valorConsultaBase) {
        super(nome, CRM, especialidade, idade, valorConsultaBase);
    }

    public MedicoOdontologo() {
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
        return "Prevenir, diagnosticar e tratar problemas que afetam os dentes, a gengiva, a língua, o céu da boca e os ossos da face";
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
