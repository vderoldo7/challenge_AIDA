package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Exame;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ExameRepository {

    @Inject
    DataSource dataSource;

    public void inserirExame(Exame exame) throws SQLException {
        String sql = "INSERT INTO exames (tipo, data, hora) VALUES (?,?,?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, exame.getTipo());
            st.setInt(2, exame.getData());
            st.setInt(3, exame.getHora());

            int linhasAfetadas = st.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Exame cadastrado com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar exame!");
            }
        }
    }

    public List<Exame> listarExames() throws SQLException {
        String sql = "SELECT * FROM exames";
        List<Exame> lista = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Exame exame = new Exame();

                System.out.println("ID: " + rs.getInt(1));
                System.out.println("Tipo: " + rs.getString(2));
                System.out.println("Data: " + rs.getInt(3));
                System.out.println("Hora: " + rs.getInt(4));

                exame.setTipo(rs.getString(2));
                exame.setData(rs.getInt(3));
                exame.setHora(rs.getInt(4));

                lista.add(exame);
            }
        }

        return lista;
    }


    public boolean deletarExame(int id) throws SQLException {
        String sql = "DELETE FROM exames WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;
        }
    }



}
