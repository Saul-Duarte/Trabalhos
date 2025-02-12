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
        String sql = "SELECT c.nome AS cliente, e.nome AS equipamento, l.data_inicio, l.data_termino, l.valor_total " +
                     "FROM locacao l " +
                     "JOIN cliente c ON l.cliente_id = c.id " +
                     "JOIN equipamento e ON l.equipamento_id = e.id " +
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
    public List<EquipamentoRelatorio> obterEquipamentosMaisAlugados() {
        List<EquipamentoRelatorio> equipamentos = new ArrayList<>();

        String sql = "SELECT e.nome AS nome_equipamento, e.id AS codigo_equipamento, " +
                     "COUNT(l.id) AS quantidade_alugado, " +
                     "SUM(e.valor_diario * DATEDIFF(l.data_termino, l.data_inicio)) AS receita_total " +
                     "FROM equipamento e " +
                     "JOIN locacao l ON e.id = l.equipamento_id " +
                     "GROUP BY e.id " +
                     "ORDER BY quantidade_alugado DESC";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nome_equipamento");
                int codigo = rs.getInt("codigo_equipamento");
                int quantidadeAlugado = rs.getInt("quantidade_alugado");
                double receitaTotal = rs.getDouble("receita_total");

                equipamentos.add(new EquipamentoRelatorio(nome, codigo, quantidadeAlugado, receitaTotal));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

            return equipamentos;
        }
            /**
         * Obtém a lista de clientes com multas acumuladas, ordenados pelo maior valor.
         * @return Lista formatada com o nome do cliente e o valor das multas.
         */
        public List<ClienteMulta> obterClientesComMultasAcumuladas() {
        List<ClienteMulta> lista = new ArrayList<>();

        String sql = "SELECT c.nome AS cliente, c.CPF AS cpf, SUM(l.multa_paga) AS total_multa " +
                     "FROM locacao l " +
                     "JOIN cliente c ON l.cliente_id = c.id " +
                     "WHERE l.multa_paga > 0 " +
                     "GROUP BY c.nome, c.CPF " +
                     "ORDER BY total_multa DESC";

        try (Connection con = ConexaoMySQL.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("cliente");
                String cpf = rs.getString("cpf");
                double totalMultas = rs.getDouble("total_multa");

                lista.add(new ClienteMulta(nome, cpf, totalMultas));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter clientes com multas acumuladas: " + e.getMessage());
        }

        return lista;
    }

}
