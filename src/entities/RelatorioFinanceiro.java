package entities;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Acumula as consultas realizadas por cada médico e calcula:
 *  - Faturamento individual por médico
 *  - Subtotal agrupado por tipo (Cirurgião, Clínico, Odontólogo)
 *  - Total geral da clínica
 */
public class RelatorioFinanceiro {

    // Associa cada médico à quantidade de consultas que ele realizou
    // LinkedHashMap mantém a ordem de inserção, garantindo que o relatório exiba os médicos na mesma ordem em que foram registrados
    private final Map<Medico, Integer> registroConsultas = new LinkedHashMap<>();

    public void registrarConsulta(Medico medico, int quantidade) {
        // merge(chave, valor, função): se a chave já existe, aplica a função entre o valor atual e o nov
        registroConsultas.merge(medico, quantidade, Integer::sum);
    }

    /**
     * Gera e imprime o relatório financeiro completo no console.
     * Percorre todos os médicos registrados, calcula seus faturamentos individuais e acumula os subtotais por tipo de médico.
     */
    public void gerarRelatorio() {
        System.out.println("=".repeat(60));
        System.out.println("         RELATÓRIO FINANCEIRO — CAREGATE");
        System.out.println("=".repeat(60));

        // Acumula receita por tipo de médico
        Map<String, Double> receitaPorTipo = new HashMap<>();
        double totalGeral = 0;

        System.out.println("\n DETALHAMENTO POR MÉDICO\n");

        // Percorre cada entrada do mapa: médico e sua quantidade de consultas
        for (Map.Entry<Medico, Integer> entry : registroConsultas.entrySet()) {
            Medico medico = entry.getKey();
            int consultas = entry.getValue();

            double faturamento = medico.calcularFaturamento(consultas);

            System.out.printf("  %-30s%n", medico.paraTexto());

            // Exibe linha resumida: tipo, consultas, valor unitário e total
            System.out.printf("  %s%n%n", medico.gerarResumoFinanceiro(consultas));

            // Acumula o faturamento no subtotal do tipo desse médico.
            // merge soma o valor se a chave já existir, ou insere se for nova.
            receitaPorTipo.merge(medico.getTipoMedico(), faturamento, Double::sum);

            totalGeral += faturamento;
        }

        System.out.println("-".repeat(60));
        System.out.println(" RECEITA POR TIPO DE MÉDICO\n");

        for (Map.Entry<String, Double> entry : receitaPorTipo.entrySet()) {
            System.out.printf("  %-15s R$ %,.2f%n", entry.getKey() + ":", entry.getValue());
        }

        // Rodapé com o total geral
        System.out.println("\n" + "=".repeat(60));
        System.out.printf("  TOTAL GERAL DA CLÍNICA:   R$ %,.2f%n", totalGeral);
        System.out.println("=".repeat(60));
    }
}