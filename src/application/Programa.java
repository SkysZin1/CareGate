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
                    int opcao2 = -1; // Reseta o menu secundário
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
                                c.getMedicos();
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
                                c.getPacientes();
                                break;
                        }
                    }
                    break;

                case 5:
                    c.getHistoricoConsulta();
                    break;
            }
        }
    }
}