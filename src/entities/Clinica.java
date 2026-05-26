package entities;

import java.util.ArrayList;

public class Clinica {

    ArrayList<Medico> medicos = new ArrayList<>();
    ArrayList<Paciente> pacientes = new ArrayList<>();
    ArrayList<Consulta> historicoConsulta = new ArrayList<>();

    public Clinica(){
    }

    public void addMedico(Medico medico){
        medicos.add(medico);
    }

    public void addPaciente(Paciente paciente){
        pacientes.add(paciente);
    }

    public void addConsulta(Consulta consulta){
        historicoConsulta.add(consulta);
    }

    public ArrayList<Medico> getMedicos() {
        return medicos;
    }

    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }

    public ArrayList<Consulta> getHistoricoConsulta() {
        return historicoConsulta;
    }

    public void inicializaClinica(){
        // Colocar aqui TODOS os 21 medicos e 7 pacientes de teste, criar eles e adicionar no respectivo array
    }
}
