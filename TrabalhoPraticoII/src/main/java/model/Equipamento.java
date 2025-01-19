/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sauls
 */
public class Equipamento {
    //Atributos da Classe Equipamento
    private static int codigoGenerator = 1;
    private int codigo;
    private String nome;
    private String descricao;
    private double valorDiario;
    private boolean status;
    private int quantidade;
    
    //Construtor da Classe Equipamento
    public Equipamento(String nome, String descricao, double valorDiario, int quantidade) {
        this.codigo = codigoGenerator++; //Gerador de código de cada equipamento
        this.nome = nome;
        this.descricao = descricao;
        this.valorDiario = valorDiario;
        this.quantidade = quantidade;
        this.status = quantidade > 0; //Se quantidade < 0, o equipamento não poderá ser alugado
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorDiario() {
        return valorDiario;
    }

    public void setValorDiario(double valorDiario) {
        this.valorDiario = valorDiario;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getQuantidade() {
        return quantidade;
    }
    
    //Método para alterar a quantidade de um equipamento
    public void ajustarQuantidade(int ajuste) {
        this.quantidade += ajuste;
        this.status = quantidade > 0;
    }
    
}