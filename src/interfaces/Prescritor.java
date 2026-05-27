// feito por miguel - (27/05/26)
package interfaces;

import entities.Consulta;
import entities.Medicamento;
import entities.Receita;

public interface Prescritor {
    Receita criarReceita(Consulta consulta);
    boolean podePrescrever(Medicamento medicamento);
}