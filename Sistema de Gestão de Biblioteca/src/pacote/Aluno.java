package pacote;

public class Aluno extends Cliente {
	
	public Aluno(String nome, String cpf, TipoCliente tipo) {
        super(nome, cpf, TipoCliente.ALUNO);
    }

    @Override
    public int getLimiteLivros() { 
    	return 3; 
    }

    @Override
    public int getPrazoDias() { 
    	return 5; 
    }

    @Override
    public double getMultaDiaria() { 
    	return 2.0;
    }

}