package pacote;

public class Professor extends Cliente {
	
	public Professor(String nome, String cpf, TipoCliente tipo) {
        super(nome, cpf, TipoCliente.PROFESSOR);
    }

    @Override
    public int getLimiteLivros() { 
    	return 10; 
    }

    @Override
    public int getPrazoDias() { 
    	return 90; 
    }

    @Override
    public double getMultaDiaria() { 
    	return 1.0; 
    }

}