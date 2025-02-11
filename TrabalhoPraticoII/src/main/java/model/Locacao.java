package model;

import controller.ConexaoMySQL;
import java.sql.Connection;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Locacao {
    public void inserirLocacao(LocalDate dataInicio, LocalDate dataTermino, double multa, int equipamentoId, int clienteId) {
        java.sql.Date dataComeco = java.sql.Date.valueOf(dataInicio);
        java.sql.Date dataFim = java.sql.Date.valueOf(dataTermino);
        String sql = "INSERT INTO locacoes (data_inicio, data_termino, multa, equipamento_id, cliente_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoMySQL.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, dataComeco);
            stmt.setDate(2, dataFim);
            stmt.setDouble(3, multa);
            stmt.setInt(4, equipamentoId);
            stmt.setInt(5, clienteId);
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
    public void buscarLocacaoPorCPF(String cpfOuCodigo, JTextArea textArea) {
    StringBuilder resultado = new StringBuilder(); // Para construir a string formatada
    String sql = "SELECT l.id, l.data_inicio, l.data_termino, l.multa, l.equipamento_id, l.cliente_id " +
                 "FROM locacao l " +
                 "JOIN cliente c ON l.cliente_id = c.id " +
                 "WHERE c.cpf = ?";
    
    try (Connection conn = ConexaoMySQL.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

           stmt.setString(1, cpfOuCodigo);
           try (ResultSet rs = stmt.executeQuery()) {
               boolean encontrouRegistros = false; // Flag para verificar se há registros

               while (rs.next()) {
                   encontrouRegistros = true; // Marca que pelo menos um registro foi encontrado
                   // Formata os dados e adiciona ao StringBuilder
                   resultado.append("ID: ").append(rs.getInt("id")).append("\n");
                   resultado.append("Data Início: ").append(rs.getDate("data_inicio")).append("\n");
                   resultado.append("Data Término: ").append(rs.getDate("data_termino")).append("\n");
                   resultado.append("Multa: ").append(rs.getDouble("multa")).append("\n");
                   resultado.append("Equipamento ID: ").append(rs.getInt("equipamento_id")).append("\n");
                   resultado.append("Cliente ID: ").append(rs.getInt("cliente_id")).append("\n");
                   resultado.append("----------------------------\n"); // Separador entre registros
               }
               
               // Verifica se nenhum registro foi encontrado
               if (!encontrouRegistros) {
                   resultado.append("Nenhuma locação encontrada para o CPF ou código informado.\n");
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();
           resultado.append("Erro ao buscar locações: ").append(e.getMessage());
       }
        
       // Define o texto no JTextArea
       textArea.setText(resultado.toString());
       }
        
    public int extrairLocacaoIdDoTexto(String texto) {
        // Procura pela linha que contém o ID da locação
        String[] linhas = texto.split("\n");
        for (String linha : linhas) {
            if (linha.startsWith("ID: ")) {
                try {
                    // Extrai o número após "ID: "
                    return Integer.parseInt(linha.substring(4).trim());
                } catch (NumberFormatException e) {
                    return -1; // Retorna -1 se não conseguir extrair o ID
                }
            }
        }
        return -1; // Retorna -1 se não encontrar a linha com o ID
    }
    
    public int obterIDEquipamentoPorLocacao (int id) {
        String sql = "SELECT equipamento_id FROM locacao WHERE id=?";
        try (Connection conn = ConexaoMySQL.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("equipamento_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;  // Retorna -1 caso o equipamento não seja encontrado
    }
    }
}