// feito por miguel - (27/05/26)
package interfaces;

import java.time.LocalDateTime;
import java.util.List;

public interface Agendavel {
    public Boolean podeAgendarConsulta(LocalDateTime data);
    List<String> obterHorariosDisponiveis();
    
}
