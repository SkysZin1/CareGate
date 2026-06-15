// Feito por Daniel (25/05/2026)

package application;

import entities.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {
    Scanner sc = new Scanner(System.in);
    public static void exibirMenu(){
            limparConsole();
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║ CareGate - SISTEMA DE GESTÃO DE CLÍNICA║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\n[1] Gerenciar Médicos");
            System.out.println("[2] Gerenciar Pacientes");
            System.out.println("[3] Gerenciar Consultas");
            System.out.println("[4] Gerar Relatório Financeiro");
            System.out.println("[0] Sair\n");
    }

    public static void exibirMenuMedicos(Clinica c, Gravacao g, Scanner sc, Menu menu) {
        int opcao = -1;
        while (opcao != 0) {
            limparConsole();
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║         GERENCIAR MÉDICOS              ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\n[1] Adicionar Médico");
            System.out.println("[2] Remover Médico");
            System.out.println("[3] Listar Médicos");
            System.out.println("[0] Voltar\n");
            opcao = sc.nextInt();
            sc.nextLine(); // limpa o \n pendente
            switch(opcao){
                case 1:
                    menu.cadastrarMedico(c, g);
                    break;
                case 2:
                    menu.removerMedico(c, g);
                    break;
                case 3:
                    limparConsole();
                    c.getMedicos();
                    while(true){
                        System.out.println("Digite 0 para voltar");
                        if(sc.nextInt() == 0){
                            break;
                        }
                    }
                    break;
            }
        }

    }

    public static void exibirMenuPacientes(Clinica c, Gravacao g, Scanner sc, Menu menu) {
        int opcao = -1;
        while (opcao != 0) {
            limparConsole();
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║         GERENCIAR PACIENTES            ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\n[1] Adicionar Paciente");
            System.out.println("[2] Remover Paciente");
            System.out.println("[3] Listar Pacientes");
            System.out.println("[0] Voltar\n");
            opcao = sc.nextInt();
            sc.nextLine(); // limpa o \n pendente
            switch (opcao) {
                case 1:
                    menu.cadastrarPaciente(c, g);
                    break;
                case 2:
                    menu.removerPaciente(c, g);
                    break;
                case 3:
                    Menu.limparConsole();
                    c.getPacientes();
                    while(true){
                         System.out.println("Digite 0 para voltar, ou digite um CPF especifico para ver as consultas do paciente");
                         String aux = sc.nextLine().trim();
                         if(aux.equals("0")) break;
                         Paciente paciente = c.getPacienteByCPF(aux);
                        if(paciente == null){
                            System.out.println("Paciente não encontrado!");
                            Menu.esperar(2000);
                            continue;
                        }
                        paciente.getHistoricoConsulta();
                        do{
                            System.out.println("Digite 0 para voltar");
                        }while(sc.nextInt() != 0);
                        sc.nextLine();
                    }
                    break;
            }
        }
    }

    public static void exibirMenuConsultas(Clinica c, Gravacao g, Scanner sc, Menu menu) {
        int opcao = -1;
        while (opcao != 0) {
            limparConsole();
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║         GERENCIAR CONSULTAS            ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\n[1] Agendar Consulta");
            System.out.println("[2] Cancelar Consulta");
            System.out.println("[3] Listar Consultas");
            System.out.println("[0] Voltar\n");
            opcao = sc.nextInt();
            sc.nextLine(); // limpa o \n pendente
            switch(opcao){
                case 1:
                    menu.agendarConsulta(c, sc, g);
                    break;
                case 2:
                    menu.removerConsulta(c, sc, g);
                    break;
                case 3:
                    limparConsole();
                    c.getHistoricoConsulta();
                    while(true){
                        System.out.println("Digite 0 para voltar");
                        if(sc.nextInt() == 0){
                            break;
                        }
                    }
                    break;
            }
        }

    }

    public void cadastrarPaciente(Clinica clinica, Gravacao gravacao){
        limparConsole();
        System.out.println("Digite o nome do Paciente");
        String nome = sc.nextLine();
        String regex = "\\d{11}";
        String cpf = null;
        do{  // usa um padrão esperado para verificar se o cpf é válido
            System.out.println("Digite um CPF válido (Sem . )");
            cpf = sc.nextLine().trim();
        }while(!Pattern.matches(regex, cpf));

        System.out.println("Digite o telefone do Paciente");
        String telefone = sc.nextLine();
        System.out.println("Digite o endereco do Paciente");
        String endereco = sc.nextLine();
        Paciente paciente = new Paciente(nome, endereco, cpf, telefone);
        clinica.addPaciente(paciente);
        gravacao.salvarNovoPaciente(paciente);
        System.out.println("Paciente cadastrado com sucesso!");
        esperar(2000); // Tempo para o usuario ver a mensagem de sucesso

    }

    public void removerPaciente(Clinica clinica, Gravacao gravacao){
        limparConsole();
        System.out.println("Digite o CPF do Paciente");
        String cpf = sc.nextLine();
        clinica.removePaciente(cpf);
        gravacao.removerPacienteDoArquivo(cpf);
        System.out.println("Paciente removido com sucesso!");
        esperar(2000); // Tempo para o usuario ver a mensagem de sucesso

    }

    public void cadastrarMedico(Clinica clinica, Gravacao gravacao){
        limparConsole();
        System.out.println("Digite o nome do Medico");
        String nome = sc.nextLine();
        String regex = "CRM/[A-Z]{2}\\s\\d{4,6}";
        String crm = null;
        do{ // usa um padrão esperado para verificar se o crm é valido
            System.out.println("Digite um CRM válido");
            crm = sc.nextLine().trim();
        }while(!Pattern.matches(regex, crm));

        System.out.println("Digite a especialidade do Medico");
        String especialidade = sc.nextLine();
        System.out.println("Digite a idade");
        Integer idade = sc.nextInt();
        System.out.println("Digite a valor da consulta do Medico");
        Integer valorConsulta = sc.nextInt();

        System.out.println("Qual o tipo de consulta do Medico?");
        while (true){
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
                break;
            }
        }
        System.out.println("Médico cadastrado com sucesso!");
        esperar(2000); // Tempo para o usuario ver a mensagem de sucesso

    }

    public void removerMedico(Clinica clinica, Gravacao gravacao){
        limparConsole();
        String regex = "CRM/[A-Z]{2}\\s\\d{4,6}";
        String crm = null;
        do{ // usa um padrão esperado para verificar se o crm é valido
            System.out.println("Digite um CRM válido");
            crm = sc.nextLine();
        }while(!Pattern.matches(regex, crm));

        // 1. Remove da lista na memória
        clinica.removeMedico(crm);

        // 2. Remove do arquivo de texto
        gravacao.removerMedicoDoArquivo(crm);

        System.out.println("Médico removido com sucesso!");
        esperar(2000); // Tempo para o usuario ver a mensagem de sucesso

    }

    public void agendarConsulta(Clinica clinica, Scanner sc, Gravacao gravacao){
        limparConsole();
        System.out.println("Digite o CPF do Paciente");
        String cpf = sc.nextLine().trim();

        // Validar CPF com regex
        String regexCpf = "\\d{11}";
        if (!Pattern.matches(regexCpf, cpf)) {
            System.out.println("CPF inválido! Digite um CPF com 11 dígitos (sem pontos).");
            esperar(2000);
            return;
        }

        System.out.println("Digite o CRM do Medico");
        String crm = sc.nextLine().trim();

        // Validar CRM com regex
        String regexCrm = "CRM/[A-Z]{2}\\s\\d{4,6}";
        if (!Pattern.matches(regexCrm, crm)) {
            System.out.println("CRM inválido! Formato esperado: CRM/XX XXXXXX");
            esperar(2000);
            return;
        }

        Medico medico = clinica.getMedicoByCRM(crm);
        if (medico == null) {
            System.out.println("Médico não encontrado!");
            esperar(2000);
            return;
        }
        // Verifica se o paciente existe antes de tentar agendar
        Paciente paciente = clinica.getPacienteByCPF(cpf);
        if (paciente == null) {
            System.out.println("Paciente não encontrado!");
            esperar(2000);
            return;
        }
        System.out.println("Digite a data da consulta (dd/MM/yyyy)");
        String data = sc.nextLine().trim();

        int id = gravacao.getIdUltimaConsulta() + 1; // Gera um ID único para a nova consulta
        Consulta consulta = clinica.agendarConsulta(cpf, crm, data, id);
        if (consulta == null) {
            System.out.println("Falha ao agendar a consulta. Verifique CPF, CRM e formato da data (dd/MM/yyyy).");
            esperar(2000);
            return;
        }
        paciente.addConsulta(consulta);
        gravacao.salvarNovaConsulta(consulta);
        System.out.println("Consulta agendada com sucesso!");
        esperar(2000); // Tempo para o usuario ver a mensagem de sucesso
    }

    public void removerConsulta(Clinica clinica, Scanner sc, Gravacao gravacao){
        limparConsole();
        System.out.println("Digite o ID da Consulta");
        int idConsulta = sc.nextInt();
        Paciente paciente = clinica.getConsultaByIdConsulta(idConsulta).getPaciente();
        paciente.removeConsulta(idConsulta);
        clinica.removeConsulta(idConsulta);
        gravacao.removerConsultaDoArquivo(idConsulta);
        System.out.println("Consulta cancelada com sucesso!");
        esperar(2000); // Tempo para o usuario ver a mensagem de sucesso
    }

    public void exibirFinancas(Clinica clinica) {
        limparConsole();

        RelatorioFinanceiro relatorio = new RelatorioFinanceiro();

        // Percorre todas as consultas já agendadas na clínica
        for (Consulta consulta : clinica.getListaConsultas()) {
            Medico medico = consulta.getMedico();
            relatorio.registrarConsulta(medico, 1);
        }

        relatorio.gerarRelatorio();

        while (true) {
            System.out.println("\nDigite 0 para voltar");
            if (sc.nextInt() == 0) break;
        }
    }
    public static void limparConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static void esperar(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

















}
