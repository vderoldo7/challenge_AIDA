package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Consulta;
import org.acme.model.Contato;
import org.acme.model.Exame;
import org.acme.model.Paciente;
import org.acme.service.*;

import java.sql.SQLException;
import java.util.List;

@Path("/main")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GreetingResource {

    @Inject
    PacienteService pacienteService;

    @Inject
    MensagemService mensagemService;

    @Inject
    ExameService exameService;

    @Inject
    ConsultaService consultaService;

    @Inject
    ContatoService contatoService;

    //=========================== PACIENTE ===============================\\

    @POST
    @Path("/paciente")
    public Response cadastrarPaciente(Paciente paciente) {
        try {
            boolean sucesso = pacienteService.cadastroPaciente(paciente);
            if (sucesso) {
                return Response.status(Response.Status.CREATED)
                        .entity("Paciente cadastrado com sucesso!").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Dados inválidos! Verifique nome, CPF, idade, gênero ou plano.").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro no banco de dados: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro inesperado: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/pacientes")
    public Response listarPacientes() {
        try {
            List<Paciente> lista = pacienteService.listarPacientes();
            return Response.ok(lista).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar pacientes: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/pacientes/{id}")
    public Response atualizarPaciente(@PathParam("id") int id, Paciente paciente) {
        try {
            boolean sucesso = pacienteService.atualizarPaciente(
                    id,
                    paciente.getNome(),
                    paciente.getCpf(),
                    paciente.getIdade(),
                    paciente.getGenero(),
                    paciente.getPlano()
            );
            if (sucesso) {
                return Response.ok("Paciente atualizado com sucesso!").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Paciente não encontrado ou dados inválidos.").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar paciente: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/paciente/{id}")
    public Response deletarPaciente(@PathParam("id") int id) {
        try {
            boolean sucesso = pacienteService.deletarPaciente(id);
            if (sucesso) {
                return Response.ok("Paciente removido com sucesso!").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Paciente não encontrado.").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar paciente: " + e.getMessage()).build();
        }
    }

    //=========================== MENSAGEM ===============================\\

    @POST
    @Path("/mensagem")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response enviarMensagem(String mensagem) {
        try {
            mensagemService.enviarMensagem(mensagem);
            return Response.ok("Mensagem enviada com sucesso: " + mensagemService.getConteudo()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao enviar mensagem: " + e.getMessage()).build();
        }
    }

    //=========================== EXAMES ===============================\\

    @GET
    @Path("/exames")
    public Response listarExames() {
        try {
            List<Exame> lista = exameService.listarExames();
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar exames: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/exame")
    public Response cadastrarExame(Exame exame) {
        try {
            exameService.cadastrarExame(exame);
            return Response.status(Response.Status.CREATED)
                    .entity("Exame cadastrado com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao cadastrar exame: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/exames/{id}")
    public Response deletarExame(@PathParam("id") int id) {
        try {
            exameService.deletarExame(id);
            return Response.ok("Exame deletado com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar exame: " + e.getMessage()).build();
        }
    }

    //=========================== CONSULTAS ===============================\\

    @GET
    @Path("/consultas")
    public Response listarConsultas() {
        try {
            List<Consulta> lista = consultaService.listarConsultas();
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar consultas: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/consulta")
    public Response cadastrarConsulta(Consulta consulta) {
        try {
            consultaService.cadastrarConsulta(consulta);
            return Response.status(Response.Status.CREATED)
                    .entity("Consulta cadastrada com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao cadastrar consulta: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/consultas/{id}")
    public Response cancelarConsulta(@PathParam("id") int id) {
        try {
            consultaService.cancelarConsulta(id);
            return Response.ok("Consulta cancelada com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao cancelar consulta: " + e.getMessage()).build();
        }
    }

    //=========================== CONTATO ===============================\\

    @POST
    @Path("/contato")
    public Response criarContato(Contato contato) {
        try {
            boolean criado = contatoService.cadastrarContato(contato);
            if (criado) {
                return Response.status(Response.Status.CREATED)
                        .entity("Contato criado com sucesso!")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Falha ao criar contato! Verifique os campos obrigatórios.")
                        .build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro no banco de dados: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro inesperado: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/contatos")
    public Response listarContatos() {
        try {
            List<Contato> contatos = contatoService.listarContatos();
            return Response.ok(contatos).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar contatos: " + e.getMessage())
                    .build();
        }
    }

    // Deletar Contato por ID
    @DELETE
    @Path("/contato/{id}")
    public Response deletarContato(@PathParam("id") int id) {
        try {
            boolean deletado = contatoService.deletarContato(id);
            if (deletado) {
                return Response.ok("Contato deletado com sucesso!").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Contato não encontrado ou ID inválido.")
                        .build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar contato: " + e.getMessage())
                    .build();
        }
    }
}
