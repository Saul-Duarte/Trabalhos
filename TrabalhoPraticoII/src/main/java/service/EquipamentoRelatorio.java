/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author temnq
 */
public class EquipamentoRelatorio {
    private String nome;
    private int codigo;
    private int quantidadeAlugado;
    private double receitaTotal;

    public EquipamentoRelatorio(String nome, int codigo, int quantidadeAlugado, double receitaTotal) {
        this.nome = nome;
        this.codigo = codigo;
        this.quantidadeAlugado = quantidadeAlugado;
        this.receitaTotal = receitaTotal;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getQuantidadeAlugado() {
        return quantidadeAlugado;
    }

    public double getReceitaTotal() {
        return receitaTotal;
    }
}