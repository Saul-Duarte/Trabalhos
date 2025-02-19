package model;

import controller.ConexaoMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
   
   public void atualizarEquipamento(int id, String nome, String descricao, double valor_diario, int quantidade) {
        String sql = "UPDATE equipamento SET nome=?, descricao=?, valor_diario=?, quantidade=? WHERE id=?";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setDouble(3, valor_diario);
            stmt.setInt(4, quantidade);
            stmt.setInt(5, id);
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
                model.addRow(new Object[] {
                    rs.getInt("id"), 
                    rs.getString("nome"), 
                    rs.getString("descricao"), 
                    rs.getDouble("valor_diario"), 
                    rs.getBoolean("status"), 
                    rs.getInt("quantidade")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<String> listarNomesEquipamentos() {
        List<String> nomesEquipamentos = new ArrayList<>();
        String sql = "SELECT nome FROM equipamento";
        try (Connection conn = ConexaoMySQL.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                nomesEquipamentos.add(rs.getString("nome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nomesEquipamentos;
    }
    
    public int obterIdEquipamento(String nome) {
        String sql = "SELECT id FROM equipamento WHERE nome=?";
        try (Connection conn = ConexaoMySQL.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;  // Retorna -1 caso o equipamento não seja encontrado
    }
    
    public void atualizarStatusEquipamento(int id, boolean status) {
        String sqlVerificaQuantidade = "SELECT quantidade FROM equipamento WHERE id = ?";
        String sqlAtualizaStatus = "UPDATE equipamento SET status = ? WHERE id = ?";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmtVerificaQuantidade = conn.prepareStatement(sqlVerificaQuantidade);
             PreparedStatement stmtAtualizaStatus = conn.prepareStatement(sqlAtualizaStatus)) {

            // Verifica a quantidade do equipamento
            stmtVerificaQuantidade.setInt(1, id);
            try (ResultSet rs = stmtVerificaQuantidade.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Nenhum equipamento encontrado com o ID informado.");
                    return; // Sai do método se o equipamento não for encontrado
                }

                int quantidade = rs.getInt("quantidade");

                // Se a quantidade for maior que 0 e o status for false, não atualiza
                if (quantidade > 0 && !status) {
                    System.out.println("Quantidade maior que 0. Status nao foi alterado para false.");
                    return;
                } else{
                    // Atualiza o status do equipamento
                   stmtAtualizaStatus.setBoolean(1, status);
                   stmtAtualizaStatus.setInt(2, id);
                   int linhasAfetadas = stmtAtualizaStatus.executeUpdate();

                   if (linhasAfetadas > 0) {
                       System.out.println("Status do equipamento atualizado com sucesso!");
                   }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean obterStatusEquipamento(int id) {
        String sql = "SELECT status FROM equipamento WHERE id=?";

        try (Connection conn = ConexaoMySQL.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("status");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;  // Retorna false por padrão caso o equipamento não seja encontrado
    }
    
    public void incrementarQuantidade(int id) {
        String sql = "UPDATE equipamento SET quantidade = quantidade + 1 WHERE id = ?";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrementarQuantidade(int id) {
        String sql = "UPDATE equipamento SET quantidade = quantidade - 1 WHERE id = ? AND quantidade > 0";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("Erro: Não é possível decrementar, quantidade já é 0.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int obterQuantidade(int id) {
        String sql = "SELECT quantidade FROM equipamento WHERE id=?";

        try (Connection conn = ConexaoMySQL.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantidade");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    
    public double obterValorDiario(int id) {
        String sql = "SELECT valor_diario FROM equipamento WHERE id=?";

        try (Connection conn = ConexaoMySQL.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("valor_diario");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;  // Retorna 0 caso o equipamento não seja encontrado
    }
}