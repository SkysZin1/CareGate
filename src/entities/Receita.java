// feito por Davi - (25/05/26)
// editado por Miguel - (27/05/26)

package entities;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
public class Receita {
    private List <Medicamento> listaMedicamentos = new ArrayList<>();
    private LocalDate data;


    public Receita() {
    
    }

    public Receita(LocalDate data) {
        this.data = data;
    }

    public void addMedicamento(Medicamento medicamento){
        this.listaMedicamentos.add(medicamento);
    }

    public void removeMedicamento(Medicamento medicamento){
        this.listaMedicamentos.remove(medicamento);
    }

    public boolean verificaValidade(){
        LocalDate hoje = LocalDate.now();
        long diasPassados = hoje.toEpochDay() - this.data.toEpochDay(); // toEpochDay -> numero total de dias
                                                                        // que se passaram desde um dia X (01/01/1970)

        if(diasPassados >= 0 && diasPassados<= 30){
            return true;
        }else{
            return false;
        }
    }

    public List<Medicamento> getListaMedicamentos() {
        return listaMedicamentos;
    }

    public LocalDate getData() {
        return data;
    }
}
