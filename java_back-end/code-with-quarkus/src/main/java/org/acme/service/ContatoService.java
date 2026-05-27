package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Contato;
import org.acme.repository.ContatoRepository;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class ContatoService {

    @Inject
    ContatoRepository contatoRepository;

    public boolean cadastrarContato(Contato contato) throws SQLException {
        if (contato.getNome() == null || contato.getNome().isEmpty()) {
            System.out.println("Erro: Nome é obrigatório.");
            return false;
        }
        if (contato.getEmail() == null || contato.getEmail().isEmpty()) {
            System.out.println("Erro: Email é obrigatório.");
            return false;
        }
        if (contato.getCpf() == null || contato.getCpf().isEmpty()) {
            System.out.println("Erro: CPF é obrigatório.");
            return false;
        }
        if (contato.getTelefone() == null || contato.getTelefone().isEmpty()) {
            System.out.println("Erro: Telefone é obrigatório.");
            return false;
        }
        if (contato.getMensagem() == null || contato.getMensagem().isEmpty()) {
            System.out.println("Erro: Mensagem é obrigatória.");
            return false;
        }

        contatoRepository.inserirContato(contato);
        return true;
    }

    public List<Contato> listarContatos() throws SQLException {
        return contatoRepository.listarContatos();
    }

    public boolean deletarContato(int id) throws SQLException {
        if (id <= 0) {
            System.out.println("Erro: ID inválido.");
            return false;
        }
        return contatoRepository.deletarContato(id);
    }
}
