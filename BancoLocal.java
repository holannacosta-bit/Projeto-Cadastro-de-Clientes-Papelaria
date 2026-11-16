import java.io.*;
import java.util.ArrayList;
 
public class BancoLocal implements Serializable {
    private ArrayList<Cliente> clientes;
    private ArrayList<Produto> produtos;
    private ArrayList<Venda> vendas;
    private ArrayList<MovimentacaoFinanceira> movimentacoes;
 
    private int proximoCliente;
    private int proximoProduto;
    private int proximaVenda;
 
    private static final String ARQUIVO = "dados_pointpel.dat";
 
    public BancoLocal() {
        clientes = new ArrayList<Cliente>();
        produtos = new ArrayList<Produto>();
        vendas = new ArrayList<Venda>();
        movimentacoes = new ArrayList<MovimentacaoFinanceira>();
 
        proximoCliente = 1;
        proximoProduto = 1;
        proximaVenda = 1;
 
        carregar();
    }
 
    @SuppressWarnings("unchecked")
    private void carregar() {
        File arquivo = new File(ARQUIVO);
 
        if (!arquivo.exists()) {
            return;
        }
 
        try {
            ObjectInputStream entrada =
                    new ObjectInputStream(new FileInputStream(arquivo));
 
            BancoLocal bancoSalvo = (BancoLocal) entrada.readObject();
            entrada.close();
 
            clientes = bancoSalvo.clientes;
            produtos = bancoSalvo.produtos;
            vendas = bancoSalvo.vendas;
            movimentacoes = bancoSalvo.movimentacoes;
            proximoCliente = bancoSalvo.proximoCliente;
            proximoProduto = bancoSalvo.proximoProduto;
            proximaVenda = bancoSalvo.proximaVenda;
 
        } catch (Exception erro) {
            System.out.println("Não foi possível carregar os dados salvos.");
        }
    }
 
    public void salvar() {
        try {
            ObjectOutputStream saida =
                    new ObjectOutputStream(new FileOutputStream(ARQUIVO));
            saida.writeObject(this);
            saida.close();
        } catch (IOException erro) {
            System.out.println("Erro ao salvar os dados.");
        }
    }
 
    public ArrayList<Cliente> getClientes() { return clientes; }
    public ArrayList<Produto> getProdutos() { return produtos; }
    public ArrayList<Venda> getVendas() { return vendas; }
    public ArrayList<MovimentacaoFinanceira> getMovimentacoes() {
        return movimentacoes;
    }
 
    public Cliente buscarClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }
 
    public Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }
 
    public Produto buscarProdutoPorId(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }
 
    public Produto buscarProdutoPorCodigo(String codigo) {
        for (Produto produto : produtos) {
            if (produto.getCodigo().equalsIgnoreCase(codigo)) {
                return produto;
            }
        }
        return null;
    }
 
    public Cliente cadastrarCliente(String nome, String cpf,
                                    String telefone, String email, String loja) {
        Cliente cliente = new Cliente(proximoCliente, nome, cpf, telefone, email, loja);
        proximoCliente++;
        clientes.add(cliente);
        salvar();
        return cliente;
    }
 
    public Produto cadastrarProduto(String nome, String categoria, String codigo,
                                    double preco, int estoque, String descricao) {
        Produto produto = new Produto(proximoProduto, nome, categoria,
                codigo, preco, estoque, descricao);
        proximoProduto++;
        produtos.add(produto);
        salvar();
        return produto;
    }
 
    public Venda registrarVenda(Cliente cliente, Produto produto,
                                int quantidade, String loja) {
        produto.baixarEstoque(quantidade);
 
        Venda venda = new Venda(proximaVenda, cliente, produto, quantidade, loja);
        proximaVenda++;
 
        cliente.registrarCompra(loja);
        vendas.add(venda);
 
        movimentacoes.add(new MovimentacaoFinanceira(
                "ENTRADA", "Venda " + venda.getId(), venda.getValorTotal()));
 
        salvar();
        return venda;
    }
 
    public void registrarMovimentacao(String tipo, String descricao, double valor) {
        movimentacoes.add(new MovimentacaoFinanceira(tipo, descricao, valor));
        salvar();
    }
}
