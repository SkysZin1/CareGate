// Feito por Daniel (25/05/2026)

package application;

import entities.*;

import java.util.Scanner;

public class Menu {  // Classe Auxiliar ao programa principal.
    Scanner sc = new Scanner(System.in);
    public static void exibirMenu(){
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

        System.out.println("Digite o nome do Paciente");
        String nome = sc.nextLine();
        System.out.println("Digite o CPF do Paciente");
        String cpf = sc.nextLine();
        System.out.println("Digite o telefone do Paciente");
        String telefone = sc.nextLine();
        System.out.println("Digite o endereco do Paciente");
        String endereco = sc.nextLine();

        Paciente paciente = new Paciente(nome, cpf, telefone, endereco);
        clinica.addPaciente(paciente);
        System.out.println("✓ Paciente cadastrado com sucesso!");

    }

    public void cadastrarMedico(Clinica clinica){

        System.out.println("Digite o nome do Medico");
        String nome = sc.nextLine();
        System.out.println("Digite o CRM do Medico");
        String crm = sc.nextLine();
        System.out.println("Digite a especialidade do Medico");
        String especialidade = sc.nextLine();
        System.out.println("Digite a idade");
        Integer idade = sc.nextInt();
        System.out.println("Digite a valor do Medico");
        Integer valorConsulta = sc.nextInt();

        System.out.println("Qual o tipo de consulta do Medico?");
        boolean aux = true;
        while (aux){
            System.out.println("[1] Cirurgião\n[2] Clínico\n[3] Odontológico");
            int opcao = sc.nextInt();
            Medico medico = null;
            switch (opcao){
                case 1:
                    medico = new MedicoCirurgiao(nome, crm, especialidade, idade, valorConsulta);
                    clinica.addMedico(medico);
                    aux = false;
                    break;
                case 2:
                    medico = new MedicoClinico(nome, crm, especialidade, idade, valorConsulta);
                    clinica.addMedico(medico);
                    aux = false;
                    break;
                case 3:
                    medico = new MedicoOdontologo(nome, crm, especialidade, idade, valorConsulta);
                    clinica.addMedico(medico);
                    aux = false;
                    break;
                default:
                    System.out.println("Por favor digite um valor valido");
            }
        }
        System.out.println("✓ Médico cadastrado com sucesso!");
    }


}
