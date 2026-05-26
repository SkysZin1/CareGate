package entities;

import java.util.ArrayList;

public class MedicoInicializador {
    private static Medico clinicoBase = new MedicoClinico();
    private static Medico cirurgiaoBase = new MedicoCirurgiao();
    private static Medico odontologoBase = new MedicoOdontologo();
    
    // Métod0 estático que gera e retorna a lista com 21 médicos
    public static ArrayList<Medico> gerarMedicosIniciais() {
        ArrayList<Medico> medicosIniciais = new ArrayList<>();

        medicosIniciais.add(new MedicoClinico("Camila", "CRM/BA 123456", "Dermatologia", 36, clinicoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoClinico("Luciana", "CRM/BA 223456", "Endócrinologia", 51, clinicoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoClinico("Leide", "CRM/BA 323456", "Ginecologia", 30, clinicoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoClinico("Bruna", "CRM/BA 423456", "Oftalmologia", 49, clinicoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoClinico("Osvaldo", "CRM/BA 523456", "Geriatria", 67, clinicoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoClinico("Mateus", "CRM/BA 623456", "Urologia", 40, clinicoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoClinico("Erick", "CRM/BA 723456", "Alergologia", 33, clinicoBase.calcularValorConsulta()));
        
        medicosIniciais.add(new MedicoCirurgiao("Wilton", "CRM/BA 133456", "Oncologica", 70, cirurgiaoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoCirurgiao("José", "CRM/BA 143456", "Neurocirurgica", 54, cirurgiaoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoCirurgiao("Davi", "CRM/BA 153456", "Traumatologica", 64, cirurgiaoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoCirurgiao("Brito", "CRM/BA 163456", "Plastica", 47, cirurgiaoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoCirurgiao("Antônio", "CRM/BA 173456", "Cardiovascular", 32, cirurgiaoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoCirurgiao("Eugênio", "CRM/BA 183456", "Geral", 39, cirurgiaoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoCirurgiao("Henrique", "CRM/BA 193456", "Pediatrica", 70, cirurgiaoBase.calcularValorConsulta()));
        
        medicosIniciais.add(new MedicoOdontologo("Isaque", "CRM/BA 124456", "Ortodontia", 35, odontologoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoOdontologo("Artur", "CRM/BA 125456", "Implantodontia", 43, odontologoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoOdontologo("Caua", "CRM/BA 126456", "Endodontia", 54, odontologoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoOdontologo("Pedro", "CRM/BA 127456", "Harmonizacao", 28, odontologoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoOdontologo("Maria", "CRM/BA 128456", "Odontopediatria", 27, odontologoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoOdontologo("Eduarda", "CRM/BA 129456", "Periodontia", 62, odontologoBase.calcularValorConsulta()));
        medicosIniciais.add(new MedicoOdontologo("Luciano", "CRM/BA 120456", "Buco-Maxilo-Facial", 44, odontologoBase.calcularValorConsulta()));
        
        return medicosIniciais;
    }
}