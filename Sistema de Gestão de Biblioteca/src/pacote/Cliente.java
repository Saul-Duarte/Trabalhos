package pacote;

import java.util.ArrayList;
import java.util.List;

public abstract class Cliente {
	
	enum TipoCLiente {
		
		ALUNO , PROFESSOR
		
	}

	private String nome;
    private String cpf;
    private double multaPendente;
    private List<Livro> livrosEmprestados;
    
    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.multaPendente = 0.0;
        this.livrosEmprestados = new ArrayList<>();
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



	public double getMultaPendente() {
		return multaPendente;
	}



	public void setMultaPendente(double multaPendente) {
		this.multaPendente = multaPendente;
	}



	public List<Livro> getLivrosEmprestados() {
		return livrosEmprestados;
	}



	public void setLivrosEmprestados(List<Livro> livrosEmprestados) {
		this.livrosEmprestados = livrosEmprestados;
	}

	public abstract int getLimiteLivros();
    public abstract int getPrazoDias();
    public abstract double getMultaDiaria();
    
    @Override
    public String toString() {
        return "Cliente: " + nome + ", CPF: " + cpf + ", Multa Pendente: R$" + multaPendente + ", Livros Emprestados: " + livrosEmprestados.size();
    }
    
}
