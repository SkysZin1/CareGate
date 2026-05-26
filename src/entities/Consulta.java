package entities;

import java.time.LocalDateTime;

public class Consulta {
    private Medico medico;
    private Paciente paciente;
    private LocalDateTime dataConsulta;
    private String diagnostico;
    public Consulta(Medico medico, Paciente paciente, LocalDateTime dataConsulta,  String diagnostico) {
        this.medico = medico;
        this.paciente = paciente;
        this.dataConsulta = dataConsulta;
        this.diagnostico =  diagnostico;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
