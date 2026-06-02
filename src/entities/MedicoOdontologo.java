// feito por Gustavo  - (25/05/26)

package entities;

import interfaces.Agendavel;
import interfaces.Prescritor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicoOdontologo extends Medico implements Agendavel, Prescritor {


    public MedicoOdontologo(String nome, String CRM, String especialidade, Integer idade, Integer valorConsultaBase) {
        super(nome, CRM, especialidade, idade, valorConsultaBase);
    }

    public MedicoOdontologo() {
    }

    @Override
    public String paraTexto() {
        // Guarda o identificador "Cirurgiao" no início da linha para sabermos restaurar depois
        return "Cirurgiao," + getNome() + "," + getCRM() + "," + getEspecialidade() + "," + getIdade() + "," + getValorConsultaBase();
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
