/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import model.Cliente;
import model.Locacao;

/**
 *
 * @author sauls
 */
public class Controlador {
    //Atributos da Classe Controlador
    private List<Locacao> locacoes;
    private List<Cliente> clientes;

    public List<Locacao> getLocacoes() {
        return locacoes;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}