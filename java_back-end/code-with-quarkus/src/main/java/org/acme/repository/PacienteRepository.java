package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Paciente;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PacienteRepository {

    @Inject
    DataSource dataSource;

    public void inserirDadosP(Paciente paciente) throws SQLException {
        String sql ="insert into pacientes(id,nome,cpf,idade,genero,plano) values(?,?,?,?,?,?)";
        try(Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)){
            st.setInt(1,paciente.getId());
            st.setString(2,paciente.getNome());
            st.setString(3, paciente.getCpf());
            st.setInt(4,paciente.getIdade());
            st.setString(5, String.valueOf(paciente.getGenero()));
            st.setString(6,paciente.getPlano());
            int linhasAfetadas= st.executeUpdate();
            if (linhasAfetadas>0){
                System.out.println("Cadastro feito");
            }else {
                System.out.println("Erro!");
            }

        }
    }

    public List<Paciente> procurarConteudoP() throws SQLException {
        String sql = "select * from pacientes";
        List<Paciente> lista = new ArrayList<>();
        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery(sql);
            while(rs.next()){
                Paciente paciente = new Paciente();
                System.out.println("ID: "+rs.getInt(1));
                System.out.println("Nome: "+rs.getString(2));
                System.out.println("CPF: "+rs.getString(3));
                System.out.println("Idade: " +rs.getInt(4));
                System.out.println("Genero: " +rs.getString(5));
                System.out.println("Plano: "+rs.getString(6));
                lista.add(paciente);
            }

        }
        return lista;
    }

    public boolean updateDadosP(int id,String nome, String cpf,int idade, String genero ,String plano) throws SQLException {
        String sql="UPDATE pacientes SET nome = ?, cpf = ?,genero=?,idade=?, plano= ? WHERE id = ?";
        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,nome);
            ps.setString(2,cpf);
            ps.setString(3,genero);
            ps.setInt(4,idade);
            ps.setString(5,plano);
            ps.setInt(6,id);
            int linhasAfetadas= ps.executeUpdate();
            return linhasAfetadas>0;

        }

    }

    public boolean cancelarCadastro(int id) throws SQLException {
        String sql="DELETE FROM pacientes WHERE id = ?";
        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        }
    }


}
