package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.sql.Date;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class LocacaoTest {

    private Locacao locacao;

    @BeforeEach
    public void setUp() {
        locacao = new Locacao();
    }

    @Test
    public void testInserirLocacao() {
        LocalDate dataInicio = LocalDate.now();
        LocalDate dataTermino = dataInicio.plusDays(5);
        locacao.inserirLocacao(dataInicio, dataTermino, 0.1, 1, 1);
        assertTrue(true); // Verificação básica de execução sem erros
    }

    @Test
    public void testAtualizarLocacao() {
        LocalDate dataTermino = LocalDate.now().plusDays(10);
        locacao.atualizarLocacao(1, Date.valueOf(dataTermino));
        assertTrue(true); // Verificação básica de execução sem erros
    }

    @Test
    public void testExcluirLocacao() {
        locacao.excluirLocacao(1);
        assertTrue(true); // Verificação básica de execução sem erros
    }

    @Test
    public void testListarLocacoes() {
        DefaultTableModel model = new DefaultTableModel();
        locacao.listarLocacoes(model);
        assertTrue(model.getRowCount() > 0);
    }

    @Test
    public void testCalcularDiasAluguel() throws Locacao.LocacaoNaoEncontradaException {
        long dias = locacao.calcularDiasAluguel(1);
        assertTrue(dias >= 0);
    }

    @Test
    public void testCalcularValorTotal() throws Locacao.LocacaoNaoEncontradaException {
        double valorTotal = locacao.calcularValorTotal(1);
        assertTrue(valorTotal >= 0);
    }

    @Test
    public void testCalcularMulta() {
        Date dataDevolucao = Date.valueOf(LocalDate.now().plusDays(15));
        double multa = locacao.calcularMulta(1, dataDevolucao);
        assertTrue(multa >= 0);
    }

    @Test
    public void testBuscarLocacaoPorCPF() {
        JTextArea textArea = new JTextArea();
        locacao.buscarLocacaoPorCPF("12345678901", textArea);
        assertFalse(textArea.getText().isEmpty());
    }
}
