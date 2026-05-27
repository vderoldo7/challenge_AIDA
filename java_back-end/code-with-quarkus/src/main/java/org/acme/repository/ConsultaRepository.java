package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Consulta;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ConsultaRepository {

    @Inject
    DataSource dataSource;

    public void inserirConsulta(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO consultas (tipo, data, hora) VALUES (?,?,?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, consulta.getTipo());
            st.setInt(2, consulta.getData());
            st.setInt(3, consulta.getHora());

            int linhasAfetadas = st.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Consulta cadastrada com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar consulta!");
            }
        }
    }

    public List<Consulta> listarConsultas() throws SQLException {
        String sql = "SELECT * FROM consultas";
        List<Consulta> lista = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Consulta consulta = new Consulta();

                System.out.println("ID: " + rs.getInt(1));
                System.out.println("Tipo: " + rs.getString(2));
                System.out.println("Data: " + rs.getInt(3));
                System.out.println("Hora: " + rs.getInt(4));

                consulta.setTipo(rs.getString(2));
                consulta.setData(rs.getInt(3));
                consulta.setHora(rs.getInt(4));

                lista.add(consulta);
            }
        }

        return lista;
    }


    public boolean cancelarConsulta(int id) throws SQLException {
        String sql = "DELETE FROM consultas WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;
        }
    }
}
