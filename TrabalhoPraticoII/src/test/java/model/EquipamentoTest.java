package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class EquipamentoTest {

    private Equipamento equipamento;

    @BeforeEach
    public void setUp() {
        equipamento = new Equipamento();
    }

    @Test
    public void testInserirEquipamento() {
        equipamento.inserirEquipamento("Teste Equipamento", "Descrição Teste", 100.0, true, 10);
        int id = equipamento.obterIdEquipamento("Teste Equipamento");
        assertTrue(id > 0);
    }

    @Test
    public void testAtualizarEquipamento() {
        equipamento.inserirEquipamento("Equipamento Antigo", "Descrição Antiga", 50.0, true, 5);
        int id = equipamento.obterIdEquipamento("Equipamento Antigo");
        equipamento.atualizarEquipamento(id, "Equipamento Novo", "Descrição Nova", 75.0, 8);

        // Verifica se o ID ainda existe após a atualização
        assertTrue(equipamento.obterIdEquipamento("Equipamento Novo") > 0);
    }

    @Test
    public void testExcluirEquipamento() {
        equipamento.inserirEquipamento("Equipamento para Excluir", "Descrição", 30.0, true, 3);
        int id = equipamento.obterIdEquipamento("Equipamento para Excluir");
        equipamento.excluirEquipamento(id);
        assertEquals(-1, equipamento.obterIdEquipamento("Equipamento para Excluir"));
    }

    @Test
    public void testListarEquipamentos() {
        DefaultTableModel model = new DefaultTableModel();
        equipamento.listarEquipamentos(model);
        assertTrue(model.getRowCount() > 0);
    }

    @Test
    public void testObterIdEquipamento() {
        equipamento.inserirEquipamento("Equipamento Teste ID", "Descrição", 40.0, true, 4);
        int id = equipamento.obterIdEquipamento("Equipamento Teste ID");
        assertTrue(id > 0);
    }

    @Test
    public void testAtualizarStatusEquipamento() {
        equipamento.inserirEquipamento("Equipamento Status", "Descrição", 60.0, true, 6);
        int id = equipamento.obterIdEquipamento("Equipamento Status");
        equipamento.atualizarStatusEquipamento(id, false);
        assertFalse(equipamento.obterStatusEquipamento(id));
    }

    @Test
    public void testIncrementarQuantidade() {
        equipamento.inserirEquipamento("Equipamento Quantidade", "Descrição", 70.0, true, 7);
        int id = equipamento.obterIdEquipamento("Equipamento Quantidade");
        equipamento.incrementarQuantidade(id);
        assertEquals(8, equipamento.obterQuantidade(id));
    }

    @Test
    public void testDecrementarQuantidade() {
        equipamento.inserirEquipamento("Equipamento Quantidade Dec", "Descrição", 80.0, true, 8);
        int id = equipamento.obterIdEquipamento("Equipamento Quantidade Dec");
        equipamento.decrementarQuantidade(id);
        assertEquals(7, equipamento.obterQuantidade(id));
    }

    @Test
    public void testObterValorDiario() {
        equipamento.inserirEquipamento("Equipamento Valor Diario", "Descrição", 90.0, true, 9);
        int id = equipamento.obterIdEquipamento("Equipamento Valor Diario");
        assertEquals(90.0, equipamento.obterValorDiario(id));
    }
}