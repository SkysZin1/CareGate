package entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Gravacao {

    private String caminhoArquivoMedicos = "medicos.txt";

    // Checa se o arquivo existe e se não está vazio
    public boolean arquivoTemDados() {
        File arquivo = new File(caminhoArquivoMedicos);
        return arquivo.exists() && arquivo.length() > 0;
    }

    // Salva médico no final do arquivo
    public void salvarNovoMedico(Medico medico) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivoMedicos, true))) {
            bw.write(medico.paraTexto()); // Certifique-se de que a classe Medico tem este método
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    // Lê o arquivo e preenche a memória da Clínica
    public void carregarMedicos(Clinica c) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoMedicos))) {
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
        File arquivo = new File(caminhoArquivoMedicos);
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