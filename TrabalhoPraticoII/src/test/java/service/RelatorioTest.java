/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import controller.Gestor;
import model.Cliente;
import model.Equipamento;
import model.Locacao;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author sauls
 */
public class RelatorioTest {
    @Test
    public void testGerarRelatorioMaisAlugados() {
        Gestor gestor = new Gestor();
        gestor.cadastrarEquipamento("Betoneira", "Betoneira de 400L", 150.0, 5);
        gestor.cadastrarEquipamento("Compactador", "Compactador de solo", 100.0, 5);

        gestor.registrarLocacao("João Silva", "12345678900", "123456789", 
                                 List.of(gestor.getEquipamentosDisponiveis().get(0)),
                                 LocalDate.of(2025, 1, 10), 
                                 LocalDate.of(2025, 1, 12), 10.0);

        Map<String, Long> relatorio = Relatorio.gerarRelatorioMaisAlugados(gestor.getLocacoes());

        assertEquals(1, relatorio.get("Betoneira"));
        assertNull(relatorio.get("Compactador"));
    }

    @Test
    public void testGerarRelatorioClientesComMultas() {
        Gestor gestor = new Gestor();
        gestor.cadastrarEquipamento("Betoneira", "Betoneira de 400L", 150.0, 5);

        gestor.registrarLocacao("João Silva", "12345678900", "123456789", 
                                 List.of(gestor.getEquipamentosDisponiveis().get(0)),
                                 LocalDate.of(2025, 1, 10), 
                                 LocalDate.of(2025, 1, 15), 10.0);

        List<Cliente> clientesComMultas = Relatorio.gerarRelatorioClientesComMultas(gestor.getClientes());

        assertTrue(clientesComMultas.isEmpty());
    }
}
