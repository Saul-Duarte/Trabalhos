/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Equipamento;
import model.Cliente;
import model.Locacao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sauls
 */
public class Gestor {
    //Atributos da Classe Gestor
    private List<Equipamento> equipamentos;
    private List<Cliente> clientes;
    private List<Locacao> locacoes;

    //Construtor da Classe Gestor
    public Gestor() {
        this.equipamentos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.locacoes = new ArrayList<>();
    }
    
    public List<Locacao> getLocacoes() {
        return locacoes;
    }
    
    public List<Cliente> getClientes() {
        return clientes;
    }

    //Método que cadastra o equipamento no sistema
    public void cadastrarEquipamento(String nome, String descricao, double valorDiario, int quantidade) {
        equipamentos.add(new Equipamento(nome, descricao, valorDiario, quantidade));
    }

    //Método que retorna os equipamentos disponíveis para aluguel
    public List<Equipamento> getEquipamentosDisponiveis() {
        return equipamentos.stream().filter(Equipamento::isStatus).toList();
    }

    //Método que registra a locação de um equipamento, adicionando o cliente também
    public void registrarLocacao(String nomeCliente, String cpf, String telefone, List<Equipamento> equipamentosSelecionados,
                                 LocalDate dataInicio, LocalDate dataFim, double multaPercentual) {
        Cliente cliente = clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElseGet(() -> {
                    Cliente novoCliente = new Cliente(nomeCliente, cpf, telefone);
                    clientes.add(novoCliente);
                    return novoCliente;
                });

        Locacao locacao = new Locacao(cliente, equipamentosSelecionados, dataInicio, dataFim, multaPercentual);
        cliente.adicionarLocacao(locacao);
        getLocacoes().add(locacao);
    }

    //Método para buscar por CPF
    public List<Locacao> buscarLocacaoPorCpf(String cpf) {
        return getLocacoes().stream().filter(l -> l.getCliente().getCpf().equals(cpf)).toList();
    }

    //Método que registra a devolução
    public void registrarDevolucao(Locacao locacao, LocalDate dataDevolucao) {
        locacao.getEquipamentos().forEach(e -> e.ajustarQuantidade(1));
        double multa = locacao.calcularMulta(dataDevolucao);
        System.out.printf("Devolução registrada. Multa: R$ %.2f%n", multa);
    }
}