package org.example;

import org.example.dao.ClienteSetDAO;
import org.example.dao.IClienteDAO;
import org.example.domain.Cliente;

import javax.swing.*;

public class App {
    private static IClienteDAO iClienteDAO;

    public static void main(String[] args) {
        iClienteDAO = new ClienteSetDAO();

        String options = solicitarOpcao();

        while (isOptionValida(options)) {
            if (isOpcaoSair(options)) {
                sair();
            }else if (isCadastro(options)) { //cadastrar usuario
                String nome = JOptionPane.showInputDialog(null,
                        "Digite: Nome",
                        "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                Long cpf = null;
                try {
                    cpf = Long.parseLong(JOptionPane.showInputDialog(null,
                            "Digite: CPF",
                            "Cadastro", JOptionPane.INFORMATION_MESSAGE));
                } catch (NumberFormatException e) {
                    mostrarMensagem("CPF inválido!", "Erro");
                    options = solicitarOpcao();
                    continue;
                }

                Long telefone = null;
                try {
                    telefone = Long.parseLong(JOptionPane.showInputDialog(null,
                            "Digite: Telefone",
                            "Cadastro", JOptionPane.INFORMATION_MESSAGE));
                } catch (NumberFormatException e) {
                    mostrarMensagem("Telefone inválido!", "Erro");
                    options = solicitarOpcao();
                    continue;


                }
                String cidade = JOptionPane.showInputDialog(null,
                        "Digite: Cidade",
                        "Cadastro", JOptionPane.INFORMATION_MESSAGE);

                cadastrar(nome, cpf, telefone, cidade);

            }else if(isConsultar(options)){  //Consultar cpf
                String dados = JOptionPane.showInputDialog(null,
                        "Digite: CPF",
                        "Consulta", JOptionPane.INFORMATION_MESSAGE);
                consultar(dados);

            } else if (isExcluir(options)) {
                String dados = JOptionPane.showInputDialog(null,
                        "Digite: CPF",
                        "Excluir", JOptionPane.INFORMATION_MESSAGE);
                excluirCadastro(dados);
            }else if(isAlterar(options)){
                String dados = JOptionPane.showInputDialog(null,
                        "Digite: CPF",
                        "Alterar", JOptionPane.INFORMATION_MESSAGE);
                alterarCadastro(dados);
            }

            options = solicitarOpcao();
        }

        sair(); // Caso saia do while sem opção válida
    }


    private static String solicitarOpcao() {
        String options = JOptionPane.showInputDialog(null,
                "Digite:\n" +
                        "1 - Cadastrar\n" +
                        "2 - Consultar\n" +
                        "3 - Excluir\n" +
                        "4 - Alterar\n" +
                        "5 - Sair",
                "Menu", JOptionPane.INFORMATION_MESSAGE);

        while (!isOptionValida(options)) {
            if (options == null || options.trim().isEmpty()) {
                sair();
            }

            options = JOptionPane.showInputDialog(null,
                    "Opção inválida! Digite novamente:\n" +
                            "1 - Cadastrar\n" +
                            "2 - Consultar\n" +
                            "3 - Excluir\n" +
                            "4 - Alterar\n" +
                            "5 - Sair",
                    "Menu", JOptionPane.WARNING_MESSAGE);
        }

        return options;
    }
    public static void mostrarMensagem(String mensagem, String titulo) {
        JOptionPane.showMessageDialog(null,
                mensagem,
                titulo,
                JOptionPane.INFORMATION_MESSAGE);
    }


    public static boolean isCadastro(String opcao) {
        return "1".equals(opcao);
    }
    private static boolean isConsultar(String options) {
       return "2".equals(options);

    }
    private static boolean isExcluir(String options) {
        return "3".equals(options);
    }
    private static boolean isAlterar(String options) {
        return "4".equals(options);
    }
    public static boolean isOpcaoSair(String opcao) {
        return "5".equals(opcao);
    }

    public static boolean isOptionValida(String opcao) {
        return opcao != null && (
                "1".equals(opcao) || "2".equals(opcao) ||
                        "3".equals(opcao) || "4".equals(opcao) || "5".equals(opcao));
    }
    public static boolean isEmpty(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
    public static boolean isNull(Long valor) {
        return valor == null;
    }


    public static Cliente cadastrar(String nome, Long cpf, Long telefone, String cidade) {
        nome = isEmpty(nome) ? null : nome;
        cpf = isNull(cpf) ? null : cpf;
        telefone = isNull(telefone) ? null : telefone;
        cidade = isEmpty(cidade) ? null : cidade;

        Cliente cliente = new Cliente(nome, cpf, telefone, cidade);

        Boolean isCadastrado = iClienteDAO.cadastrar(cliente);

        if (isCadastrado) {
            mostrarMensagem("Cadastrado com sucesso!", "Sucesso");
        } else {
            mostrarMensagem("Cliente já cadastrado.", "Atenção");
        }

        return cliente;
    }
    private static void consultar(String dados) {
        Cliente cliente = iClienteDAO.consultar(Long.parseLong(dados));
        if( cliente != null){
            JOptionPane.showMessageDialog(null,
                    "  Cliente Encontrado:  " + cliente.toString(),
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null,
                    "Cliente não Encontrado:",
                    "Sem Registro", JOptionPane.INFORMATION_MESSAGE);}
    }
    private static void excluirCadastro(String dados) {
        Cliente cliente = iClienteDAO.consultar(Long.parseLong(dados));
        if(cliente != null){
            iClienteDAO.excluir(Long.parseLong(dados));
            JOptionPane.showMessageDialog(null,
                    "Cliente Excluido:",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null,
                    "Cliente não encontrado:",
                    "Falha", JOptionPane.INFORMATION_MESSAGE);
        }


    }
    private static void alterarCadastro(String dados) {
        Cliente cliente = iClienteDAO.consultar(Long.parseLong(dados));
        if (cliente != null) {
            String novoNome = JOptionPane.showInputDialog(null,
                    "Digite o novo nome:",
                    "Alterar", JOptionPane.INFORMATION_MESSAGE);
            String novoTelefoneStr = JOptionPane.showInputDialog(null,
                    "Digite o novo telefone:",
                    "Alterar", JOptionPane.INFORMATION_MESSAGE);
            String novaCidade = JOptionPane.showInputDialog(null,
                    "Digite a nova cidade:",
                    "Alterar", JOptionPane.INFORMATION_MESSAGE);

            Long novoTelefone = null;
            try {
                novoTelefone = Long.parseLong(novoTelefoneStr);
            } catch (NumberFormatException e) {
                mostrarMensagem("Telefone inválido!", "Erro");
                return;
            }

            // Atualiza os dados do cliente
            cliente.setNome(novoNome);
            cliente.setTel(novoTelefone);
            cliente.setCidade(novaCidade);

            // Chama DAO para alterar
            iClienteDAO.alterar(cliente);

            JOptionPane.showMessageDialog(null,
                    "Cliente alterado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Cliente não encontrado.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void sair() {
        JOptionPane.showMessageDialog(null,
                "Até logo!",
                "Saindo", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}


