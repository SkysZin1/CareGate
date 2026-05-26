// feito por Gustavo  - (25/05/26)

package entities;

import java.time.LocalDateTime;

public class MedicoClinico extends Medico {

    public MedicoClinico(String nome, String CRM, String especialidade, Integer idade, Integer valorConsultaBase) {
        super(nome, CRM, especialidade, idade, valorConsultaBase);
    }

    public MedicoClinico() {

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
