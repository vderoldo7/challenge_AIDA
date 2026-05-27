package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Paciente;
import org.acme.repository.PacienteRepository;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class PacienteService {

    @Inject
    PacienteRepository pacienteRepository;

    public boolean cadastroPaciente(Paciente paciente) throws SQLException {
        if (paciente.getId() <= 0) {
            return false;
        }
        if (paciente.getNome() == null || paciente.getNome().isEmpty()) {
            return false;
        }
        if (paciente.getCpf() == null || paciente.getCpf().isEmpty()) {
            return false;
        }
        if (paciente.getIdade() <= 0) {
            return false;
        }
        if (paciente.getGenero() ==  null || paciente.getGenero().isEmpty()) {
            return false;
        }
        if (paciente.getPlano() == null || paciente.getPlano().isEmpty()) {
            return false;
        }
        pacienteRepository.inserirDadosP(paciente);
        return true;
    }

    public List<Paciente> listarPacientes() throws SQLException {
        return pacienteRepository.procurarConteudoP();
    }

    public boolean atualizarPaciente(int id, String nome, String cpf, int idade, String genero, String plano) throws SQLException {
        if (id <= 0 || nome.isEmpty() || cpf.isEmpty() || idade <= 0 || plano.isEmpty()||genero.isEmpty()) {
            return false;
        }
        return pacienteRepository.updateDadosP(id, nome, cpf, idade, genero, plano);
    }

    public boolean deletarPaciente(int id) throws SQLException {
        if (id <= 0) {
            return false;
        }
        return pacienteRepository.cancelarCadastro(id);
    }


}