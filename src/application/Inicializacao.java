package application;

import entities.*;

public class Inicializacao {

    public static void inicializarMedicosPadrao(Clinica c, Gravacao g) {

        MedicoClinico clinicoBase = new MedicoClinico("", "", "", 0, 200);
        MedicoCirurgiao cirurgiaoBase = new MedicoCirurgiao("", "", "", 0, 400);
        MedicoOdontologo odontologoBase = new MedicoOdontologo("", "", "", 0, 300);

        // Médicos Clínicos
        adicionarESalvar(c, g, new MedicoClinico("Camila", "CRM/BA 123456", "Dermatologia", 36, clinicoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoClinico("Luciana", "CRM/BA 223456", "Endócrinologia", 51, clinicoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoClinico("Leide", "CRM/BA 323456", "Ginecologia", 30, clinicoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoClinico("Bruna", "CRM/BA 423456", "Oftalmologia", 49, clinicoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoClinico("Osvaldo", "CRM/BA 523456", "Geriatria", 67, clinicoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoClinico("Mateus", "CRM/BA 623456", "Urologia", 40, clinicoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoClinico("Erick", "CRM/BA 723456", "Alergologia", 33, clinicoBase.calcularValorConsulta()));

        // Médicos Cirurgiões
        adicionarESalvar(c, g, new MedicoCirurgiao("Wilton", "CRM/BA 133456", "Oncologica", 70, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoCirurgiao("José", "CRM/BA 143456", "Neurocirurgica", 54, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoCirurgiao("Davi", "CRM/BA 153456", "Traumatologica", 64, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoCirurgiao("Brito", "CRM/BA 163456", "Plastica", 47, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoCirurgiao("Antônio", "CRM/BA 173456", "Cardiovascular", 32, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoCirurgiao("Eugênio", "CRM/BA 183456", "Geral", 39, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoCirurgiao("Henrique", "CRM/BA 193456", "Pediatrica", 70, cirurgiaoBase.calcularValorConsulta()));

        // Médicos Odontólogos
        adicionarESalvar(c, g, new MedicoOdontologo("Isaque", "CRM/BA 124456", "Ortodontia", 35, odontologoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoOdontologo("Artur", "CRM/BA 125456", "Implantodontia", 43, odontologoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoOdontologo("Caua", "CRM/BA 126456", "Endodontia", 54, odontologoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoOdontologo("Pedro", "CRM/BA 127456", "Harmonizacao", 28, odontologoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoOdontologo("Maria", "CRM/BA 128456", "Odontopediatria", 27, odontologoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoOdontologo("Eduarda", "CRM/BA 129456", "Periodontia", 62, odontologoBase.calcularValorConsulta()));
        adicionarESalvar(c, g, new MedicoOdontologo("Luciano", "CRM/BA 120456", "Buco-Maxilo", 44, odontologoBase.calcularValorConsulta()));
    }

    // Métod0 para garantir que o médico vá tanto para a lista na memória quanto para o arquivo TXT
    private static void adicionarESalvar(Clinica c, Gravacao g, Medico m) {
        c.addMedico(m);
        g.salvarNovoMedico(m);
    }
}