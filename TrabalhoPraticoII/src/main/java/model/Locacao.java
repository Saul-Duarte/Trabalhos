/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import controller.ConexaoMySQL;
import java.sql.Connection;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sauls
 */
public class Locacao {
public void inserirLocacao(Date dataInicio, Date dataTermino, double multa) {
        String sql = "INSERT INTO locacoes (data_inicio, data_termino, multa, equipamento_id, cliente_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, dataInicio);
            stmt.setDate(2, dataTermino);
            stmt.setDouble(3, multa);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizarLocacao(int id, Date dataInicio, Date dataTermino, double multa, int equipamentoId, int clienteId) {
        String sql = "UPDATE locacoes SET data_inicio=?, data_termino=?, multa=?, equipamento_id=?, cliente_id=? WHERE id=?";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, dataInicio);
            stmt.setDate(2, dataTermino);
            stmt.setDouble(3, multa);
            stmt.setInt(4, equipamentoId);
            stmt.setInt(5, clienteId);
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluirLocacao(int id) {
        String sql = "DELETE FROM locacoes WHERE id=?";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarLocacoes(DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT * FROM locacoes";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getDate("data_inicio"),
                    rs.getDate("data_termino"),
                    rs.getBigDecimal("multa"),
                    rs.getInt("equipamento_id"),
                    rs.getInt("cliente_id")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}