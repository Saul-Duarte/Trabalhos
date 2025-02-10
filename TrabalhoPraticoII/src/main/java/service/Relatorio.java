package service;

import controller.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Relatorio {
    /**
     * Obtém o histórico de locações detalhado por cliente.Retorna uma lista formatada com os dados de aluguel.
     * @return
     */
    public List<String> obterHistoricoLocacoes() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT c.nome AS cliente, e.nome AS equipamento, l.data_inicio, l.data_devolucao, l.valor_total " +
                     "FROM locacoes l " +
                     "JOIN clientes c ON l.cliente_id = c.id " +
                     "JOIN equipamentos e ON l.equipamento_id = e.id " +
                     "ORDER BY c.nome, l.data_inicio";

        try (Connection con = ConexaoMySQL.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("cliente") + " alugou " +
                          rs.getString("equipamento") + " de " +
                          rs.getDate("data_inicio") + " até " +
                          rs.getDate("data_devolucao") + " por R$ " +
                          rs.getDouble("valor_total"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter histórico de locações: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtém os equipamentos mais alugados, listando os 10 mais frequentes.Retorna uma lista formatada com o nome do equipamento e a quantidade de vezes que foi alugado.
     * @return
     */
    public List<String> obterEquipamentosMaisAlugados() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT e.nome AS equipamento, COUNT(l.equipamento_id) AS quantidade_alugado " +
                     "FROM locacoes l " +
                     "JOIN equipamentos e ON l.equipamento_id = e.id " +
                     "GROUP BY e.nome " +
                     "ORDER BY quantidade_alugado DESC " +
                     "LIMIT 10";

        try (Connection con = ConexaoMySQL.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("equipamento") + " - Alugado " + rs.getInt("quantidade_alugado") + " vezes");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter equipamentos mais alugados: " + e.getMessage());
        }
        return lista;
    }
        /**
     * Obtém a lista de clientes com multas acumuladas, ordenados pelo maior valor.
     * @return Lista formatada com o nome do cliente e o valor das multas.
     */
    public List<String> obterClientesComMultasAcumuladas() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT c.nome AS cliente, SUM(l.multa) AS total_multas " +
                     "FROM locacoes l " +
                     "JOIN clientes c ON l.cliente_id = c.id " +
                     "WHERE l.multa > 0 " +
                     "GROUP BY c.nome " +
                     "ORDER BY total_multas DESC";

        try (Connection con = ConexaoMySQL.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("cliente") + " - Multas: R$" + rs.getDouble("total_multas"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter clientes com multas acumuladas: " + e.getMessage());
        }
        return lista;
    }

}