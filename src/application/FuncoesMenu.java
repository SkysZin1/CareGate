package application;

import entities.Clinica;
import entities.Paciente;

public class FuncoesMenu {  // Classe Auxiliar ao programa principal.

    public void exibirMenu(){
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║ CareGate - SISTEMA DE GESTÃO DE CLÍNICA║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("\n[1] Cadastrar Paciente");
        System.out.println("[2] Cadastrar Médico");
        System.out.println("[3] Agendar Consulta");
        System.out.println("[4] Realizar Consulta");
        System.out.println("[5] Listar Consultas do Mês");
        System.out.println("[6] Buscar Histórico de Paciente");
        System.out.println("[7] Listar Todos os Médicos");
        System.out.println("[8] Listar Todos os Pacientes");
        System.out.println("[9] Gerar Relatório Financeiro");
        System.out.println("[0] Sair\n");
    }

    public void cadastrarPaciente(Clinica clinica){
        //Colocar dados, criar o paciente e add pra clinica


        // Clinica.addPaciente(paciente);
    }
}
