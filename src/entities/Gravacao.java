package entities;

import repository.ConsultaRepository;
import repository.Database;
import repository.MedicoRepository;
import repository.PacienteRepository;

import java.io.File;

public class Gravacao {
    private final Database database;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;

    public Gravacao() {
        this.database = new Database();
        this.medicoRepository = new MedicoRepository(database);
        this.pacienteRepository = new PacienteRepository(database);
        this.consultaRepository = new ConsultaRepository(database);
    }

    public boolean arquivoTemDados() {
        return medicoRepository.temDados();
    }

    public boolean arquivoTemDadosPacientes() {
        return pacienteRepository.temDados();
    }

    public boolean arquivoTemDadosConsultas() {
        return consultaRepository.temDados();
    }

    public void salvarNovoMedico(Medico medico) {
        medicoRepository.salvar(medico);
    }

    public void carregarMedicos(Clinica c) {
        for (Medico medico : medicoRepository.listar()) {
            c.addMedico(medico);
        }
    }

    public void salvarNovoPaciente(Paciente paciente) {
        pacienteRepository.salvar(paciente);
    }

    public void carregarPacientes(Clinica c) {
        for (Paciente paciente : pacienteRepository.listar()) {
            c.addPaciente(paciente);
        }
    }

    public void removerPacienteDoArquivo(String cpfParaRemover) {
        pacienteRepository.remover(cpfParaRemover);
    }

    public void removerMedicoDoArquivo(String crmParaRemover) {
        medicoRepository.remover(crmParaRemover);
    }

    public void salvarNovaConsulta(Consulta consulta) {
        consultaRepository.salvar(consulta);
    }

    public void carregarConsultas(Clinica c) {
        for (Consulta consulta : consultaRepository.listar(c)) {
            c.addConsulta(consulta);
        }
    }

    public void removerConsultaDoArquivo(int id) {
        consultaRepository.remover(id);
    }

    public int getIdUltimaConsulta() {
        return consultaRepository.ultimoId();
    }

    public File getDatabaseFile() {
        return database.getDbFile();
    }

    public boolean bancoFoiCriadoAgora() {
        return database.isNovoBanco();
    }

    public void resetarBanco() {
        database.limparDados();
    }
}
