package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Exame;
import org.acme.repository.ExameRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ExameService {

    @Inject
    ExameRepository exameRepository;
    public void cadastrarExame(Exame exame) {
        try {
            exameRepository.inserirExame(exame);
            System.out.println("Exame cadastrado com sucesso no Service!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar exame no Service: " + e.getMessage());
        }
    }

    public List<Exame> listarExames() {
        try {
            return exameRepository.listarExames();
        } catch (SQLException e) {
            System.out.println(" Erro ao listar exames: " + e.getMessage());
            return null;
        }
    }

    public void deletarExame(int id) {
        try {
            boolean deletado = exameRepository.deletarExame(id);
            if (deletado) {
                System.out.println("Exame deletado com sucesso!");
            } else {
                System.out.println(" Nenhum exame encontrado para deletar.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar exame: " + e.getMessage());
        }
    }




}
