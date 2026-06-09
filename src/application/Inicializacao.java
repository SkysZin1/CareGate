package application;

import entities.*;
import java.time.LocalDateTime;

public class Inicializacao {

    public static void inicializarMedicosPadrao(Clinica c, Gravacao g) {

        MedicoClinico clinicoBase = new MedicoClinico("", "", "", 0, 200);
        MedicoCirurgiao cirurgiaoBase = new MedicoCirurgiao("", "", "", 0, 400);
        MedicoOdontologo odontologoBase = new MedicoOdontologo("", "", "", 0, 300);

        // Médicos Clínicos
        adicionarESalvarMedico(c, g, new MedicoClinico("Camila", "CRM/BA 123456", "Dermatologia", 36, clinicoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoClinico("Luciana", "CRM/BA 223456", "Endócrinologia", 51, clinicoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoClinico("Leide", "CRM/BA 323456", "Ginecologia", 30, clinicoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoClinico("Bruna", "CRM/BA 423456", "Oftalmologia", 49, clinicoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoClinico("Osvaldo", "CRM/BA 523456", "Geriatria", 67, clinicoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoClinico("Mateus", "CRM/BA 623456", "Urologia", 40, clinicoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoClinico("Erick", "CRM/BA 723456", "Alergologia", 33, clinicoBase.calcularValorConsulta()));

        // Médicos Cirurgiões
        adicionarESalvarMedico(c, g, new MedicoCirurgiao("Wilton", "CRM/BA 133456", "Oncologica", 70, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoCirurgiao("José", "CRM/BA 143456", "Neurocirurgica", 54, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoCirurgiao("Davi", "CRM/BA 153456", "Traumatologica", 64, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoCirurgiao("Brito", "CRM/BA 163456", "Plastica", 47, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoCirurgiao("Antônio", "CRM/BA 173456", "Cardiovascular", 32, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoCirurgiao("Eugênio", "CRM/BA 183456", "Geral", 39, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoCirurgiao("Henrique", "CRM/BA 193456", "Pediatrica", 70, cirurgiaoBase.calcularValorConsulta()));

        // Médicos Odontólogos
        adicionarESalvarMedico(c, g, new MedicoOdontologo("Isaque", "CRM/BA 124456", "Ortodontia", 35, odontologoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoOdontologo("Artur", "CRM/BA 125456", "Implantodontia", 43, odontologoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoOdontologo("Caua", "CRM/BA 126456", "Endodontia", 54, odontologoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoOdontologo("Pedro", "CRM/BA 127456", "Harmonizacao", 28, odontologoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoOdontologo("Maria", "CRM/BA 128456", "Odontopediatria", 27, odontologoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoOdontologo("Eduarda", "CRM/BA 129456", "Periodontia", 62, odontologoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoOdontologo("Luciano", "CRM/BA 120456", "Buco-Maxilo", 44, odontologoBase.calcularValorConsulta()));
    }

    public static void inicializarPacientesPadrao(Clinica c, Gravacao g) {
        adicionarESalvarPaciente(c, g, new Paciente("Ana Silva", "Rua das Flores, 123", "12345678901", "71999990001"));
        adicionarESalvarPaciente(c, g, new Paciente("Bruno Costa", "Av. Brasil, 45", "22345678901", "71999990002"));
        adicionarESalvarPaciente(c, g, new Paciente("Carla Souza", "Rua do Sol, 78", "32345678901", "71999990003"));
        adicionarESalvarPaciente(c, g, new Paciente("Diego Lima", "Trav. das Oliveiras, 12", "42345678901", "71999990004"));
        adicionarESalvarPaciente(c, g, new Paciente("Eva Pereira", "Alameda Central, 9", "52345678901", "71999990005"));
        adicionarESalvarPaciente(c, g, new Paciente("Felipe Rocha", "Rua Nova, 200", "62345678901", "71999990006"));
        adicionarESalvarPaciente(c, g, new Paciente("Gabriela Alves", "Praça da Alegria, 1", "72345678901", "71999990007"));
        adicionarESalvarPaciente(c, g, new Paciente("Hugo Martins", "Av. dos Coqueiros, 77", "82345678901", "71999990008"));
        adicionarESalvarPaciente(c, g, new Paciente("Iara Mendes", "Rua Vitória, 33", "92345678901", "71999990009"));
        adicionarESalvarPaciente(c, g, new Paciente("João Ferreira", "Loteamento Verde, 10", "02345678901", "71999990010"));
    }

    public static void inicializarConsultasPadrao(Clinica c, Gravacao g) {

        // Obtém pacientes e médicos do sistema
        Paciente paciente1 = c.getPacienteByCPF("12345678901");
        Medico medico1 = c.getMedicoByCRM("CRM/BA 123456");

        Paciente paciente2 = c.getPacienteByCPF("22345678901");
        Medico medico2 = c.getMedicoByCRM("CRM/BA 133456");

        Paciente paciente3 = c.getPacienteByCPF("32345678901");
        Medico medico3 = c.getMedicoByCRM("CRM/BA 124456");

        Paciente paciente4 = c.getPacienteByCPF("42345678901");
        Medico medico4 = c.getMedicoByCRM("CRM/BA 223456");

        Paciente paciente5 = c.getPacienteByCPF("52345678901");
        Medico medico5 = c.getMedicoByCRM("CRM/BA 143456");

        if (paciente1 != null && medico1 != null) {
            adicionarESalvarConsulta(c, g, new Consulta(medico1, paciente1, LocalDateTime.of(2026, 7, 5, 10, 0), "Consulta de rotina", 1));
        }

        if (paciente2 != null && medico2 != null) {
            adicionarESalvarConsulta(c, g, new Consulta(medico2, paciente2, LocalDateTime.of(2026, 7, 7, 14, 30), "Avaliação pré-cirúrgica", 2));
        }

        if (paciente3 != null && medico3 != null) {
            adicionarESalvarConsulta(c, g, new Consulta(medico3, paciente3, LocalDateTime.of(2026, 7, 10, 9, 0), "Limpeza dental", 3));
        }

        if (paciente4 != null && medico4 != null) {
            adicionarESalvarConsulta(c, g, new Consulta(medico4, paciente4, LocalDateTime.of(2026, 7, 12, 15, 0), "Avaliação endócrina", 4));
        }

        if (paciente5 != null && medico5 != null) {
            adicionarESalvarConsulta(c, g, new Consulta(medico5, paciente5, LocalDateTime.of(2026, 7, 15, 11, 30), "Avaliação neurológica", 5));
        }
    }

    // Métod0 para garantir que o paciente vá tanto para a lista na memória quanto para o arquivo TXT
    private static void adicionarESalvarPaciente(Clinica c, Gravacao g, Paciente p) {
        c.addPaciente(p);
        g.salvarNovoPaciente(p);
    }

    // Métod0 para garantir que o médico vá tanto para a lista na memória quanto para o arquivo TXT
    private static void adicionarESalvarMedico(Clinica c, Gravacao g, Medico m) {
        c.addMedico(m);
        g.salvarNovoMedico(m);
    }

    // Métod0 para garantir que a consulta vá tanto para a lista na memória quanto para o arquivo TXT
    private static void adicionarESalvarConsulta(Clinica c, Gravacao g, Consulta consulta) {
        c.addConsulta(consulta);
        g.salvarNovaConsulta(consulta);
    }

    // Verifica se os arquivos ja possuem dados, se não, inicializa os dados padrão
    public static void Inicializar(Clinica c, Gravacao g) {
        if (g.arquivoTemDados()) {
            g.carregarMedicos(c);
        } else {
            inicializarMedicosPadrao(c, g); // Cria os médicos e salva no arquivo
        }

        // Pacientes: carregar se existir arquivo, senão inicializar com pacientes padrão
        if (g.arquivoTemDadosPacientes()) {
            g.carregarPacientes(c);
        } else {
            inicializarPacientesPadrao(c, g);
        }

        // Consultas: carregar se existir arquivo, senão inicializar com consultas padrão
        if (g.arquivoTemDadosConsultas()) {
            g.carregarConsultas(c);
        } else {
            inicializarConsultasPadrao(c, g);
        }
    }
}

