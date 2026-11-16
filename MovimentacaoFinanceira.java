import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 
public class MovimentacaoFinanceira implements Serializable {
    private String tipo;
    private String descricao;
    private double valor;
    private LocalDateTime data;
 
    public MovimentacaoFinanceira(String tipo, String descricao, double valor) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.data = LocalDateTime.now();
    }
 
    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
 
    public String resumo() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
 
        return data.format(formato)
                + " | " + tipo
                + " | " + descricao
                + " | R$ " + String.format("%.2f", valor);
    }
}
