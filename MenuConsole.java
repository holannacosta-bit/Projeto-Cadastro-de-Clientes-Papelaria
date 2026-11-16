import java.util.ArrayList;
import java.util.Scanner;
 
public class MenuConsole {
    private Scanner scanner;
    private BancoLocal banco;
 
    private final String[] lojas = {
        "Loja 1", "Loja 2", "Loja 3", "Loja 4"
    };
 
    private final String emailLogin = "admin@pointpel.local";
    private final String senhaLogin = "PointPel123";
 
    public MenuConsole(Scanner scanner, BancoLocal banco) {
        this.scanner = scanner;
        this.banco = banco;
    }
 
    public void iniciar() {
        System.out.println("=== POINTPEL - PAPELARIA E PRESENTES ===");
 
        if (!login()) {
            System.out.println("Acesso não autorizado.");
            return;
        }
 
        int opcao;
        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Clientes");
            System.out.println("2 - Produtos");
            System.out.println("3 - Vendas");
            System.out.println("4 - Financeiro");
            System.out.println("0 - Sair");
 
            opcao = lerInteiro("Escolha uma opção: ");
 
            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuProdutos();
                    break;
                case 3:
                    menuVendas();
                    break;
                case 4:
                    menuFinanceiro();
                    break;
                case 0:
                    banco.salvar();
                    System.out.println("Dados salvos. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
 
    private boolean login() {
        for (int tentativa = 1; tentativa <= 3; tentativa++) {
            String email = lerTexto("E-mail: ");
            String senha = lerTexto("Senha: ");
 
            if (email.equalsIgnoreCase(emailLogin) && senha.equals(senhaLogin)) {
                return true;
            }
 
            System.out.println("E-mail ou senha incorretos.");
        }
        return false;
    }
 
    // ---------------- CLIENTES ----------------
 
    private void menuClientes() {
        int opcao;
        do {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Listar clientes ativos");
            System.out.println("3 - Pesquisar cliente");
            System.out.println("4 - Editar cliente");
            System.out.println("5 - Inativar cliente");
            System.out.println("0 - Voltar");
 
            opcao = lerInteiro("Escolha: ");
 
            switch (opcao) {
                case 1: cadastrarCliente(); break;
                case 2: listarClientes(); break;
                case 3: pesquisarCliente(); break;
                case 4: editarCliente(); break;
                case 5: inativarCliente(); break;
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
 
    private void cadastrarCliente() {
        String nome = lerObrigatorio("Nome: ");
        String cpf = lerObrigatorio("CPF (11 números): ").replaceAll("\\D", "");
 
        if (cpf.length() != 11 || banco.buscarClientePorCpf(cpf) != null) {
            System.out.println("CPF inválido ou já cadastrado.");
            return;
        }
 
        String telefone = lerObrigatorio("Telefone: ");
        String email = lerTexto("E-mail: ");
        String loja = selecionarLoja();
 
        Cliente cliente = banco.cadastrarCliente(nome, cpf, telefone, email, loja);
        System.out.println("Cliente cadastrado!");
        System.out.println(cliente.resumo());
    }
 
    private void listarClientes() {
        boolean encontrou = false;
 
        for (Cliente cliente : banco.getClientes()) {
            if (cliente.isAtivo()) {
                System.out.println(cliente.resumo());
                encontrou = true;
            }
        }
 
        if (!encontrou) {
            System.out.println("Nenhum cliente ativo cadastrado.");
        }
    }
 
    private void pesquisarCliente() {
String termo = lerTexto("Digite o nome ou CPF do cliente: ").trim();
String cpf = termo.replaceAll("\\D", "");
boolean encontrou = false;
        for (Cliente cliente : banco.getClientes()) {
if (cliente.getNome().toLowerCase().contains(termo.toLowerCase())
        || cliente.getCpf().equals(cpf)) {
                encontrou = true;
            }
        }
 
        if (!encontrou) {
            System.out.println("Cliente não encontrado.");
        }
    }
 
    private void editarCliente() {
        Cliente cliente = localizarCliente();
 
        if (cliente == null) return;
        if (!cliente.isAtivo()) {
            System.out.println("Não é possível editar um cliente inativo.");
            return;
        }
 
        String nome = lerObrigatorio("Novo nome: ");
        String telefone = lerObrigatorio("Novo telefone: ");
        String email = lerTexto("Novo e-mail: ");
 
        cliente.atualizar(nome, telefone, email);
        banco.salvar();
        System.out.println("Cliente atualizado.");
    }
 
    private void inativarCliente() {
        Cliente cliente = localizarCliente();
 
        if (cliente == null) return;
 
        String resposta = lerTexto("Confirma a inativação? (s/n): ");
        if (resposta.equalsIgnoreCase("s")) {
            cliente.inativar();
            banco.salvar();
            System.out.println("Cliente inativado.");
        }
    }
 
    private Cliente localizarCliente() {
        int id = lerInteiro("Digite o ID do cliente: ");
        Cliente cliente = banco.buscarClientePorId(id);
 
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
        }
        return cliente;
    }
 
    // ---------------- PRODUTOS ----------------
 
    private void menuProdutos() {
        int opcao;
        do {
            System.out.println("\n--- PRODUTOS ---");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos ativos");
            System.out.println("3 - Pesquisar produto");
            System.out.println("4 - Editar produto");
            System.out.println("5 - Inativar produto");
            System.out.println("6 - Repor estoque");
            System.out.println("0 - Voltar");
 
            opcao = lerInteiro("Escolha: ");
 
            switch (opcao) {
                case 1: cadastrarProduto(); break;
                case 2: listarProdutos(); break;
                case 3: pesquisarProduto(); break;
                case 4: editarProduto(); break;
                case 5: inativarProduto(); break;
                case 6: reporEstoque(); break;
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
 
    private void cadastrarProduto() {
        String nome = lerObrigatorio("Nome: ");
        String categoria = lerObrigatorio("Categoria: ");
        String codigo = lerObrigatorio("Código: ");
 
        if (banco.buscarProdutoPorCodigo(codigo) != null) {
            System.out.println("Código já cadastrado.");
            return;
        }
 
        double preco = lerDecimal("Preço: ");
        int estoque = lerInteiro("Quantidade em estoque: ");
 
        if (preco <= 0 || estoque < 0) {
            System.out.println("Preço ou estoque inválido.");
            return;
        }
 
        String descricao = lerTexto("Descrição (opcional): ");
 
        Produto produto = banco.cadastrarProduto(nome, categoria,
                codigo, preco, estoque, descricao);
 
        System.out.println("Produto cadastrado!");
        System.out.println(produto.resumo());
    }
 
    private void listarProdutos() {
        boolean encontrou = false;
 
        for (Produto produto : banco.getProdutos()) {
            if (produto.isAtivo()) {
                System.out.println(produto.resumo());
                encontrou = true;
            }
        }
 
        if (!encontrou) {
            System.out.println("Nenhum produto ativo cadastrado.");
        }
    }
 
    private void pesquisarProduto() {
        String termo = lerTexto("Digite o nome ou código: ").toLowerCase();
        boolean encontrou = false;
 
        for (Produto produto : banco.getProdutos()) {
            if (produto.isAtivo()
                    && (produto.getNome().toLowerCase().contains(termo)
                    || produto.getCodigo().equalsIgnoreCase(termo))) {
                System.out.println(produto.resumo());
                encontrou = true;
            }
        }
 
        if (!encontrou) {
            System.out.println("Produto não encontrado.");
        }
    }
 
    private void editarProduto() {
        Produto produto = localizarProduto();
 
        if (produto == null) return;
        if (!produto.isAtivo()) {
            System.out.println("Não é possível editar um produto inativo.");
            return;
        }
 
        String nome = lerObrigatorio("Novo nome: ");
        String categoria = lerObrigatorio("Nova categoria: ");
        double preco = lerDecimal("Novo preço: ");
 
        if (preco <= 0) {
            System.out.println("O preço deve ser maior que zero.");
            return;
        }
 
        String descricao = lerTexto("Nova descrição: ");
 
        produto.atualizar(nome, categoria, preco, descricao);
        banco.salvar();
        System.out.println("Produto atualizado.");
    }
 
    private void inativarProduto() {
        Produto produto = localizarProduto();
 
        if (produto == null) return;
 
        String resposta = lerTexto("Confirma a inativação? (s/n): ");
        if (resposta.equalsIgnoreCase("s")) {
            produto.inativar();
            banco.salvar();
            System.out.println("Produto inativado.");
        }
    }
 
    private void reporEstoque() {
        Produto produto = localizarProduto();
 
        if (produto == null) return;
 
        int quantidade = lerInteiro("Quantidade para adicionar: ");
        if (quantidade <= 0) {
            System.out.println("A quantidade deve ser maior que zero.");
            return;
        }
 
        produto.adicionarEstoque(quantidade);
        banco.salvar();
        System.out.println("Estoque atualizado.");
    }
 
    private Produto localizarProduto() {
        int id = lerInteiro("Digite o ID do produto: ");
        Produto produto = banco.buscarProdutoPorId(id);
 
        if (produto == null) {
            System.out.println("Produto não encontrado.");
        }
        return produto;
    }
 
    // ---------------- VENDAS ----------------
 
    private void menuVendas() {
        int opcao;
        do {
            System.out.println("\n--- VENDAS ---");
            System.out.println("1 - Registrar venda");
            System.out.println("2 - Consultar histórico de vendas");
            System.out.println("0 - Voltar");
 
            opcao = lerInteiro("Escolha: ");
 
            switch (opcao) {
                case 1: registrarVenda(); break;
                case 2: listarVendas(); break;
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
 
    private void registrarVenda() {
        int idCliente = lerInteiro("ID do cliente: ");
        Cliente cliente = banco.buscarClientePorId(idCliente);
 
        if (cliente == null || !cliente.isAtivo()) {
            System.out.println("Cliente não encontrado ou inativo.");
            return;
        }
 
        int idProduto = lerInteiro("ID do produto: ");
        Produto produto = banco.buscarProdutoPorId(idProduto);
 
        if (produto == null || !produto.isAtivo()) {
            System.out.println("Produto não encontrado ou inativo.");
            return;
        }
 
        int quantidade = lerInteiro("Quantidade: ");
 
        if (quantidade <= 0 || quantidade > produto.getEstoque()) {
            System.out.println("Quantidade inválida ou estoque insuficiente.");
            return;
        }
 
        String loja = selecionarLoja();
        double total = produto.getPreco() * quantidade;
 
        System.out.println("Total da venda: R$ " + String.format("%.2f", total));
        String confirmar = lerTexto("Confirmar venda? (s/n): ");
 
        if (confirmar.equalsIgnoreCase("s")) {
            Venda venda = banco.registrarVenda(cliente, produto, quantidade, loja);
            System.out.println("Venda registrada!");
            System.out.println(venda.resumo());
        }
    }
 
    private void listarVendas() {
        if (banco.getVendas().isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
 
        for (Venda venda : banco.getVendas()) {
            System.out.println(venda.resumo());
        }
    }
 
    // ---------------- FINANCEIRO ----------------
 
    private void menuFinanceiro() {
        int opcao;
        do {
            System.out.println("\n--- FINANCEIRO ---");
            System.out.println("1 - Registrar entrada ou saída");
            System.out.println("2 - Consultar movimentações e saldo");
            System.out.println("0 - Voltar");
 
            opcao = lerInteiro("Escolha: ");
 
            switch (opcao) {
                case 1: registrarMovimentacao(); break;
                case 2: consultarFinanceiro(); break;
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
 
    private void registrarMovimentacao() {
        String tipo = lerTexto("Digite E para entrada ou S para saída: ");
 
        if (!tipo.equalsIgnoreCase("E") && !tipo.equalsIgnoreCase("S")) {
            System.out.println("Tipo inválido.");
            return;
        }
 
        String descricao = lerObrigatorio("Descrição: ");
        double valor = lerDecimal("Valor: ");
 
        if (valor <= 0) {
            System.out.println("O valor deve ser maior que zero.");
            return;
        }
 
        if (tipo.equalsIgnoreCase("E")) {
            banco.registrarMovimentacao("ENTRADA", descricao, valor);
        } else {
            banco.registrarMovimentacao("SAÍDA", descricao, valor);
        }
 
        System.out.println("Movimentação registrada.");
    }
 
    private void consultarFinanceiro() {
        double saldo = 0;
 
        if (banco.getMovimentacoes().isEmpty()) {
            System.out.println("Nenhuma movimentação registrada.");
            return;
        }
 
        for (MovimentacaoFinanceira movimentacao : banco.getMovimentacoes()) {
            System.out.println(movimentacao.resumo());
 
            if (movimentacao.getTipo().equals("ENTRADA")) {
                saldo += movimentacao.getValor();
            } else {
                saldo -= movimentacao.getValor();
            }
        }
 
        System.out.println("Saldo calculado: R$ " + String.format("%.2f", saldo));
    }
 
    // ---------------- MÉTODOS DE APOIO ----------------
 
    private String selecionarLoja() {
        System.out.println("Selecione a unidade:");
        for (int i = 0; i < lojas.length; i++) {
            System.out.println((i + 1) + " - " + lojas[i]);
        }
 
        int opcao;
        do {
            opcao = lerInteiro("Número da loja (1 a 4): ");
            if (opcao < 1 || opcao > 4) {
                System.out.println("Escolha uma loja entre 1 e 4.");
            }
        } while (opcao < 1 || opcao > 4);
 
        return lojas[opcao - 1];
    }
 
    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }
 
    private String lerObrigatorio(String mensagem) {
        String texto;
 
        do {
            texto = lerTexto(mensagem);
            if (texto.isEmpty()) {
                System.out.println("Este campo é obrigatório.");
            }
        } while (texto.isEmpty());
 
        return texto;
    }
 
    private int lerInteiro(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(mensagem));
            } catch (NumberFormatException erro) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }
 
    private double lerDecimal(String mensagem) {
        while (true) {
            try {
                String valor = lerTexto(mensagem).replace(",", ".");
                return Double.parseDouble(valor);
            } catch (NumberFormatException erro) {
                System.out.println("Digite um valor numérico válido.");
            }
        }
    }
}
