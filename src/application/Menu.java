// Feito por Daniel (25/05/2026)

package application;

import entities.*;
import java.io.IOException;
import java.util.Scanner;

public class Menu {
    Scanner sc = new Scanner(System.in);
    public static void exibirMenu(){
            
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║ CareGate - SISTEMA DE GESTÃO DE CLÍNICA║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\n[1] Gerenciar Médicos");
            System.out.println("[2] Gerenciar Pacientes");
            System.out.println("[3] Agendar Consulta");
            System.out.println("[4] Realizar Consulta");
            System.out.println("[5] Listar Histórico de Consultas");
            System.out.println("[6] Gerar Relatório Financeiro");
            System.out.println("[0] Sair\n");
    }

    public static void exibirMenuMedicos() {
        
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         GERENCIAR MÉDICOS              ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("\n[1] Adicionar Médico");
        System.out.println("[2] Remover Médico");
        System.out.println("[3] Listar Médicos");
        System.out.println("[0] Voltar\n");
    }

    public static void exibirMenuPacientes() {
        
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         GERENCIAR PACIENTES            ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("\n[1] Adicionar Paciente");
        System.out.println("[2] Remover Paciente");
        System.out.println("[3] Listar Pacientes");
        System.out.println("[0] Voltar\n");
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

    public void removerPaciente(Clinica clinica){
        
        System.out.println("Digite o CPF do Paciente");
        String cpf = sc.nextLine();
        clinica.removePaciente(cpf);
        System.out.println("✓ Paciente removido com sucesso!");
    }

    public void cadastrarMedico(Clinica clinica, Gravacao gravacao){

        System.out.println("Digite o nome do Medico");
        String nome = sc.nextLine();
        System.out.println("Digite o CRM do Medico");
        String crm = sc.nextLine();
        System.out.println("Digite a especialidade do Medico");
        String especialidade = sc.nextLine();
        System.out.println("Digite a idade");
        Integer idade = sc.nextInt();
        System.out.println("Digite a valor da consulta do Medico");
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
                    break;
                case 2:
                    medico = new MedicoClinico(nome, crm, especialidade, idade, valorConsulta);
                    break;
                case 3:
                    medico = new MedicoOdontologo(nome, crm, especialidade, idade, valorConsulta);
                    break;
                default:
                    System.out.println("Por favor digite um valor valido");
            }

            // Salva nos dois lugares (memória e disco)
            if (medico != null) {
                clinica.addMedico(medico);           // Salva na memória
                gravacao.salvarNovoMedico(medico);   // Salva no txt
                aux = false;
            }
        }
        System.out.println("✓ Médico cadastrado com sucesso!");
    }

    public void removerMedico(Clinica clinica, Gravacao gravacao){

        System.out.println("Digite o CRM do Medico (CRM/** ******)");
        String crm = sc.nextLine();

        // 1. Remove da lista na memória
        clinica.removeMedico(crm);

        // 2. Remove do arquivo de texto
        gravacao.removerMedicoDoArquivo(crm);

        System.out.println("✓ Médico removido com sucesso!");
    }















}
