package model;

import controller.ConexaoMySQL;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Equipamento {
   public void inserirEquipamento(String nome, String descricao, double valor_diario, boolean status, int quantidade) {
       String sql = "INSERT INTO equipamento (nome, descricao, valor_diario, status, quantidade) VALUES (?, ?, ?, ?, ?)";
       try (Connection conn = ConexaoMySQL.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setDouble(3, valor_diario);
            stmt.setBoolean(4, status);
            stmt.setInt(5, quantidade);
            stmt.executeUpdate();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   
   public void atualizarEquipamento(String nome, int id, String descricao, double valor_diario, boolean status, int quantidade) {
        String sql = "UPDATE usuarios SET nome=?, descricao=?, valor_diario=?, status=?, quantidade=? WHERE id=?";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setDouble(3, valor_diario);
            stmt.setBoolean(4, status);
            stmt.setInt(5, quantidade);
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluirEquipamento(int id) {
        String sql = "DELETE FROM equipamento WHERE id=?";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarEquipamentos(DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT * FROM equipamento";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("nome"), rs.getInt("id"), rs.getString("descricao"), rs.getDouble("valor_diario"), rs.getBoolean("status"), rs.getInt("quantidade")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}