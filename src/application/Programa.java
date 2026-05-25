package application;

import entities.*;
import application.FuncoesMenu;
import java.util.ArrayList;
import java.util.Scanner;

public class Programa {

    static void main(String[] args) {
        FuncoesMenu menu = new FuncoesMenu();
        Scanner sc = new Scanner(System.in);
        Clinica c = new Clinica();
        int opcao = -1;
        while(opcao != 0){
            menu.exibirMenu();
            opcao = sc.nextInt();
            switch(opcao){
                case 1:
                    menu.cadastrarPaciente(c);
                    break;
                case 2:
                    // Criar a função no menu e chamar aqui
            }
        }



    }
}
