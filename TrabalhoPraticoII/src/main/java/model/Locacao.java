/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import controller.ConexaoMySQL;
import java.sql.Connection;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sauls
 */
public class Locacao {
    public void inserirLocacao(LocalDate dataInicio, LocalDate dataTermino, double multa) {
        java.sql.Date dataComeco = java.sql.Date.valueOf(dataInicio);
        java.sql.Date dataFim = java.sql.Date.valueOf(dataTermino);
        String sql = "INSERT INTO locacoes (data_inicio, data_termino, multa, equipamento_id, cliente_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoMySQL.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, dataComeco);
            stmt.setDate(2, dataFim);
            stmt.setDouble(3, multa);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void atualizarLocacao(int id, Date dataTermino) {
        String sql = "UPDATE locacoes SET data_termino=? WHERE id=?";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, dataTermino);
            stmt.setInt(2, id);
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
    
    public long calcularDiasAluguel(int id) {
        String sql = "SELECT DATEDIFF(data_termino, data_inicio) AS diasAluguel FROM locacao WHERE id = ?";
        try (Connection conn = ConexaoMySQL.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
            stmt.setLong(1, id);
            if (rs.next()) {
                return rs.getLong("diasAluguel");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public double calcularValorTotal(int locacaoId) {
        double total = 0;
        String sqlEquipamentos = "SELECT e.valor_diario FROM equipamentos e " +
                                 "JOIN locacoes l ON e.id = l.equipamento_id WHERE l.id = ?";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sqlEquipamentos)) {

            stmt.setInt(1, locacaoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    double valorDiario = rs.getDouble("valor_diario");
                    total += valorDiario;
                }
            }

            // Calculando os dias de aluguel
            long diasAluguel = calcularDiasAluguel(locacaoId);
            total *= diasAluguel;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public double obterMultaDiaria(int locacaoId) {
        String sql = "SELECT multa FROM locacoes WHERE id = ?";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, locacaoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("multa");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    
    public Date obterDataTermino(int locacaoId) {
        String sql = "SELECT data_termino FROM locacoes WHERE id = ?";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, locacaoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDate("data_termino");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Retorna null se não encontrar a data
    }

    
    public double calcularMulta(int locacaoId, Date dataDevolucao) {
        double multaTotal = 0;
        // Recuperando a data de término diretamente do banco de dados
        Date dataTermino = obterDataTermino(locacaoId);

        // Calculando os dias de atraso
        long diasAtraso = ChronoUnit.DAYS.between(dataTermino.toLocalDate(), dataDevolucao.toLocalDate());

        if (diasAtraso > 0) {
            // Calculando a multa apenas se houver atraso
            String sqlEquipamentos = "SELECT e.valor_diario FROM equipamentos e " +
                                     "JOIN locacoes l ON e.id = l.equipamento_id WHERE l.id = ?";
            try (Connection conn = ConexaoMySQL.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sqlEquipamentos)) {
                stmt.setInt(1, locacaoId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        double valorDiario = rs.getDouble("valor_diario");
                        double multaDiaria = obterMultaDiaria(locacaoId); // Método para obter a multa diária da locação
                        multaTotal += valorDiario * (multaDiaria / 100) * diasAtraso;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return multaTotal;
    }


}