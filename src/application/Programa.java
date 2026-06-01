package application;

import entities.*;
import java.util.Scanner;

public class Programa {

    public static void main(String[] args) {
        Menu menu = new Menu();
        Scanner sc = new Scanner(System.in);
        Clinica c = new Clinica();
        Gravacao g = new Gravacao();

        Inicializacao.Inicializar(c, g); // Inicializa os dados padrão

        int opcao = -1;
        while(opcao != 0){
            Menu.exibirMenu();
            opcao = sc.nextInt();
            sc.nextLine(); // limpa o \n pendente
            switch(opcao){
                case 1:
                    Menu.exibirMenuMedicos(c, g, sc, menu);
                    break;
                case 2:
                    Menu.exibirMenuPacientes(c, g, sc, menu);
                    break;
                case 5:
                    c.getHistoricoConsulta();
                    while(true){
                        System.out.println("Digite 0 para voltar");
                        if(sc.nextInt() == 0){
                            break;
                        }
                    }
            }
        }
    }
}