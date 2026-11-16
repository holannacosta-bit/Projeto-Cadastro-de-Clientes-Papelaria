import java.io.Serializable;
 
public class Produto implements Serializable {
    private int id;
    private String nome;
    private String categoria;
    private String codigo;
    private double preco;
    private int estoque;
    private String descricao;
    private boolean ativo;
 
    public Produto(int id, String nome, String categoria, String codigo,
                   double preco, int estoque, String descricao) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.codigo = codigo;
        this.preco = preco;
        this.estoque = estoque;
        this.descricao = descricao;
        this.ativo = true;
    }
 
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCodigo() { return codigo; }
    public double getPreco() { return preco; }
    public int getEstoque() { return estoque; }
    public boolean isAtivo() { return ativo; }
 
    public void atualizar(String nome, String categoria,
                          double preco, String descricao) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.descricao = descricao;
    }
 
    public void adicionarEstoque(int quantidade) {
        if (quantidade > 0) {
            estoque += quantidade;
        }
    }
 
    public void baixarEstoque(int quantidade) {
        if (quantidade <= 0 || quantidade > estoque) {
            throw new IllegalArgumentException("Quantidade inválida ou estoque insuficiente.");
        }
        estoque -= quantidade;
    }
 
    public void inativar() {
        ativo = false;
    }
 
    public String resumo() {
        return "ID: " + id
                + " | Código: " + codigo
                + " | Nome: " + nome
                + " | Categoria: " + categoria
                + " | Preço: R$ " + String.format("%.2f", preco)
                + " | Estoque: " + estoque
                + " | Status: " + (ativo ? "Ativo" : "Inativo")
                + " | Descrição: " + descricao;
    }
}
