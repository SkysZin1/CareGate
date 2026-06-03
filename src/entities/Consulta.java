package entities;

import java.time.LocalDateTime;

public class Consulta {
    private Medico medico;
    private Paciente paciente;
    private LocalDateTime dataConsulta;
    private String diagnostico;
    private int idConsulta;
    public Consulta(Medico medico, Paciente paciente, LocalDateTime dataConsulta, String diagnostico) {
        this.medico = medico;
        this.paciente = paciente;
        this.dataConsulta = dataConsulta;
        this.diagnostico =  diagnostico;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public Medico getMedico() {
        return medico;
    }
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }
    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }
    public String getDiagnostico() {
        return diagnostico;
    }
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
}
