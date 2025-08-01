package org.example.dao;

import org.example.domain.Cliente;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ClienteSetDAO implements IClienteDAO {
    private Set<Cliente> set;

    public ClienteSetDAO() {
        this.set = new HashSet<>();
    }

    @Override
    public Boolean cadastrar(Cliente cliente) {
        return this.set.add(cliente); // Não adiciona se já existir (com base em equals/hashCode)
    }

    @Override
    public void excluir(Long cpf) {
        Cliente cliente = buscarPorCpf(cpf);
        if (cliente != null) {
            this.set.remove(cliente);
        }
    }

    @Override
    public void alterar(Cliente cliente) {
        Cliente clienteExistente = buscarPorCpf(cliente.getCpf());
        if (clienteExistente != null) {
            set.remove(clienteExistente); // Remove o antigo
            set.add(cliente); // Adiciona o novo (atualizado)
        }
    }

    @Override
    public Cliente consultar(Long cpf) {
        return buscarPorCpf(cpf);
    }

    @Override
    public Collection<Cliente> buscartodos() {
        return this.set;
    }

    private Cliente buscarPorCpf(Long cpf) {
        for (Cliente c : set) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }
}
