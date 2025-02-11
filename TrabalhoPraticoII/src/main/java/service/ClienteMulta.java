/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author temnq
 */
public class ClienteMulta {
    private String nome;
    private String cpf;
    private double totalMultas;

    public ClienteMulta(String nome, String cpf, double totalMultas) {
        this.nome = nome;
        this.cpf = cpf;
        this.totalMultas = totalMultas;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public double getTotalMultas() {
        return totalMultas;
    }

    public String[] split(String __Multas_R$) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}