// feito por Gustavo  - (25/05/26)

package entities;

import interfaces.Faturavel;

import java.time.LocalDateTime;

public class MedicoCirurgiao extends Medico implements Faturavel {

    public MedicoCirurgiao(String nome, String CRM, String especialidade, Integer idade, Integer valorConsultaBase) {
        super(nome, CRM, especialidade, idade, valorConsultaBase);
    }

    public MedicoCirurgiao() {
    }

    @Override
    public String paraTexto() {
        return "Cirurgiao, " + getNome() + ", " + getCRM() + ", " + getEspecialidade() + ", " + getIdade() + " anos, " + getValorConsultaBase() + " reais por consulta";
    }

    @Override
    public Integer calcularValorConsulta() {
        return 350;
    }

    @Override
    public String obterProtocoloAtendimento() {
        return "Diagnosticar, avaliar e realizar intervenções cirúrgicas";
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

    @Override
    public double getValorConsulta() {
        return getValorConsultaBase();
    }

    @Override
    public String getTipoMedico() {
        return "Cirurgião";
    }
}
