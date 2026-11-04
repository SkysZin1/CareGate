package application;

import entities.*;

import java.time.LocalDateTime;

public class Inicializacao {

    public static void inicializarMedicosPadrao(Clinica c, Gravacao g) {
        MedicoClinico clinicoBase = new MedicoClinico("", "", "", 0, 200);
        MedicoCirurgiao cirurgiaoBase = new MedicoCirurgiao("", "", "", 0, 400);
        MedicoOdontologo odontologoBase = new MedicoOdontologo("", "", "", 0, 300);

        adicionarESalvarMedico(c, g, new MedicoClinico("Camila", "CRM/BA 123456", "Dermatologia", 36, clinicoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoClinico("Luciana", "CRM/BA 223456", "Endocrinologia", 51, clinicoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoClinico("Leide", "CRM/BA 323456", "Ginecologia", 30, clinicoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoClinico("Bruna", "CRM/BA 423456", "Oftalmologia", 49, clinicoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoClinico("Osvaldo", "CRM/BA 523456", "Geriatria", 67, clinicoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoClinico("Mateus", "CRM/BA 623456", "Urologia", 40, clinicoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoClinico("Erick", "CRM/BA 723456", "Alergologia", 33, clinicoBase.calcularValorConsulta()));

        adicionarESalvarMedico(c, g, new MedicoCirurgiao("Wilton", "CRM/BA 133456", "Oncologica", 70, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoCirurgiao("Jose", "CRM/BA 143456", "Neurocirurgica", 54, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoCirurgiao("Davi", "CRM/BA 153456", "Traumatologica", 64, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoCirurgiao("Brito", "CRM/BA 163456", "Plastica", 47, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoCirurgiao("Antonio", "CRM/BA 173456", "Cardiovascular", 32, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoCirurgiao("Eugenio", "CRM/BA 183456", "Geral", 39, cirurgiaoBase.calcularValorConsulta()));
        adicionarESalvarMedico(c, g, new MedicoCirurgiao("Henrique", "CRM/BA 193456", "Pediatrica", 70, cirurgiaoBase.calcularValorConsulta()));

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
        adicionarESalvarPaciente(c, g, new Paciente("Gabriela Alves", "Praca da Alegria, 1", "72345678901", "71999990007"));
        adicionarESalvarPaciente(c, g, new Paciente("Hugo Martins", "Av. dos Coqueiros, 77", "82345678901", "71999990008"));
        adicionarESalvarPaciente(c, g, new Paciente("Iara Mendes", "Rua Vitoria, 33", "92345678901", "71999990009"));
        adicionarESalvarPaciente(c, g, new Paciente("Joao Ferreira", "Loteamento Verde, 10", "02345678901", "71999990010"));
    }

    public static void inicializarConsultasPadrao(Clinica c, Gravacao g) {
        adicionarConsultaSeAusente(c, g, 1, "12345678901", "CRM/BA 123456", LocalDateTime.of(2026, 7, 5, 10, 0), "Consulta de rotina");
        adicionarConsultaSeAusente(c, g, 2, "22345678901", "CRM/BA 133456", LocalDateTime.of(2026, 7, 7, 14, 30), "Avaliacao pre-cirurgica");
        adicionarConsultaSeAusente(c, g, 3, "32345678901", "CRM/BA 124456", LocalDateTime.of(2026, 7, 10, 9, 0), "Limpeza dental");
        adicionarConsultaSeAusente(c, g, 4, "42345678901", "CRM/BA 223456", LocalDateTime.of(2026, 7, 12, 15, 0), "Avaliacao endocrina");
        adicionarConsultaSeAusente(c, g, 5, "52345678901", "CRM/BA 143456", LocalDateTime.of(2026, 7, 15, 11, 30), "Avaliacao neurologica");
    }

    public static void enriquecerBancoDeDados(Clinica c, Gravacao g) {
        adicionarPacienteSeAusente(c, g, new Paciente("Mariana Oliveira", "Rua Atlantica, 88", "10345678901", "71999991001"));
        adicionarPacienteSeAusente(c, g, new Paciente("Rafael Santos", "Av. Paralela, 1500", "11345678901", "71999991002"));
        adicionarPacienteSeAusente(c, g, new Paciente("Beatriz Almeida", "Rua dos Lirios, 42", "12345078901", "71999991003"));
        adicionarPacienteSeAusente(c, g, new Paciente("Lucas Nascimento", "Travessa Sao Jorge, 19", "13345678901", "71999991004"));
        adicionarPacienteSeAusente(c, g, new Paciente("Patricia Gomes", "Rua Bahia, 321", "14345678901", "71999991005"));
        adicionarPacienteSeAusente(c, g, new Paciente("Renata Barros", "Av. Sete de Setembro, 700", "15345678901", "71999991006"));
        adicionarPacienteSeAusente(c, g, new Paciente("Thiago Ribeiro", "Rua Primavera, 56", "16345678901", "71999991007"));
        adicionarPacienteSeAusente(c, g, new Paciente("Larissa Teixeira", "Rua das Acacias, 91", "17345678901", "71999991008"));
        adicionarPacienteSeAusente(c, g, new Paciente("Gustavo Rocha", "Alameda Rio Vermelho, 204", "18345678901", "71999991009"));
        adicionarPacienteSeAusente(c, g, new Paciente("Helena Carvalho", "Rua do Comercio, 17", "19345678901", "71999991010"));
        adicionarPacienteSeAusente(c, g, new Paciente("Vitor Azevedo", "Av. Oceano, 440", "20345678901", "71999991011"));
        adicionarPacienteSeAusente(c, g, new Paciente("Sofia Matos", "Rua das Palmeiras, 12", "21345678901", "71999991012"));
        adicionarPacienteSeAusente(c, g, new Paciente("Caio Andrade", "Rua da Esperanca, 230", "23345678901", "71999991013"));
        adicionarPacienteSeAusente(c, g, new Paciente("Amanda Farias", "Condominio Bela Vista, 31", "24345678901", "71999991014"));
        adicionarPacienteSeAusente(c, g, new Paciente("Eduardo Pires", "Av. Tancredo Neves, 980", "25345678901", "71999991015"));

        adicionarConsultaSeAusente(c, g, 101, "10345678901", "CRM/BA 123456", LocalDateTime.of(2026, 6, 13, 8, 30), "Retorno dermatologico");
        adicionarConsultaSeAusente(c, g, 102, "11345678901", "CRM/BA 223456", LocalDateTime.of(2026, 6, 13, 9, 30), "Acompanhamento de exames hormonais");
        adicionarConsultaSeAusente(c, g, 103, "12345078901", "CRM/BA 124456", LocalDateTime.of(2026, 6, 13, 10, 30), "Avaliacao odontologica inicial");
        adicionarConsultaSeAusente(c, g, 104, "13345678901", "CRM/BA 133456", LocalDateTime.of(2026, 6, 13, 14, 0), "Revisao de exames pre-operatorios");
        adicionarConsultaSeAusente(c, g, 105, "14345678901", "CRM/BA 423456", LocalDateTime.of(2026, 6, 14, 8, 0), "Consulta oftalmologica");
        adicionarConsultaSeAusente(c, g, 106, "15345678901", "CRM/BA 128456", LocalDateTime.of(2026, 6, 14, 10, 0), "Acompanhamento odontopediatrico");
        adicionarConsultaSeAusente(c, g, 107, "16345678901", "CRM/BA 523456", LocalDateTime.of(2026, 6, 15, 9, 0), "Avaliacao geriatrica preventiva");
        adicionarConsultaSeAusente(c, g, 108, "17345678901", "CRM/BA 153456", LocalDateTime.of(2026, 6, 15, 13, 30), "Retorno de trauma no joelho");
        adicionarConsultaSeAusente(c, g, 109, "18345678901", "CRM/BA 623456", LocalDateTime.of(2026, 6, 16, 11, 0), "Investigacao urologica");
        adicionarConsultaSeAusente(c, g, 110, "19345678901", "CRM/BA 129456", LocalDateTime.of(2026, 6, 16, 15, 30), "Tratamento periodontal");
        adicionarConsultaSeAusente(c, g, 111, "20345678901", "CRM/BA 723456", LocalDateTime.of(2026, 6, 17, 8, 30), "Teste alergico e orientacoes");
        adicionarConsultaSeAusente(c, g, 112, "21345678901", "CRM/BA 323456", LocalDateTime.of(2026, 6, 17, 14, 30), "Consulta ginecologica de rotina");
        adicionarConsultaSeAusente(c, g, 113, "23345678901", "CRM/BA 125456", LocalDateTime.of(2026, 6, 18, 9, 30), "Planejamento de implante");
        adicionarConsultaSeAusente(c, g, 114, "24345678901", "CRM/BA 163456", LocalDateTime.of(2026, 6, 18, 16, 0), "Avaliacao para procedimento plastico");
        adicionarConsultaSeAusente(c, g, 115, "25345678901", "CRM/BA 173456", LocalDateTime.of(2026, 6, 19, 10, 30), "Avaliacao cardiovascular");
        adicionarConsultaSeAusente(c, g, 116, "12345678901", "CRM/BA 126456", LocalDateTime.of(2026, 6, 19, 15, 0), "Tratamento endodontico");
        adicionarConsultaSeAusente(c, g, 117, "22345678901", "CRM/BA 183456", LocalDateTime.of(2026, 6, 20, 9, 0), "Consulta cirurgica geral");
        adicionarConsultaSeAusente(c, g, 118, "32345678901", "CRM/BA 120456", LocalDateTime.of(2026, 6, 20, 11, 30), "Avaliacao buco-maxilo");
        adicionarConsultaSeAusente(c, g, 119, "42345678901", "CRM/BA 193456", LocalDateTime.of(2026, 6, 22, 8, 0), "Avaliacao pediatrica cirurgica");
        adicionarConsultaSeAusente(c, g, 120, "52345678901", "CRM/BA 127456", LocalDateTime.of(2026, 6, 22, 13, 0), "Harmonizacao e planejamento");
        adicionarConsultaSeAusente(c, g, 121, "62345678901", "CRM/BA 143456", LocalDateTime.of(2026, 6, 25, 10, 0), "Avaliacao neurocirurgica");
        adicionarConsultaSeAusente(c, g, 122, "72345678901", "CRM/BA 123456", LocalDateTime.of(2026, 6, 25, 14, 0), "Dermatite alergica");
        adicionarConsultaSeAusente(c, g, 123, "82345678901", "CRM/BA 223456", LocalDateTime.of(2026, 7, 1, 8, 30), "Controle de diabetes");
        adicionarConsultaSeAusente(c, g, 124, "92345678901", "CRM/BA 423456", LocalDateTime.of(2026, 7, 2, 9, 30), "Revisao de grau");
        adicionarConsultaSeAusente(c, g, 125, "02345678901", "CRM/BA 523456", LocalDateTime.of(2026, 7, 3, 10, 30), "Acompanhamento geriatrico");
        adicionarConsultaSeAusente(c, g, 126, "10345678901", "CRM/BA 133456", LocalDateTime.of(2026, 7, 6, 14, 0), "Consulta oncologica de rotina");
        adicionarConsultaSeAusente(c, g, 127, "11345678901", "CRM/BA 124456", LocalDateTime.of(2026, 7, 8, 16, 0), "Limpeza e orientacao preventiva");
        adicionarConsultaSeAusente(c, g, 128, "14345678901", "CRM/BA 623456", LocalDateTime.of(2026, 5, 28, 9, 0), "Consulta anterior registrada");
        adicionarConsultaSeAusente(c, g, 129, "15345678901", "CRM/BA 128456", LocalDateTime.of(2026, 5, 30, 11, 0), "Retorno odontologico anterior");
        adicionarConsultaSeAusente(c, g, 130, "16345678901", "CRM/BA 153456", LocalDateTime.of(2026, 6, 5, 15, 30), "Revisao pos-trauma");
        adicionarConsultaSeAusente(c, g, 131, "19345678901", "CRM/BA 173456", LocalDateTime.of(2026, 7, 10, 8, 0), "Retorno cardiovascular");
        adicionarConsultaSeAusente(c, g, 132, "20345678901", "CRM/BA 723456", LocalDateTime.of(2026, 7, 11, 9, 30), "Reavaliacao alergologica");
    }

    public static void popularBancoInicial(Clinica c, Gravacao g) {
        inicializarMedicosPadrao(c, g);
        inicializarPacientesPadrao(c, g);
        inicializarConsultasPadrao(c, g);
        enriquecerBancoDeDados(c, g);
    }

    private static void adicionarESalvarPaciente(Clinica c, Gravacao g, Paciente paciente) {
        c.addPaciente(paciente);
        g.salvarNovoPaciente(paciente);
    }

    private static void adicionarPacienteSeAusente(Clinica c, Gravacao g, Paciente paciente) {
        if (c.getPacienteByCPF(paciente.getCpf()) != null) return;
        adicionarESalvarPaciente(c, g, paciente);
    }

    private static void adicionarESalvarMedico(Clinica c, Gravacao g, Medico medico) {
        c.addMedico(medico);
        g.salvarNovoMedico(medico);
    }

    private static void adicionarESalvarConsulta(Clinica c, Gravacao g, Consulta consulta) {
        c.addConsulta(consulta);
        g.salvarNovaConsulta(consulta);
    }

    private static void adicionarConsultaSeAusente(Clinica c, Gravacao g, int id, String cpf, String crm, LocalDateTime dataHora, String diagnostico) {
        if (c.getConsultaByIdConsulta(id) != null) return;
        Paciente paciente = c.getPacienteByCPF(cpf);
        Medico medico = c.getMedicoByCRM(crm);
        if (paciente == null || medico == null) return;
        adicionarESalvarConsulta(c, g, new Consulta(medico, paciente, dataHora, diagnostico, id));
    }

    public static void Inicializar(Clinica c, Gravacao g) {
        if (g.bancoFoiCriadoAgora()) {
            popularBancoInicial(c, g);
            return;
        }

        if (g.arquivoTemDados()) {
            g.carregarMedicos(c);
        }

        if (g.arquivoTemDadosPacientes()) {
            g.carregarPacientes(c);
        }

        if (g.arquivoTemDadosConsultas()) {
            g.carregarConsultas(c);
        }
    }
}
