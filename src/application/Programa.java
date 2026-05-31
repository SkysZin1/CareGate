package application;

import entities.*;
import java.util.Scanner;

public class Programa {

    public static void main(String[] args) {
        Menu menu = new Menu();
        Scanner sc = new Scanner(System.in);
        Clinica c = new Clinica();
        Gravacao g = new Gravacao();

        if (g.arquivoTemDados()) {
            g.carregarMedicos(c);
        } else {
            Inicializacao.inicializarMedicosPadrao(c, g); // Cria os médicos e salva no arquivo
        }

        int opcao = -1;
        while(opcao != 0){
            Menu.exibirMenu();
            opcao = sc.nextInt();
            switch(opcao){
                case 1:
                    int opcao2 = -1; // Reset para o menu de médicos
                    while(opcao2 != 0){
                        Menu.exibirMenuMedicos();
                        opcao2 = sc.nextInt();
                        switch (opcao2) {
                            case 1:
                                menu.cadastrarMedico(c, g);
                                break;
                            case 2:
                                menu.removerMedico(c, g);
                                break;
                            case 3:
                                Menu.limparConsole();
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
                    break;
                case 2:
                    int opcao3 = -1; // Reset para o menu de pacientes
                    while(opcao3 != 0){
                        Menu.exibirMenuPacientes();
                        opcao3 = sc.nextInt();
                        switch (opcao3) {
                            case 1:
                                menu.cadastrarPaciente(c);
                                break;
                            case 2:
                                menu.removerPaciente(c);
                                break;
                            case 3:
                                Menu.limparConsole();
                                c.getPacientes();
                                while(true){
                                    System.out.println("Digite 0 para voltar");
                                    if(sc.nextInt() == 0){
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                    break;

                case 5:
                    Menu.exibirMenuPacientes();
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
}