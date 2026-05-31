package entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class GravacaoPaciente {

    private String caminhoArquivoPacientes = "pacientes.txt";

    // Checa se o arquivo existe e se não está vazio
    public boolean arquivoTemDados() {
        File arquivo = new File(caminhoArquivoPacientes);
        return arquivo.exists() && arquivo.length() > 0;
    }

    // Salva paciente no final do arquivo
    public void salvarNovoPaciente(Paciente paciente) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivoPacientes, true))) {
            bw.write(paciente.toString()); // Necessário adicionar este método na classe Paciente
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    // Lê o arquivo e preenche a memória da Clínica
    public void carregarPacientes(Clinica c) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoPacientes))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                Paciente paciente = converterLinhaParaPaciente(linha);
                if (paciente != null) {
                    c.addPaciente(paciente); // Certifique-se de que a classe Clinica tem este método
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler dados: " + e.getMessage());
        }
    }

    // remontar o objeto a partir do texto
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
        File arquivo = new File(caminhoArquivoPacientes);
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
}