package dao;

import model.ModeloCarro;
import model.Montadora;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloCarroDAO {

    public void salvar(ModeloCarro modelo) throws SQLException {
        if (modelo.getMontadora() == null || modelo.getMontadora().getId() <= 0) {
            throw new IllegalArgumentException("O modelo precisa estar associado a uma montadora já salva.");
        }

        String sql = "INSERT INTO modelo_carro (nome, tipo_combustivel, potencia_cv, montadora_id) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, modelo.getNome());
            stmt.setString(2, modelo.getTipoCombustivel());
            stmt.setInt(3, modelo.getPotenciaCv());
            stmt.setInt(4, modelo.getMontadora().getId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) modelo.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar modelo de carro: " + e.getMessage());
            throw e;
        }
    }

    public ModeloCarro buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT mc.id AS modelo_id, mc.nome AS modelo_nome,
                   mc.tipo_combustivel, mc.potencia_cv,
                   m.id AS montadora_id, m.nome AS montadora_nome, m.pais_sede
            FROM modelo_carro mc
            INNER JOIN montadora m ON m.id = mc.montadora_id
            WHERE mc.id = ?
            """;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapearComMontadora(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar modelo: " + e.getMessage());
            throw e;
        }
        return null;
    }

    
    public List<ModeloCarro> listarTodos() throws SQLException {
        String sql = """
            SELECT mc.id AS modelo_id, mc.nome AS modelo_nome,
                   mc.tipo_combustivel, mc.potencia_cv,
                   m.id AS montadora_id, m.nome AS montadora_nome, m.pais_sede
            FROM modelo_carro mc
            INNER JOIN montadora m ON m.id = mc.montadora_id
            ORDER BY m.nome, mc.nome
            """;
        List<ModeloCarro> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapearComMontadora(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar modelos: " + e.getMessage());
            throw e;
        }
        return lista;
    }

    public boolean atualizar(ModeloCarro modelo) throws SQLException {
        if (modelo.getMontadora() == null || modelo.getMontadora().getId() <= 0) {
            throw new IllegalArgumentException("Informe uma montadora válida.");
        }
        String sql = "UPDATE modelo_carro SET nome = ?, tipo_combustivel = ?, " +
                     "potencia_cv = ?, montadora_id = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, modelo.getNome());
            stmt.setString(2, modelo.getTipoCombustivel());
            stmt.setInt(3, modelo.getPotenciaCv());
            stmt.setInt(4, modelo.getMontadora().getId());
            stmt.setInt(5, modelo.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar modelo: " + e.getMessage());
            throw e;
        }
    }

    public boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM modelo_carro WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir modelo: " + e.getMessage());
            throw e;
        }
    }

    private ModeloCarro mapearComMontadora(ResultSet rs) throws SQLException {
        Montadora montadora = new Montadora(
            rs.getInt("montadora_id"),
            rs.getString("montadora_nome"),
            rs.getString("pais_sede")
        );
        return new ModeloCarro(
            rs.getInt("modelo_id"),
            rs.getString("modelo_nome"),
            rs.getString("tipo_combustivel"),
            rs.getInt("potencia_cv"),
            montadora
        );
    }
}
