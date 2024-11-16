package pacote;

//Subclasse de Cliente
public class Aluno extends Cliente {
	
	//Construtor da classe Aluno
	public Aluno(String nome, String cpf, TipoCliente tipo) {
        super(nome, cpf, TipoCliente.ALUNO);
    }

	//Limite de livros para a classe Aluno
    @Override
    public int getLimiteLivros() { 
    	return 3; 
    }

    //Prazo de dias de devolução para a classe Aluno
    @Override
    public int getPrazoDias() { 
    	return 5; 
    }

    //Valor da multa diária para a classe Aluno
    @Override
    public double getMultaDiaria() { 
    	return 2.0;
    }

}