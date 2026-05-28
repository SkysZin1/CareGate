// Criado por Daniel e Gustavo (26/05/2026)

package application;

import entities.*;

public class Inicializacao {

    public static void inicializaMedicos(Clinica c) {
        // Métod0 estático que gera e retorna a lista com 21 médicos

        Medico clinicoBase = new MedicoClinico();
        Medico cirurgiaoBase = new MedicoCirurgiao();
        Medico odontologoBase = new MedicoOdontologo();

        c.addMedico(new MedicoClinico("Camila", "CRM/BA 123456", "Dermatologia", 36, clinicoBase.calcularValorConsulta()));
        c.addMedico(new MedicoClinico("Luciana", "CRM/BA 223456", "Endócrinologia", 51, clinicoBase.calcularValorConsulta()));
        c.addMedico(new MedicoClinico("Leide", "CRM/BA 323456", "Ginecologia", 30, clinicoBase.calcularValorConsulta()));
        c.addMedico(new MedicoClinico("Bruna", "CRM/BA 423456", "Oftalmologia", 49, clinicoBase.calcularValorConsulta()));
        c.addMedico(new MedicoClinico("Osvaldo", "CRM/BA 523456", "Geriatria", 67, clinicoBase.calcularValorConsulta()));
        c.addMedico(new MedicoClinico("Mateus", "CRM/BA 623456", "Urologia", 40, clinicoBase.calcularValorConsulta()));
        c.addMedico(new MedicoClinico("Erick", "CRM/BA 723456", "Alergologia", 33, clinicoBase.calcularValorConsulta()));

        c.addMedico(new MedicoCirurgiao("Wilton", "CRM/BA 133456", "Oncologica", 70, cirurgiaoBase.calcularValorConsulta()));
        c.addMedico(new MedicoCirurgiao("José", "CRM/BA 143456", "Neurocirurgica", 54, cirurgiaoBase.calcularValorConsulta()));
        c.addMedico(new MedicoCirurgiao("Davi", "CRM/BA 153456", "Traumatologica", 64, cirurgiaoBase.calcularValorConsulta()));
        c.addMedico(new MedicoCirurgiao("Brito", "CRM/BA 163456", "Plastica", 47, cirurgiaoBase.calcularValorConsulta()));
        c.addMedico(new MedicoCirurgiao("Antônio", "CRM/BA 173456", "Cardiovascular", 32, cirurgiaoBase.calcularValorConsulta()));
        c.addMedico(new MedicoCirurgiao("Eugênio", "CRM/BA 183456", "Geral", 39, cirurgiaoBase.calcularValorConsulta()));
        c.addMedico(new MedicoCirurgiao("Henrique", "CRM/BA 193456", "Pediatrica", 70, cirurgiaoBase.calcularValorConsulta()));

        c.addMedico(new MedicoOdontologo("Isaque", "CRM/BA 124456", "Ortodontia", 35, odontologoBase.calcularValorConsulta()));
        c.addMedico(new MedicoOdontologo("Artur", "CRM/BA 125456", "Implantodontia", 43, odontologoBase.calcularValorConsulta()));
        c.addMedico(new MedicoOdontologo("Caua", "CRM/BA 126456", "Endodontia", 54, odontologoBase.calcularValorConsulta()));
        c.addMedico(new MedicoOdontologo("Pedro", "CRM/BA 127456", "Harmonizacao", 28, odontologoBase.calcularValorConsulta()));
        c.addMedico(new MedicoOdontologo("Maria", "CRM/BA 128456", "Odontopediatria", 27, odontologoBase.calcularValorConsulta()));
        c.addMedico(new MedicoOdontologo("Eduarda", "CRM/BA 129456", "Periodontia", 62, odontologoBase.calcularValorConsulta()));
        c.addMedico(new MedicoOdontologo("Luciano", "CRM/BA 120456", "Buco-Maxilo-Facial", 44, odontologoBase.calcularValorConsulta()));
    }

    public static void inicializaPacientes(Clinica c) {
        c.addPaciente(new Paciente("Arduíno", "Rua Casa Nossa, 1404", "123.456.789-01", "71 99876-5432"));
        c.addPaciente(new Paciente("Uelintão", "Rua Madre Mia, 20", "123.456.789-02", "71 99876-5433"));
        c.addPaciente(new Paciente("Jairzinho", "Travessa Jair Ventura, 52", "123.456.789-03", "71 99876-5434"));
        c.addPaciente(new Paciente("Feijão", "Rua Fonte Nossa, 10", "123.456.789-04", "71 99876-5435"));
        c.addPaciente(new Paciente("Fernando Neto", "Alameda dos Nervos, 74", "123.456.789-05", "71 99876-5436"));
        c.addPaciente(new Paciente("Artêmio", "Rua Menino Rildo, 61", "123.456.789-06", "71 99876-5437"));
        c.addPaciente(new Paciente("Manoel Gomes", "Rua Canto dos Gremistas, 5", "123.456.789-07", "71 99876-5438"));
    }
}
