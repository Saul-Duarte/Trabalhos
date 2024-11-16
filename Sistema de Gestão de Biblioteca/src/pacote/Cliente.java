package pacote;

import java.util.ArrayList;

public abstract class Cliente {
	
	//Enum referente ao tipo de cliente
	enum TipoCliente {
		ALUNO, PROFESSOR
	}

	//Atributos da classe Cliente
	private String nome;
    private String cpf;
    private TipoCliente tipo;
    private double multaPendente;
    private ArrayList<Livro> livrosEmprestados;
    
    //Construtor da classe Cliente
    public Cliente(String nome, String cpf, TipoCliente tipo) {
        this.nome = nome;
        this.cpf = cpf;
        this.tipo = tipo;
        this.multaPendente = 0.0;
        this.livrosEmprestados = new ArrayList<>();
    }

    //Getters e Setters da classe Cliente
	public TipoCliente getTipo() {
		return tipo;
	}

	public void setTipo(TipoCliente tipo) {
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

	//Métodos abstratos da classe Cliente que serão definidos nas subclasses
	public abstract int getLimiteLivros();
    public abstract int getPrazoDias();
    public abstract double getMultaDiaria();
    
    //Método de verificação para poder emprestar um livro
    public boolean podeEmprestar() {
        return livrosEmprestados.size() < getLimiteLivros() && multaPendente == 0.0;
    }

    //Método para adicionar o livro emprestado para a lista pessoal do cliente
    public void emprestarLivro(Livro livro) {
        livrosEmprestados.add(livro);
    }

    //Método para remover o livro devolvido da lista pessoal do cliente
    public void devolverLivro(Livro livro) {
        livrosEmprestados.remove(livro);
    }

    //Método para adicionar um valor à multa do cliente
    public void adicionarMulta(double valor) {
        multaPendente += valor;
    }

    //Método para pagar uma multa pendente
    public void quitarMulta() {
        multaPendente = 0.0;
    }
    
  //Método para listar o cliente com seus atributos
    @Override
    public String toString() {
        return "Cliente: " + nome + ", CPF: " + cpf + ", Livros Emprestados: " + livrosEmprestados.size() + ", Perfil: " + tipo;
    }
    
}