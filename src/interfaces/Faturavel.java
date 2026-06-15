package interfaces;

public interface Faturavel {

    double getValorConsulta();
    String getTipoMedico();

    default double calcularFaturamento(int quantidadeConsultas) {
        return getValorConsulta() * quantidadeConsultas;
    }

    default String gerarResumoFinanceiro(int quantidadeConsultas) {
        return String.format(
                "Consultas: %d | Valor unitário: R$ %.2f | Total: R$ %.2f",
                quantidadeConsultas,
                getValorConsulta(),
                calcularFaturamento(quantidadeConsultas)
        );
    }
}
