package service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class RelatorioTest {

    private Relatorio relatorio;

    @BeforeEach
    public void setUp() {
        relatorio = new Relatorio();
    }

    @Test
    public void testObterHistoricoLocacoes() {
        List<String> historico = relatorio.obterHistoricoLocacoes();
        assertNotNull(historico);
    }

    @Test
    public void testObterEquipamentosMaisAlugados() {
        List<EquipamentoRelatorio> equipamentos = relatorio.obterEquipamentosMaisAlugados();
        assertNotNull(equipamentos);
    }

    @Test
    public void testObterClientesComMultasAcumuladas() {
        List<ClienteMulta> clientes = relatorio.obterClientesComMultasAcumuladas();
        assertNotNull(clientes);
    }
}