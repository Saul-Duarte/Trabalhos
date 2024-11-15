package pacote;

public class Professor extends Cliente {
	
	public Professor(String nome, String cpf, TipoCLiente tipo) {
        super(nome, cpf, TipoCLiente.PROFESSOR);
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

