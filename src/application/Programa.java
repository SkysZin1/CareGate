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
        while(opcao != 0){
            Menu.exibirMenu();
            opcao = sc.nextInt();
            switch(opcao){
                case 1:
                    menu.cadastrarPaciente(c);
                    break;
                case 2:
                    menu.cadastrarMedico(c);
                    break;


                case 5:
                    c.getHistoricoConsulta();
                    break;
                case 6:


                case 7:
                    c.getMedicos();
                    break;

                case 8:
                    c.getPacientes();
                    break;
            }
        }



    }

}
