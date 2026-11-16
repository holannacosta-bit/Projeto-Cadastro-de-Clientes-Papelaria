import java.io.Serializable;
import java.util.ArrayList;
 
public class Cliente implements Serializable {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private boolean ativo;
    private int quantidadeCompras;
    private ArrayList<String> lojasFrequentadas;
 
    public Cliente(int id, String nome, String cpf, String telefone,
                   String email, String loja) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.ativo = true;
        this.quantidadeCompras = 0;
        this.lojasFrequentadas = new ArrayList<String>();
 
        if (loja != null && !loja.isEmpty()) {
            lojasFrequentadas.add(loja);
        }
    }
 
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
    public boolean isAtivo() { return ativo; }
    public int getQuantidadeCompras() { return quantidadeCompras; }
 
    public String getClassificacao() {
        if (quantidadeCompras == 0) {
            return "Novo";
        }
        return "Recorrente";
    }
 
    public void atualizar(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }
 
    public void inativar() {
        ativo = false;
    }
 
    public void registrarCompra(String loja) {
        quantidadeCompras++;
 
        if (loja != null && !loja.isEmpty()
                && !lojasFrequentadas.contains(loja)) {
            lojasFrequentadas.add(loja);
        }
    }
 
    public String resumo() {
        return "ID: " + id
                + " | Nome: " + nome
                + " | CPF: " + cpf
                + " | Telefone: " + telefone
                + " | E-mail: " + email
                + " | Status: " + (ativo ? "Ativo" : "Inativo")
                + " | Classificação: " + getClassificacao()
                + " | Compras: " + quantidadeCompras
                + " | Lojas: " + lojasFrequentadas;
    }
}
