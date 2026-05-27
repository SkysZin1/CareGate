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

    public void getMedicos() {
        System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", "Nome", "CRM", "Especialidade", "Idade", "Valor da Consulta Base");
        System.out.println("--------------------------------------------------------------------------------");
        for (Medico medico : medicos){
            System.out.printf("%-15s %-15s %-15s %-15d %-15d%n", medico.getNome(), medico.getCRM(), medico.getEspecialidade(), medico.getIdade(), medico.getValorConsultaBase());
        }
    }

    public void getPacientes() {
        System.out.printf("%-15s %-15s %-15s %-15s%n", "Nome", "Endereço", "CPF", "Telefone");
        System.out.println("--------------------------------------------------------------------------------");
        for (Paciente paciente : pacientes){
            System.out.printf("%-15s %-15s %-15s %-15s%n", paciente.getNome(), paciente.getEndereco(), paciente.getCpf(), paciente.getTelefone());
        }

    }

    public void getHistoricoConsulta() {
        System.out.printf("%-15s %-15s %-15s %-15s%n", "Médico", "Paciente", "Data", "Diagnostico");
        System.out.println("--------------------------------------------------------------------------------");
        for (Consulta consulta : historicoConsulta){
            System.out.printf("%-15s %-15s %-15s %-15s%n", consulta.getMedico().getNome(), consulta.getPaciente().getNome(), consulta.getDataConsulta(), consulta.getDiagnostico());
        }
    }

}
