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

    // Mét0do para garantir que a consulta vá para a lista em memória e seja persistida no arquivo
    private static void adicionaESalvarConsulta(Clinica c, Gravacao g, Consulta consulta) {
        // Se o ID não estiver definido (<= 0), pega o próximo ID disponível no arquivo
        if (consulta.getIdConsulta() <= 0) {
            int novoId = g.getIdUltimaConsulta() + 1;
            consulta.setIdConsulta(novoId);
        }

        c.addConsulta(consulta);
        g.salvarNovaConsulta(consulta);
    }

    // Inicializa consultas pre-definidas usando medicos e pacientes ja existentes
    public static void inicializarConsultasPadrao(Clinica c, Gravacao g) {

        try {
            Paciente p1 = c.getPacienteByCPF("12345678901");
            Paciente p2 = c.getPacienteByCPF("22345678901");
            Paciente p3 = c.getPacienteByCPF("32345678901");
            Paciente p4 = c.getPacienteByCPF("42345678901");
            Paciente p5 = c.getPacienteByCPF("52345678901");
            Paciente p6 = c.getPacienteByCPF("62345678901");
            Paciente p7 = c.getPacienteByCPF("72345678901");
            Paciente p8 = c.getPacienteByCPF("82345678901");
            Paciente p9 = c.getPacienteByCPF("92345678901");
            Paciente p10 = c.getPacienteByCPF("02345678901");

            Medico m1 = c.getMedicoByCRM("CRM/BA 123456");
            Medico m2 = c.getMedicoByCRM("CRM/BA 223456");
            Medico m3 = c.getMedicoByCRM("CRM/BA 323456");
            Medico m4 = c.getMedicoByCRM("CRM/BA 423456");
            Medico m5 = c.getMedicoByCRM("CRM/BA 523456");
            Medico m8 = c.getMedicoByCRM("CRM/BA 133456");
            Medico m9 = c.getMedicoByCRM("CRM/BA 124456");
            Medico m10 = c.getMedicoByCRM("CRM/BA 125456");

            int anoAtual = LocalDateTime.now().getYear();

            // Se algum for nulo, nao tenta criar a consulta correspondente
            if (m1 != null && p1 != null) adicionaESalvarConsulta(c, g, new Consulta(m1, p1, LocalDateTime.of(anoAtual, 1, 10, 0, 0), "Dermatite", 0));
            if (m2 != null && p2 != null) adicionaESalvarConsulta(c, g, new Consulta(m2, p2, LocalDateTime.of(anoAtual, 2, 12, 0, 0), "Avaliacao endocrinologica", 0));
            if (m3 != null && p3 != null) adicionaESalvarConsulta(c, g, new Consulta(m3, p3, LocalDateTime.of(anoAtual, 3, 5, 0, 0), "Consulta ginecologica", 0));
            if (m4 != null && p4 != null) adicionaESalvarConsulta(c, g, new Consulta(m4, p4, LocalDateTime.of(anoAtual, 4, 20, 0, 0), "Revisao oftalmologica", 0));
            if (m5 != null && p5 != null) adicionaESalvarConsulta(c, g, new Consulta(m5, p5, LocalDateTime.of(anoAtual, 5, 14, 0, 0), "Avaliacao geriatrica", 0));
            if (m1 != null && p6 != null) adicionaESalvarConsulta(c, g, new Consulta(m1, p6, LocalDateTime.of(anoAtual, 6, 25, 0, 0), "Dermatologia - retorno", 0));
            if (m2 != null && p7 != null) adicionaESalvarConsulta(c, g, new Consulta(m2, p7, LocalDateTime.of(anoAtual, 7, 9, 0, 0), "Endocrinologia - acompanhamento", 0));
            if (m8 != null && p8 != null) adicionaESalvarConsulta(c, g, new Consulta(m8, p8, LocalDateTime.of(anoAtual, 8, 22, 0, 0), "Cirurgia avaliativa", 0));
            if (m9 != null && p9 != null) adicionaESalvarConsulta(c, g, new Consulta(m9, p9, LocalDateTime.of(anoAtual, 9, 2, 0, 0), "Ortodontia - primeira consulta", 0));
            if (m10 != null && p10 != null) adicionaESalvarConsulta(c, g, new Consulta(m10, p10, LocalDateTime.of(anoAtual, 10, 30, 0, 0), "Implante - avaliacao", 0));
            if (m5 != null && p3 != null) adicionaESalvarConsulta(c, g, new Consulta(m5, p3, LocalDateTime.of(anoAtual, 11, 18, 0, 0), "Geriatria - retorno", 0));
            if (m8 != null && p4 != null) adicionaESalvarConsulta(c, g, new Consulta(m8, p4, LocalDateTime.of(anoAtual, 12, 5, 0, 0), "Revisao cirurgica", 0));

        } catch (Exception e) {
            System.out.println("Erro ao inicializar consultas padrão: " + e.getMessage());
        }
    }
    public static void inicializarPacientesPadrao(Clinica c, Gravacao g) {
        adicionarESalvarPaciente(c, g, new Paciente("Ana Silva", "Rua das Flores 123", "12345678901", "71999990001"));
        adicionarESalvarPaciente(c, g, new Paciente("Bruno Costa", "Av. Brasil 45", "22345678901", "71999990002"));
        adicionarESalvarPaciente(c, g, new Paciente("Carla Souza", "Rua do Sol 78", "32345678901", "71999990003"));
        adicionarESalvarPaciente(c, g, new Paciente("Diego Lima", "Trav. das Oliveiras 12", "42345678901", "71999990004"));
        adicionarESalvarPaciente(c, g, new Paciente("Eva Pereira", "Alameda Central 9", "52345678901", "71999990005"));
        adicionarESalvarPaciente(c, g, new Paciente("Felipe Rocha", "Rua Nova 200", "62345678901", "71999990006"));
        adicionarESalvarPaciente(c, g, new Paciente("Gabriela Alves", "Praça da Alegria 1", "72345678901", "71999990007"));
        adicionarESalvarPaciente(c, g, new Paciente("Hugo Martins", "Av. dos Coqueiros 77", "82345678901", "71999990008"));
        adicionarESalvarPaciente(c, g, new Paciente("Iara Mendes", "Rua Vitória 33", "92345678901", "71999990009"));
        adicionarESalvarPaciente(c, g, new Paciente("João Ferreira", "Loteamento Verde 10", "02345678901", "71999990010"));
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

