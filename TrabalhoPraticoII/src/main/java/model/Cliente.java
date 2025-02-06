package model;

import controller.ConexaoMySQL;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Cliente {
    public void inserirCliente (String nome, String CPF, String telefone) throws SQLException {
        String sql = "INSERT INTO cliente (nome, CPF, telefone) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, CPF);
            stmt.setString(3, telefone);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void atualizarCliente (int id, String nome, String CPF, String telefone) {
        String sql = "UPDATE cliente SET nome=?, CPF=?, telefone=? WHERE id=?";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, CPF);
            stmt.setString(3, telefone);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void excluirCliente(int id) {
        String sql = "DELETE FROM cliente WHERE id=?";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarCliente(DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT * FROM cliente";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("nome"), rs.getString("CPF"), rs.getString("telefone")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}