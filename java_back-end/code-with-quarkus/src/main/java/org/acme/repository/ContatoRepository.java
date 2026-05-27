package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Contato;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ContatoRepository {

    @Inject
    DataSource dataSource;

    public void inserirContato(Contato contato) throws SQLException {
        String sql = "INSERT INTO contatos (nome, email, sexo, nascimento, cpf, telefone, mensagem) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, contato.getNome());
            st.setString(2, contato.getEmail());
            st.setString(3, contato.getSexo());
            st.setString(4, contato.getNascimento());
            st.setString(5, contato.getCpf());
            st.setString(6, contato.getTelefone());
            st.setString(7, contato.getMensagem());

            int linhasAfetadas = st.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Contato cadastrado com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar contato!");
            }
        }
    }

    public List<Contato> listarContatos() throws SQLException {
        String sql = "SELECT * FROM contatos";
        List<Contato> lista = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Contato contato = new Contato();

                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Sexo: " + rs.getString("sexo"));
                System.out.println("Nascimento: " + rs.getString("nascimento"));
                System.out.println("CPF: " + rs.getString("cpf"));
                System.out.println("Telefone: " + rs.getString("telefone"));
                System.out.println("Mensagem: " + rs.getString("mensagem"));

                contato.setId(rs.getInt("id"));
                contato.setNome(rs.getString("nome"));
                contato.setEmail(rs.getString("email"));
                contato.setSexo(rs.getString("sexo"));
                contato.setNascimento(rs.getString("nascimento"));
                contato.setCpf(rs.getString("cpf"));
                contato.setTelefone(rs.getString("telefone"));
                contato.setMensagem(rs.getString("mensagem"));

                lista.add(contato);
            }
        }
        return lista;
    }

    public boolean deletarContato(int id) throws SQLException {
        String sql = "DELETE FROM contatos WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;
        }
    }



}
