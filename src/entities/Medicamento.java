// feito por Davi - (25/05/26)

package entities;

public class Medicamento {
    private String nomeRemedio, principioAtivo, posologia;
    private double valor;
    private int dosagem;

    public Medicamento(String nomeRemedio, String principioAtivo, float valor, int dosagem, String posologia) {
        this.nomeRemedio = nomeRemedio;
        this.principioAtivo = principioAtivo;
        this.valor = valor;
        this.dosagem = dosagem;
        this.posologia = posologia;
    }

    public String getNomeRemedio() {
        return nomeRemedio;
    }

    public void setNomeRemedio(String nomeRemedio) {
        this.nomeRemedio = nomeRemedio;
    }

    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    public void setPrincipioAtivo(String principioAtivo) {
        this.principioAtivo = principioAtivo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getDosagem() {
        return dosagem;
    }

    public void setDosagem(int dosagem) {
        this.dosagem = dosagem;
    }
}
