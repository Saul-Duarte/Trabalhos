/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sauls
 */
public class Cliente {
    //Atributos da Classe Cliente
    private String nome;
    private String cpf;
    private String telefone;
    private List<Locacao> locacoes;
    
    //Construtor da Classe Cliente
    public Cliente(String nome, String cpf, String telefone){
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.locacoes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public List<Locacao> getLocacoes() {
        return locacoes;
    }
    
    //Método para adicionar locações para um determinado cliente
    public void adicionarLocacao(Locacao locacao) {
        this.getLocacoes().add(locacao);
    }
}