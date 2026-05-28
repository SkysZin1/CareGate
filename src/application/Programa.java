package application;

import entities.*;

import java.util.Scanner;


public class Programa {

    static void main(String[] args) {
        Menu menu = new Menu();
        Scanner sc = new Scanner(System.in);
        Clinica c = new Clinica();
        Inicializacao.inicializaMedicos(c);
        int opcao = -1;
        while(opcao != 0){  // Releitura do menu necessaria
            Menu.exibirMenu();
            opcao = sc.nextInt();
            switch(opcao){
                case 1:
                    Menu.exibirMenuMedicos();
                    break;
                case 2:
                    Menu.exibirMenuPacientes();
                    break;


                case 5:
                    c.getHistoricoConsulta();
                    break;
            }
        }



    }

}
