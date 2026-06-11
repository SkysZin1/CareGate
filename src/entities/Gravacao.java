package entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Gravacao {

    // Arquivos serão mantidos em <projectRoot>/data para não depender do diretório de execução
    private final File dataDir;
    private final File arquivoMedicos;
    private final File arquivoPacientes;
    private final File arquivoConsultas;

    // Checa se o arquivo existe e se não está vazio
    public boolean arquivoTemDados() {
        return arquivoMedicos.exists() && arquivoMedicos.length() > 0;
    }

    // Checa se o arquivo de pacientes existe e se não está vazio
    public boolean arquivoTemDadosPacientes() {
        return arquivoPacientes.exists() && arquivoPacientes.length() > 0;
    }

    // Checa se o arquivo de consultas existe e se não está vazio
    public boolean arquivoTemDadosConsultas() {
        return arquivoConsultas.exists() && arquivoConsultas.length() > 0;
    }

    // das classes (por exemplo build/). Tentamos determinar o root do projeto
    // de forma robusta: primeiro consideramos o working directory (user.dir) se
    // aparentar ser a raiz do projeto; caso contrário usamos a localização das
    // classes e subimos diretórios quando necessário (ex.: build/, out/production/).
    public Gravacao() {
        File projectRoot = null;

        // 1) Verifica se o working dir parece ser a raiz do projeto
        File userDir = new File(System.getProperty("user.dir"));
        if (userDir.exists()) {
            boolean looksLikeProject = new File(userDir, "src").exists()
                    || new File(userDir, "run.bat").exists()
                    || new File(userDir, ".git").exists();
            if (looksLikeProject) {
                projectRoot = userDir;
            }
        }

        // 2) Caso não tenhamos identificado pelo user.dir, tentamos pela localização
        // das classes (por exemplo quando executado a partir do IDE com outro cwd).
        if (projectRoot == null) {
            File codeLocation;
            try {
                codeLocation = new File(Gravacao.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            } catch (URISyntaxException e) {
                codeLocation = new File(".");
            }

            File candidate = codeLocation;
            if (candidate.isFile()) candidate = candidate.getParentFile();

            // Se a pasta for build/ ou out/ (IDE), subimos um nível
            String name = candidate.getName().toLowerCase();
            if ((name.equals("build") || name.equals("out") || name.equals("classes")) && candidate.getParentFile() != null) {
                candidate = candidate.getParentFile();
            }

            projectRoot = candidate;
        }

        // Finalmente definimos dataDir relativo ao projectRoot encontrado
        this.dataDir = new File(projectRoot, "data");
        if (!this.dataDir.exists()) this.dataDir.mkdirs();
        this.arquivoMedicos = new File(this.dataDir, "medicos.txt");
        this.arquivoPacientes = new File(this.dataDir, "pacientes.txt");
        this.arquivoConsultas = new File(this.dataDir, "consultas.txt");

        // Migração: procura por arquivos medicos/pacientes em locais comuns e
        // move (ou copia) para data/ para unificar a base de dados.
        try {
            // possível arquivos no project root
            File rootMed = new File(projectRoot, "medicos.txt");
            if (!this.arquivoMedicos.exists() && rootMed.exists()) rootMed.renameTo(this.arquivoMedicos);

            File rootPac = new File(projectRoot, "pacientes.txt");
            if (!this.arquivoPacientes.exists() && rootPac.exists()) rootPac.renameTo(this.arquivoPacientes);

            // também checa no working dir original (userDir) caso seja diferente
            File userMed = new File(System.getProperty("user.dir"), "medicos.txt");
            if (!this.arquivoMedicos.exists() && userMed.exists()) userMed.renameTo(this.arquivoMedicos);

            File userPac = new File(System.getProperty("user.dir"), "pacientes.txt");
            if (!this.arquivoPacientes.exists() && userPac.exists()) userPac.renameTo(this.arquivoPacientes);
        } catch (Exception ex) {
            // se a migração falhar, ignoramos (não crítica)
        }
    }

    // Salva médico no final do arquivo
    public void salvarNovoMedico(Medico medico) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoMedicos, true))) {
            bw.write(medico.paraTexto());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    // Lê o arquivo e preenche a memória da Clínica
    public void carregarMedicos(Clinica c) {
        if (!arquivoMedicos.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoMedicos))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                Medico medico = converterLinhaParaMedico(linha);
                if (medico != null) {
                    c.addMedico(medico);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler dados: " + e.getMessage());
        }
    }

    // ======= Funções de pacientes (antes em GravacaoPaciente) =======
    // Salva paciente no final do arquivo
    public void salvarNovoPaciente(Paciente paciente) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoPacientes, true))) {
            bw.write(paciente.getNome() + "|" + paciente.getEndereco() + "|" + paciente.getCpf() + "|" + paciente.getTelefone());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar paciente no arquivo: " + e.getMessage());
        }
    }

    // Lê o arquivo e preenche a memória da Clínica com pacientes
    public void carregarPacientes(Clinica c) {
        if (!arquivoPacientes.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoPacientes))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                Paciente paciente = converterLinhaParaPaciente(linha);
                if (paciente != null) {
                    c.addPaciente(paciente);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler dados de pacientes: " + e.getMessage());
        }
    }

    // Função para remontar o objeto Paciente a partir do texto
    private Paciente converterLinhaParaPaciente(String linha) {
        if (linha.contains("|")) {
            String[] dados = linha.split("\\|");
            if (dados.length < 4) {
                return null;
            }
            String nome = dados[0].trim();
            String endereco = dados[1].trim();
            String cpf = dados[2].trim();
            String telefone = dados[3].trim();
            return new Paciente(nome, endereco, cpf, telefone);
        }

        String[] dados = linha.split(",");
        if (dados.length < 4) {
            return null;
        }

        String nome = dados[0].trim();
        String telefone = dados[dados.length - 1].trim();
        String cpf = dados[dados.length - 2].trim();
        StringBuilder enderecoBuilder = new StringBuilder();
        for (int i = 1; i < dados.length - 2; i++) {
            if (enderecoBuilder.length() > 0) {
                enderecoBuilder.append(",");
            }
            enderecoBuilder.append(dados[i].trim());
        }
        String endereco = enderecoBuilder.toString();

        return new Paciente(nome, endereco, cpf, telefone);
    }

    // remover o paciente baseado no CPF (que funciona como identificador único, similar ao CRM)
    public void removerPacienteDoArquivo(String cpfParaRemover) {
        File arquivo = arquivoPacientes;
        if (!arquivo.exists()) {
            return;
        }

        List<String> linhasQueFicam = new ArrayList<>();

        // ler tudo e guardar apenas quem não tem o CPF informado
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(",");
                if (dados.length >= 4) {
                    String cpfDaLinha;
                    if (linha.contains("|")) {
                        cpfDaLinha = linha.split("\\|")[2].trim();
                    } else {
                        cpfDaLinha = dados[dados.length - 2].trim();
                    }

                    // Só adiciona na lista se o CPF for diferente do que queremos remover
                    if (!cpfDaLinha.equalsIgnoreCase(cpfParaRemover.trim())) {
                        linhasQueFicam.add(linha);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo para remoção: " + e.getMessage());
            return; // Para a execução em caso de erro
        }

        // Reescreve o arquivo com os pacientes que ficaram
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, false))) {
            for (String linha : linhasQueFicam) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo após remoção: " + e.getMessage());
        }
    }

    // Função para remontar o objeto a partir do texto
    private Medico converterLinhaParaMedico(String linha) {
        String[] dados = linha.split(",");
        String tipo = dados[0].trim();
        String nome = dados[1].trim();
        String crm = dados[2].trim();
        String espec = dados[3].trim();
        Integer idade = Integer.parseInt(dados[4].trim());
        Integer valor = Integer.parseInt(dados[5].trim());

        switch (tipo) {
            case "Clinico": return new MedicoClinico(nome, crm, espec, idade, valor);
            case "Cirurgiao": return new MedicoCirurgiao(nome, crm, espec, idade, valor);
            case "Odontologo": return new MedicoOdontologo(nome, crm, espec, idade, valor);
            default: return null;
        }
    }

    public void removerMedicoDoArquivo(String crmParaRemover) {
        File arquivo = arquivoMedicos;
        if (!arquivo.exists()) {
            return;
        }

        List<String> linhasQueFicam = new ArrayList<>();

        // Passo 1 e 2: Ler tudo e guardar apenas quem nao tem o CRM informado
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(",");
                if (dados.length >= 3) {
                    String crmDaLinha = dados[2].trim();

                    // Só adiciona na lista se o CRM for diferente do que queremos remover
                    if (!crmDaLinha.equalsIgnoreCase(crmParaRemover.trim())) {
                        linhasQueFicam.add(linha);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo para remoção: " + e.getMessage());
            return; // Para a execução do erro
        }

        //Reescrevee o arquivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, false))) {
            for (String linha : linhasQueFicam) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo após remoção: " + e.getMessage());
        }
    }
    // Salva consulta no arquivo com formato estruturado (vírgula separada)
    public void salvarNovaConsulta(Consulta consulta) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoConsultas, true))) {
            // Formato: id,CPF,CRM,data(dd/MM/yyyy),diagnostico
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = consulta.getDataConsulta().format(formatter);
            String linha = consulta.getIdConsulta() + "," +
                          consulta.getPaciente().getCpf() + "," +
                          consulta.getMedico().getCRM() + "," +
                          dataFormatada + "," +
                          consulta.getDiagnostico();
            bw.write(linha);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar consulta no arquivo: " + e.getMessage());
        }
    }

    // Lê o arquivo e preenche a memória da Clínica com consultas
    public void carregarConsultas(Clinica c) {
        if (!arquivoConsultas.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoConsultas))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                Consulta consulta = converterLinhaParaConsulta(c, linha);
                if (consulta != null) {
                    c.addConsulta(consulta);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler dados de consultas: " + e.getMessage());
        }
    }

    // Função para remontar o objeto Consulta a partir do texto
    private Consulta converterLinhaParaConsulta(Clinica c, String linha) {
        if (!linha.contains(",")) {
            return null;
        }

        String[] dados = linha.split(",");
        if (dados.length < 4) {
            return null;
        }

        try {
            int id = Integer.parseInt(dados[0].trim());
            String cpf = dados[1].trim();
            String crm = dados[2].trim();
            String dataStr = dados[3].trim();
            String diagnostico = dados.length > 4 ? dados[4].trim() : "";

            // Busca os objetos pela Clínica
            Paciente paciente = c.getPacienteByCPF(cpf);
            Medico medico = c.getMedicoByCRM(crm);

            if (paciente == null || medico == null) {
                return null;
            }

            // Converte a data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                LocalDate localDate = LocalDate.parse(dataStr, formatter);
                LocalDateTime dataConsulta = localDate.atStartOfDay();

                Consulta consulta = new Consulta(medico, paciente, dataConsulta, diagnostico, id);
                return consulta;
            } catch (Exception e) {
                System.out.println("Erro ao converter data da consulta: " + e.getMessage());
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter ID da consulta: " + e.getMessage());
            return null;
        }
    }

    // Remove consulta do arquivo baseado no CPF e CRM (identificadores únicos)
    public void removerConsultaDoArquivo(String cpfPaciente, String crmMedico) {
        if (!arquivoConsultas.exists()) {
            return;
        }

        List<String> linhasQueFicam = new ArrayList<>();

        // Ler tudo e guardar apenas as consultas que não correspondem aos critérios
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoConsultas))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(",");
                if (dados.length >= 3) {
                    String cpfDaLinha = dados[1].trim();
                    String crmDaLinha = dados[2].trim();

                    // Só adiciona na lista se NÃO for a consulta que queremos remover
                    if (!cpfDaLinha.equals(cpfPaciente.trim()) || !crmDaLinha.equals(crmMedico.trim())) {
                        linhasQueFicam.add(linha);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo para remoção de consulta: " + e.getMessage());
            return;
        }

        // Reescreve o arquivo com as consultas que ficaram
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoConsultas, false))) {
            for (String linha : linhasQueFicam) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo após remoção de consulta: " + e.getMessage());
        }
    }

    // Retorna o ID da última consulta existente no arquivo
    public int getIdUltimaConsulta() {
        if (!arquivoConsultas.exists() || arquivoConsultas.length() == 0) {
            return 0; // Se não há arquivo ou está vazio, retorna 0
        }

        int ultimoId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoConsultas))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(",");
                if (dados.length >= 1) {
                    try {
                        int id = Integer.parseInt(dados[0].trim());
                        if (id > ultimoId) {
                            ultimoId = id;
                        }
                    } catch (NumberFormatException e) {
                        // Ignora linhas com formato inválido
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de consultas: " + e.getMessage());
        }

        return ultimoId;
    }
}