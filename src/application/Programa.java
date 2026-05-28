package application;

import entities.*;

import java.util.Scanner;


public class Programa {

    static void main(String[] args) {
        Menu menu = new Menu();
        Scanner sc = new Scanner(System.in);
        Clinica c = new Clinica();
        Inicializacao.inicializaMedicos(c);
        Inicializacao.inicializaPacientes(c);
        int opcao = -1;
        int opcao2 = -1;
        while(opcao != 0){
            Menu.exibirMenu();
            opcao = sc.nextInt();
            switch(opcao){
                case 1:
                    while(opcao2 != 0){
                        Menu.exibirMenuMedicos();
                        opcao2 = sc.nextInt();
                        switch (opcao2) {
                            case 1:
                                menu.cadastrarMedico(c);
                                break;
                            case 2:
                                menu.removerMedico(c);
                                break;
                            case 3:
                                c.getMedicos();
                                break;
                        }
                    }
                    break;
                case 2:
                    while(opcao2 != 0){
                        Menu.exibirMenuPacientes();
                        opcao2 = sc.nextInt();
                        switch (opcao2) {
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
