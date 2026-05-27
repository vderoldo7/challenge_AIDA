package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Consulta;
import org.acme.repository.ConsultaRepository;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class ConsultaService {

    @Inject
    ConsultaRepository consultaRepository;

    public void cadastrarConsulta(Consulta consulta) {
        try {
            consultaRepository.inserirConsulta(consulta);
            System.out.println("Consulta cadastrada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar consulta: " + e.getMessage());
        }
    }

    public List<Consulta> listarConsultas() {
        try {
            return consultaRepository.listarConsultas();
        } catch (SQLException e) {
            System.out.println("Erro ao listar consultas: " + e.getMessage());
            return null;
        }
    }

    public void cancelarConsulta(int id) {
        try {
            boolean cancelado = consultaRepository.cancelarConsulta(id);
            if (cancelado) {
                System.out.println("Consulta cancelada (deletada) com sucesso!");
            } else {
                System.out.println("Nenhuma consulta encontrada com esse ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cancelar consulta: " + e.getMessage());
        }
    }


}
