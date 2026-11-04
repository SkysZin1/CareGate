package service;

import application.Inicializacao;
import entities.Clinica;
import entities.Consulta;
import entities.Gravacao;
import entities.Medico;
import entities.MedicoCirurgiao;
import entities.MedicoClinico;
import entities.MedicoOdontologo;
import entities.Paciente;
import repository.MedicoRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClinicaService {
    private final Clinica clinica;
    private final Gravacao gravacao;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ClinicaService(Clinica clinica, Gravacao gravacao) {
        this.clinica = clinica;
        this.gravacao = gravacao;
    }

    public List<Medico> listarMedicos() {
        return clinica.listarMedicos();
    }

    public List<Paciente> listarPacientes() {
        return clinica.listarPacientes();
    }

    public List<Consulta> listarConsultas() {
        return clinica.listarConsultas().stream()
                .sorted(Comparator.comparing(Consulta::getDataConsulta))
                .collect(Collectors.toList());
    }

    public Paciente buscarPaciente(String cpf) {
        return clinica.getPacienteByCPF(cpf);
    }

    public Medico buscarMedico(String crm) {
        return clinica.getMedicoByCRM(crm);
    }

    public Consulta buscarConsulta(int id) {
        return clinica.getConsultaByIdConsulta(id);
    }

    public Paciente cadastrarPaciente(String nome, String endereco, String cpf, String telefone) throws ServiceException {
        validarPaciente(nome, endereco, cpf, telefone);
        if (clinica.getPacienteByCPF(cpf) != null) {
            throw new ServiceException("Ja existe um paciente com esse CPF.");
        }
        Paciente paciente = new Paciente(nome.trim(), endereco.trim(), cpf.trim(), telefone.trim());
        clinica.addPaciente(paciente);
        gravacao.salvarNovoPaciente(paciente);
        return paciente;
    }

    public void atualizarPaciente(String cpf, String nome, String endereco, String telefone) throws ServiceException {
        Paciente paciente = clinica.getPacienteByCPF(cpf);
        if (paciente == null) {
            throw new ServiceException("Paciente nao encontrado.");
        }
        validarPaciente(nome, endereco, cpf, telefone);
        paciente.setNome(nome.trim());
        paciente.setEndereco(endereco.trim());
        paciente.setTelefone(telefone.trim());
        gravacao.salvarNovoPaciente(paciente);
    }

    public void removerPaciente(String cpf) throws ServiceException {
        Paciente paciente = clinica.getPacienteByCPF(cpf);
        if (paciente == null) {
            throw new ServiceException("Paciente nao encontrado.");
        }
        if (!paciente.listarConsultas().isEmpty()) {
            throw new ServiceException("Paciente possui consultas cadastradas. Cancele as consultas antes de remover.");
        }
        clinica.removePaciente(cpf);
        gravacao.removerPacienteDoArquivo(cpf);
    }

    public Medico cadastrarMedico(String tipo, String nome, String crm, String especialidade, int idade, int valor) throws ServiceException {
        validarMedico(nome, crm, especialidade, idade, valor);
        if (clinica.getMedicoByCRM(crm) != null) {
            throw new ServiceException("Ja existe um medico com esse CRM.");
        }
        Medico medico = criarMedico(tipo, nome.trim(), crm.trim(), especialidade.trim(), idade, valor);
        clinica.addMedico(medico);
        gravacao.salvarNovoMedico(medico);
        return medico;
    }

    public void atualizarMedico(String crm, String tipo, String nome, String especialidade, int idade, int valor) throws ServiceException {
        Medico atual = clinica.getMedicoByCRM(crm);
        if (atual == null) {
            throw new ServiceException("Medico nao encontrado.");
        }
        validarMedico(nome, crm, especialidade, idade, valor);

        Medico atualizado = criarMedico(tipo, nome.trim(), crm.trim(), especialidade.trim(), idade, valor);
        clinica.removeMedico(crm);
        clinica.addMedico(atualizado);
        for (Consulta consulta : clinica.listarConsultas()) {
            if (consulta.getMedico().getCRM().equals(crm)) {
                consulta.setMedico(atualizado);
            }
        }
        gravacao.salvarNovoMedico(atualizado);
    }

    public void removerMedico(String crm) throws ServiceException {
        Medico medico = clinica.getMedicoByCRM(crm);
        if (medico == null) {
            throw new ServiceException("Medico nao encontrado.");
        }
        boolean temConsulta = clinica.listarConsultas().stream()
                .anyMatch(consulta -> consulta.getMedico().getCRM().equals(crm));
        if (temConsulta) {
            throw new ServiceException("Medico possui consultas cadastradas. Cancele as consultas antes de remover.");
        }
        clinica.removeMedico(crm);
        gravacao.removerMedicoDoArquivo(crm);
    }

    public Consulta agendarConsulta(String cpf, String crm, LocalDate data, LocalTime hora, String observacoes) throws ServiceException {
        Paciente paciente = clinica.getPacienteByCPF(cpf);
        Medico medico = clinica.getMedicoByCRM(crm);
        if (paciente == null) throw new ServiceException("Paciente nao encontrado.");
        if (medico == null) throw new ServiceException("Medico nao encontrado.");

        LocalDateTime dataHora = LocalDateTime.of(data, hora);
        validarConflitoAgenda(null, cpf, crm, dataHora);

        int id = gravacao.getIdUltimaConsulta() + 1;
        Consulta consulta = new Consulta(medico, paciente, dataHora, normalizarTexto(observacoes), id);
        clinica.addConsulta(consulta);
        gravacao.salvarNovaConsulta(consulta);
        return consulta;
    }

    public void atualizarConsulta(int id, String cpf, String crm, LocalDate data, LocalTime hora, String observacoes) throws ServiceException {
        Consulta consulta = clinica.getConsultaByIdConsulta(id);
        if (consulta == null) throw new ServiceException("Consulta nao encontrada.");
        Paciente paciente = clinica.getPacienteByCPF(cpf);
        Medico medico = clinica.getMedicoByCRM(crm);
        if (paciente == null) throw new ServiceException("Paciente nao encontrado.");
        if (medico == null) throw new ServiceException("Medico nao encontrado.");

        LocalDateTime dataHora = LocalDateTime.of(data, hora);
        validarConflitoAgenda(id, cpf, crm, dataHora);

        if (!consulta.getPaciente().getCpf().equals(cpf)) {
            consulta.getPaciente().removeConsulta(id);
            paciente.addConsulta(consulta);
        }
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataConsulta(dataHora);
        consulta.setDiagnostico(normalizarTexto(observacoes));
        gravacao.salvarNovaConsulta(consulta);
    }

    public void cancelarConsulta(int id) throws ServiceException {
        Consulta consulta = clinica.getConsultaByIdConsulta(id);
        if (consulta == null) {
            throw new ServiceException("Consulta nao encontrada.");
        }
        consulta.getPaciente().removeConsulta(id);
        clinica.removeConsulta(id);
        gravacao.removerConsultaDoArquivo(id);
    }

    public long contarConsultasHoje() {
        LocalDate hoje = LocalDate.now();
        return clinica.listarConsultas().stream()
                .filter(consulta -> consulta.getDataConsulta().toLocalDate().equals(hoje))
                .count();
    }

    public long contarProximasConsultas(int dias) {
        LocalDate hoje = LocalDate.now();
        return clinica.listarConsultas().stream()
                .filter(consulta -> {
                    LocalDate data = consulta.getDataConsulta().toLocalDate();
                    return !data.isBefore(hoje) && !data.isAfter(hoje.plusDays(dias));
                })
                .count();
    }

    public String especialidadeMaisAgendada() {
        return clinica.listarConsultas().stream()
                .collect(Collectors.groupingBy(consulta -> consulta.getMedico().getEspecialidade(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Sem consultas");
    }

    public File gerarRelatorio() throws ServiceException {
        File relatorio = new File(new File(System.getProperty("user.dir"), "data"), "relatorio-caregate.txt");
        if (!relatorio.getParentFile().exists()) relatorio.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(relatorio))) {
            writer.write("CareGate - Relatorio Geral");
            writer.newLine();
            writer.write("Gerado em: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            writer.newLine();
            writer.newLine();
            writer.write("Pacientes ativos: " + listarPacientes().size());
            writer.newLine();
            writer.write("Medicos cadastrados: " + listarMedicos().size());
            writer.newLine();
            writer.write("Consultas cadastradas: " + listarConsultas().size());
            writer.newLine();
            writer.write("Consultas de hoje: " + contarConsultasHoje());
            writer.newLine();
            writer.write("Proximas consultas em 7 dias: " + contarProximasConsultas(7));
            writer.newLine();
            writer.write("Especialidade mais agendada: " + especialidadeMaisAgendada());
            writer.newLine();
            writer.newLine();
            writer.write("Consultas:");
            writer.newLine();
            for (Consulta consulta : listarConsultas()) {
                writer.write(String.format(
                        "#%d | %s | %s | %s | %s | %s",
                        consulta.getIdConsulta(),
                        consulta.getDataConsulta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        consulta.getPaciente().getNome(),
                        consulta.getMedico().getNome(),
                        consulta.getMedico().getEspecialidade(),
                        consulta.getDiagnostico()
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ServiceException("Nao foi possivel gerar o relatorio: " + e.getMessage());
        }
        return relatorio;
    }

    public void resetarBancoDeDados() throws ServiceException {
        try {
            gravacao.resetarBanco();
            clinica.limparDados();
            Inicializacao.popularBancoInicial(clinica, gravacao);
        } catch (RuntimeException e) {
            throw new ServiceException("Nao foi possivel resetar o banco de dados: " + e.getMessage());
        }
    }

    public String tipoDoMedico(Medico medico) {
        return MedicoRepository.tipoDoMedico(medico);
    }

    private void validarPaciente(String nome, String endereco, String cpf, String telefone) throws ServiceException {
        if (isBlank(nome) || isBlank(cpf) || isBlank(telefone) || isBlank(endereco)) {
            throw new ServiceException("Preencha todos os campos do paciente.");
        }
        if (!cpf.trim().matches("\\d{11}")) {
            throw new ServiceException("CPF deve ter 11 digitos, sem pontos ou tracos.");
        }
        if (!telefone.trim().matches("\\d{8,13}")) {
            throw new ServiceException("Telefone deve conter apenas numeros, entre 8 e 13 digitos.");
        }
    }

    private void validarMedico(String nome, String crm, String especialidade, int idade, int valor) throws ServiceException {
        if (isBlank(nome) || isBlank(crm) || isBlank(especialidade)) {
            throw new ServiceException("Preencha nome, CRM e especialidade.");
        }
        if (!crm.trim().matches("CRM/[A-Z]{2}\\s\\d{4,6}")) {
            throw new ServiceException("CRM invalido. Use o formato CRM/UF 123456.");
        }
        if (idade <= 0 || idade > 100) {
            throw new ServiceException("Idade do medico deve estar entre 1 e 100.");
        }
        if (valor <= 0) {
            throw new ServiceException("Valor da consulta deve ser maior que zero.");
        }
    }

    private void validarConflitoAgenda(Integer idIgnorado, String cpf, String crm, LocalDateTime dataHora) throws ServiceException {
        for (Consulta consulta : clinica.listarConsultas()) {
            if (idIgnorado != null && consulta.getIdConsulta() == idIgnorado) continue;
            boolean mesmoHorario = consulta.getDataConsulta().equals(dataHora);
            if (mesmoHorario && consulta.getMedico().getCRM().equals(crm)) {
                throw new ServiceException("Esse medico ja possui consulta nesse horario.");
            }
            if (mesmoHorario && consulta.getPaciente().getCpf().equals(cpf)) {
                throw new ServiceException("Esse paciente ja possui consulta nesse horario.");
            }
        }
    }

    private Medico criarMedico(String tipo, String nome, String crm, String especialidade, int idade, int valor) {
        if ("Cirurgiao".equals(tipo)) return new MedicoCirurgiao(nome, crm, especialidade, idade, valor);
        if ("Odontologo".equals(tipo)) return new MedicoOdontologo(nome, crm, especialidade, idade, valor);
        return new MedicoClinico(nome, crm, especialidade, idade, valor);
    }

    private String normalizarTexto(String texto) {
        return texto == null ? "" : texto.trim();
    }

    private boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}
