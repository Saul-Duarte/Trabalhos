package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

public class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
    }

    @Test
    public void testInserirCliente() throws SQLException {
        cliente.inserirCliente("Cliente Teste", "12345678901", "1234567890");
        int id = cliente.obterIdCliente("12345678901");
        assertTrue(id > 0);
    }

    @Test
    public void testAtualizarCliente() throws SQLException {
        cliente.inserirCliente("Cliente Antigo", "12345678902", "1234567891");
        int id = cliente.obterIdCliente("12345678902");
        cliente.atualizarCliente(id, "Cliente Novo", "12345678902", "1234567892");

        // Verifica se o ID ainda existe após a atualização
        assertTrue(cliente.obterIdCliente("12345678902") > 0);
    }

    @Test
    public void testExcluirCliente() throws SQLException {
        cliente.inserirCliente("Cliente para Excluir", "12345678903", "1234567893");
        int id = cliente.obterIdCliente("12345678903");
        cliente.excluirCliente(id);
        assertEquals(0, cliente.obterIdCliente("12345678903"));
    }

    @Test
    public void testListarCliente() {
        DefaultTableModel model = new DefaultTableModel();
        cliente.listarCliente(model);
        assertTrue(model.getRowCount() > 0);
    }

    @Test
    public void testObterIdCliente() throws SQLException {
        cliente.inserirCliente("Cliente Teste ID", "12345678904", "1234567894");
        int id = cliente.obterIdCliente("12345678904");
        assertTrue(id > 0);
    }
}