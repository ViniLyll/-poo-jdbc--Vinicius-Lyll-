package dao;

import model.ModeloCarro;
import model.Montadora;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MontadoraDAO {

    public void salvar(Montadora montadora) throws SQLException {
        String sql = "INSERT INTO montadora (nome, pais_sede) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, montadora.getNome());
            stmt.setString(2, montadora.getPaisSede());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) montadora.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar montadora: " + e.getMessage());
            throw e;
        }
    }

    public Montadora buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nome, pais_sede FROM montadora WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Montadora montadora = mapearMontadora(rs);
                    montadora.setModelos(buscarModelosDaMontadora(id, conn));
                    return montadora;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar montadora: " + e.getMessage());
            throw e;
        }
        return null;
    }

    private List<ModeloCarro> buscarModelosDaMontadora(int idMontadora, Connection conn) throws SQLException {
        String sql = "SELECT mc.id, mc.nome, mc.tipo_combustivel, mc.potencia_cv " +
                     "FROM modelo_carro mc WHERE mc.montadora_id = ? ORDER BY mc.nome";
        List<ModeloCarro> modelos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMontadora);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Montadora referencia = new Montadora(idMontadora, "", "");
                    modelos.add(new ModeloCarro(
                        rs.getInt("id"), rs.getString("nome"),
                        rs.getString("tipo_combustivel"), rs.getInt("potencia_cv"), referencia
                    ));
                }
            }
        }
        return modelos;
    }

    public List<Montadora> listarTodos() throws SQLException {
        String sql = "SELECT id, nome, pais_sede FROM montadora ORDER BY nome";
        List<Montadora> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapearMontadora(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar montadoras: " + e.getMessage());
            throw e;
        }
        return lista;
    }

    public boolean atualizar(Montadora montadora) throws SQLException {
        String sql = "UPDATE montadora SET nome = ?, pais_sede = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, montadora.getNome());
            stmt.setString(2, montadora.getPaisSede());
            stmt.setInt(3, montadora.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar montadora: " + e.getMessage());
            throw e;
        }
    }

    public boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM montadora WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir montadora. Verifique se há modelos vinculados: " + e.getMessage());
            throw e;
        }
    }

    private Montadora mapearMontadora(ResultSet rs) throws SQLException {
        return new Montadora(rs.getInt("id"), rs.getString("nome"), rs.getString("pais_sede"));
    }
}
