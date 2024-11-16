package pacote;

//Subclasse de Cliente
public class Professor extends Cliente {
	
	//Construtor da classe Professor
	public Professor(String nome, String cpf, TipoCliente tipo) {
        super(nome, cpf, TipoCliente.PROFESSOR);
    }

	//Limite de livros para a classe Professor
    @Override
    public int getLimiteLivros() { 
    	return 10; 
    }

    //Prazo de dias de devolução para a classe Professor
    @Override
    public int getPrazoDias() { 
    	return 90; 
    }

    //Valor da multa diária para a classe Professor
    @Override
    public double getMultaDiaria() { 
    	return 1.0; 
    }

}