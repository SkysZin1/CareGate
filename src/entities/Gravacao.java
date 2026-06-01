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

public class Gravacao {

    // Arquivos serão mantidos em <projectRoot>/data para não depender do diretório de execução
    private final File dataDir;
    private final File arquivoMedicos;
    private final File arquivoPacientes;

    // Checa se o arquivo existe e se não está vazio
    public boolean arquivoTemDados() {
        return arquivoMedicos.exists() && arquivoMedicos.length() > 0;
    }

    // Checa se o arquivo de pacientes existe e se não está vazio
    public boolean arquivoTemDadosPacientes() {
        return arquivoPacientes.exists() && arquivoPacientes.length() > 0;
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
            bw.write(medico.paraTexto()); // Certifique-se de que a classe Medico tem este método
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
            bw.write(paciente.toString());
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
        String[] dados = linha.split(",");

        // Verifica se a linha tem os dados necessários para evitar ArrayIndexOutOfBoundsException
        if (dados.length < 4) {
            return null;
        }

        String nome = dados[0];
        String endereco = dados[1];
        String cpf = dados[2];
        String telefone = dados[3];

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
                    String cpfDaLinha = dados[2]; // O CPF está na posição 2 (índice 2)

                    // Só adiciona na lista se o CPF for diferente do que queremos remover
                    if (!cpfDaLinha.equalsIgnoreCase(cpfParaRemover)) {
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
        String tipo = dados[0];
        String nome = dados[1];
        String crm = dados[2];
        String espec = dados[3];
        Integer idade = Integer.parseInt(dados[4]);
        Integer valor = Integer.parseInt(dados[5]);

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
                    String crmDaLinha = dados[2];

                    // Só adiciona na lista se o CRM for diferente do que queremos remover
                    if (!crmDaLinha.equalsIgnoreCase(crmParaRemover)) {
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
}