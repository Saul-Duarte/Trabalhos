package pacote;

import java.util.ArrayList;
import java.util.List;

public abstract class Cliente {
	
	enum TipoCLiente {
		
		ALUNO , PROFESSOR
		
	}

	private String nome;
    private String cpf;
    private TipoCLiente tipo;
    private double multaPendente;
    private ArrayList<Livro> livrosEmprestados;
    
    public Cliente(String nome, String cpf, TipoCLiente tipo) {
        this.nome = nome;
        this.cpf = cpf;
        this.tipo = tipo;
        this.multaPendente = 0.0;
        this.livrosEmprestados = new ArrayList<>();
    }


	public TipoCLiente getTipo() {
		return tipo;
	}


	public void setTipo(TipoCLiente tipo) {
		this.tipo = tipo;
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

	public ArrayList<Livro> getLivrosEmprestados() {
		return livrosEmprestados;
	}

	public void setLivrosEmprestados(ArrayList<Livro> livrosEmprestados) {
		this.livrosEmprestados = livrosEmprestados;
	}

	public abstract int getLimiteLivros();
    public abstract int getPrazoDias();
    public abstract double getMultaDiaria();
    
    public boolean podeEmprestar() {
        return livrosEmprestados.size() < getLimiteLivros() && multaPendente == 0.0;
    }

    public void emprestarLivro(Livro livro) {
        livrosEmprestados.add(livro);
    }

    public void devolverLivro(Livro livro) {
        livrosEmprestados.remove(livro);
    }

    public void adicionarMulta(double valor) {
        multaPendente += valor;
    }

    public void quitarMulta() {
        multaPendente = 0.0;
    }

    
    
    @Override
    public String toString() {
        return "Cliente: " + nome + ", CPF: " + cpf + ", Livros Emprestados: " + livrosEmprestados.size();
    }
    
}
