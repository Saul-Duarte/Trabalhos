/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Equipamento;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author sauls
 */
public class GestorTest {
    @Test
    public void testCadastrarEquipamento() {
        Gestor gestor = new Gestor();

        gestor.cadastrarEquipamento("Betoneira", "Betoneira de 400L", 150.0, 5);

        List<Equipamento> equipamentos = gestor.getEquipamentosDisponiveis();

        assertEquals(1, equipamentos.size());
        assertEquals("Betoneira", equipamentos.get(0).getNome());
        assertEquals(150.0, equipamentos.get(0).getValorDiario());
        assertTrue(equipamentos.get(0).isStatus());
    }

    @Test
    public void testCadastrarEquipamentoComQuantidadeZero() {
        Gestor gestor = new Gestor();

        gestor.cadastrarEquipamento("Compactador", "Compactador de solo", 100.0, 0);

        List<Equipamento> equipamentos = gestor.getEquipamentosDisponiveis();

        assertTrue(equipamentos.isEmpty());
    }
    
    @Test
    public void testRegistrarLocacao() {
        Gestor gestor = new Gestor();

        gestor.cadastrarEquipamento("Betoneira", "Betoneira de 400L", 150.0, 5);

        gestor.registrarLocacao("João Silva", "12345678900", "123456789", 
                                 List.of(gestor.getEquipamentosDisponiveis().get(0)),
                                 java.time.LocalDate.of(2025, 1, 10),
                                 java.time.LocalDate.of(2025, 1, 15), 10.0);

        assertEquals(1, gestor.buscarLocacaoPorCpf("12345678900").size());
    }
}
