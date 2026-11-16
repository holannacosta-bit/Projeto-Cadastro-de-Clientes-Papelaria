import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 
public class Venda implements Serializable {
    private int id;
    private int clienteId;
    private int produtoId;
    private String nomeCliente;
    private String nomeProduto;
    private int quantidade;
    private String loja;
    private double valorTotal;
    private LocalDateTime data;
 
    public Venda(int id, Cliente cliente, Produto produto,
                 int quantidade, String loja) {
        this.id = id;
        this.clienteId = cliente.getId();
        this.produtoId = produto.getId();
        this.nomeCliente = cliente.getNome();
        this.nomeProduto = produto.getNome();
        this.quantidade = quantidade;
        this.loja = loja;
        this.valorTotal = produto.getPreco() * quantidade;
        this.data = LocalDateTime.now();
    }
 
    public int getId() { return id; }
    public double getValorTotal() { return valorTotal; }
 
    public String resumo() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
 
        return "Venda: " + id
                + " | Data: " + data.format(formato)
                + " | Cliente: " + nomeCliente + " (ID " + clienteId + ")"
                + " | Produto: " + nomeProduto + " (ID " + produtoId + ")"
                + " | Quantidade: " + quantidade
                + " | Loja: " + loja
                + " | Total: R$ " + String.format("%.2f", valorTotal);
    }
}
