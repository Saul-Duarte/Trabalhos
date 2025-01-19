/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 *
 * @author sauls
 */
public class Locacao {
    //Atributos da Classe Locacao
    private static int codigoGenerator = 1;
    private int codigo;
    private Cliente cliente;
    private List<Equipamento> equipamentos;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private double multaDiaria;
    private double valorTotal;
    
    //Construtor da Classe Locação
    public Locacao(Cliente cliente, List<Equipamento> equipamentos, LocalDate dataInicio, LocalDate dataTermino, double multaDiaria){
        this.codigo = codigoGenerator++;
        this.cliente = cliente;
        this.equipamentos = equipamentos;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.multaDiaria = multaDiaria;
        equipamentos.forEach(e -> e.ajustarQuantidade(-1));
        this.valorTotal = calcularValorTotal();
    }
    
    public int getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }
    
    //Método que calcula os dias de aluguel
    public double calcularDiasAluguel(){
        return ChronoUnit.DAYS.between(dataInicio, dataTermino);
    }
    
    //Método que calcula o valor do aluguel
    public double calcularValorTotal(){
        return equipamentos.stream().mapToDouble(Equipamento::getValorDiario).sum() * calcularDiasAluguel();
    }
    
    //Método que calcula o valor da multa se houver atraso
    public double calcularMulta(LocalDate dataDevolucao){
    long diasAtraso = ChronoUnit.DAYS.between(dataTermino, dataDevolucao);
        if (diasAtraso > 0){
            return equipamentos.stream()
                    .mapToDouble(e -> e.getValorDiario() * (multaDiaria / 100) * diasAtraso)
                    .sum();
        }
        return 0;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}