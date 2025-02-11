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
        String sql = "INSERT INTO locacao (data_inicio, data_termino, multa, equipamento_id, cliente_id) VALUES (?, ?, ?, ?, ?)";
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
        String sql = "UPDATE locacao SET data_termino=? WHERE id=?";
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
        String sql = "DELETE FROM locacao WHERE id=?";
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
        String sql = "SELECT * FROM locacao";
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
    
    public class LocacaoNaoEncontradaException extends Exception {
    public LocacaoNaoEncontradaException(String message) {
        super(message);
    }
}
    public long calcularDiasAluguel(int id) throws LocacaoNaoEncontradaException {
    String sql = "SELECT DATEDIFF(data_termino, data_inicio) AS diasAluguel FROM locacao WHERE id = ?";
    try (Connection conn = ConexaoMySQL.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("diasAluguel");
            } else {
                throw new LocacaoNaoEncontradaException("Locação com ID " + id + " não encontrada.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new LocacaoNaoEncontradaException("Erro ao calcular dias de aluguel: " + e.getMessage());
    }
}
    
    public double calcularValorTotal(int locacaoId) throws LocacaoNaoEncontradaException {
        double total = 0;
        String sqlEquipamentos = "SELECT e.valor_diario FROM equipamento e " +
                                 "JOIN locacao l ON e.id = l.equipamento_id WHERE l.id = ?";

        try (Connection conn = ConexaoMySQL.conectar();
           PreparedStatement stmt = conn.prepareStatement(sqlEquipamentos)) {

           stmt.setInt(1, locacaoId);
           try (ResultSet rs = stmt.executeQuery()) {
               // Verifica se há resultados
               if (!rs.isBeforeFirst()) {
                   System.out.println("Nenhum equipamento encontrado para a locação ID: " + locacaoId);
                   return 0;
               }

               // Soma os valores diários dos equipamentos
               while (rs.next()) {
                   double valorDiario = rs.getDouble("valor_diario");
                   total += valorDiario;
               }
        }

            // Calculando os dias de aluguel
            long diasAluguel = calcularDiasAluguel(locacaoId);
            if (diasAluguel <= 0) {
                System.out.println("Dias de aluguel inválidos para a locação ID: " + locacaoId);
                return 0;
            }

            total *= diasAluguel;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public double obterMultaDiaria(int locacaoId) {
        String sql = "SELECT multa FROM locacao WHERE id = ?";
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
        String sql = "SELECT data_termino FROM locacao WHERE id = ?";
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
            String sqlEquipamentos = "SELECT e.valor_diario FROM equipamento e " +
                                     "JOIN locacao l ON e.id = l.equipamento_id WHERE l.id = ?";
            try (Connection conn = ConexaoMySQL.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sqlEquipamentos)) {
                stmt.setInt(1, locacaoId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        double valorDiario = rs.getDouble("valor_diario");
                        double multaDiaria = obterMultaDiaria(locacaoId); // Método para obter a multa diária da locação
                        multaTotal += valorDiario * (multaDiaria) * diasAtraso;
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
                 "WHERE c.cpf = ? AND l.status_pendente = TRUE"; // Filtra apenas locações pendentes

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
                double multa = rs.getDouble("multa");
                resultado.append("Taxa da Multa: ").append(String.format("%.0f%%", multa * 100)).append("\n");
                resultado.append("Equipamento ID: ").append(rs.getInt("equipamento_id")).append("\n");
                resultado.append("Cliente ID: ").append(rs.getInt("cliente_id")).append("\n");
                resultado.append("----------------------------\n"); // Separador entre registros
            }

            // Verifica se nenhum registro foi encontrado
            if (!encontrouRegistros) {
                resultado.append("Nenhuma locação pendente encontrada para o CPF ou código informado.\n");
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

    public void excluirLocacaoPorCliente(int clienteId) {
        String sql = "DELETE FROM locacao WHERE cliente_id =?";
        try (Connection conn = ConexaoMySQL.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void alterarLocacaoPorCliente(int clienteId) {
        String sql = "UPDATE locacao SET cliente_id =? WHERE id=?";
        try (Connection conn = ConexaoMySQL.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean equipamentoVinculado(int equipamentoId) {
        String sql = "SELECT COUNT(*) FROM locacao WHERE equipamento_id = ?";
        try (Connection conn = ConexaoMySQL.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, equipamentoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void excluirLocacoesPorEquipamento(int equipamentoId) {
        String sql = "DELETE FROM locacao WHERE equipamento_id = ?";
        try (Connection conn = ConexaoMySQL.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, equipamentoId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void alterarLocacoesPorEquipamento(int equipamentoId) {
        String sql = "UPDATE locacao SET equipamento_id =? WHERE id=?";
        try (Connection conn = ConexaoMySQL.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, equipamentoId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void marcarLocacaoComoNaoPendente(int id) {
    String sql = "UPDATE locacao SET status_pendente = 0 WHERE id = ?";
    try (Connection conn = ConexaoMySQL.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        int linhasAfetadas = stmt.executeUpdate();
        
        if (linhasAfetadas == 0) {
            System.out.println("Nenhuma locação encontrada com o ID: " + id);
        } else {
            System.out.println("Locacao ID " + id + " marcada como nao pendente.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public void atualizarMultaPaga(int locacaoId, double multa) throws SQLException {
    String sql = "UPDATE locacao SET multa_paga = ? WHERE id = ?";
    try (Connection conn = ConexaoMySQL.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setDouble(1, multa);
        stmt.setInt(2, locacaoId);
        stmt.executeUpdate();
    }
}

}