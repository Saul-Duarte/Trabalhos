/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.time.LocalDate;
import model.Cliente;
import model.Locacao;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author sauls
 */
public class Relatorio {
    //Relatório dos equipamentos mais alugadas, filtra o número de alugueis feitos para cada equipamento se o mesmo foi alugado
    public static Map<String, Long> gerarRelatorioMaisAlugados(List<Locacao> locacoes) {
        return locacoes.stream()
                .flatMap(locacao -> locacao.getEquipamentos().stream()) // Equipamentos alugados
                .collect(Collectors.groupingBy(equipamento -> equipamento.getNome(), Collectors.counting())); // Agrupa por nome
    }

    //Relatório dos clientes com multas, filtra os clientes que possuem multas e os adiciona ao relatório
    public static List<Cliente> gerarRelatorioClientesComMultas(List<Cliente> clientes) {
        return clientes.stream()
                .filter(cliente -> cliente.getLocacoes().stream() // Locações do cliente
                        .anyMatch(locacao -> locacao.calcularMulta(LocalDate.now()) > 0)) // Verifica se há multas
                .collect(Collectors.toList());
    }
}