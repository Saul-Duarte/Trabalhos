package pacote;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

class Biblioteca {
	
	//Atributos da classe Biblioteca
    private ArrayList<Livro> livros;
    private ArrayList<Cliente> clientes;

    //Construtor da classe Biblioteca
    public Biblioteca() {
        livros = new ArrayList<>();
        clientes = new ArrayList<>();
    }

    //Método para cadastrar um livro
    public void cadastrarLivro(Livro livro) {
        livros.add(livro);
    }

    //Método para listar os livros da biblioteca
    public void listarLivros() {
    	//Verifica se a lista de livros está vazia
    	if (livros.isEmpty()) {
    		System.out.println("Nenhum livro cadastrado.");
    	} else {
    		System.out.println("Livros cadastrados: ");
    		for (Livro livro : livros) {
                System.out.println(livro);
            }
    	}
    }

    //Método para cadastrar um cliente
    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    //Método para listar os clientes da biblioteca
    public void listarClientes() {
    	//Verifica se a lista de clientes está vazia
    	if (clientes.isEmpty()) {
    		System.out.println("Nenhum cliente cadastrado.");
    	} else {
    		System.out.println("Clientes cadastrados: ");
    		for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
    	}
    }

    //Método para encontrar um cliente pelo CPF
    public Cliente buscarCliente(String cpf) {
        return clientes.stream().filter(c -> c.getCpf().equals(cpf)).findFirst().orElse(null);
    }

    //Método para encontrar um livro pelo Título
    public Livro buscarLivro(String titulo) {
        return livros.stream().filter(l -> l.getTitulo().equalsIgnoreCase(titulo)).findFirst().orElse(null);
    }

    //Método para realizar empréstimos dos livros
    public void emprestarLivro(String cpf, String titulo) {
    	//Busca se existem o cliente e o livro no sistema
        Cliente cliente = buscarCliente(cpf);
        Livro livro = buscarLivro(titulo);

        //Não encontrou o cliente ou o livro
        if (cliente == null || livro == null) {
            System.out.println("Cliente ou livro não encontrado.");
            return;
        //O cliente possui pendências ou já alcançou o limite de livros emprestados
        } else if (!cliente.podeEmprestar()) {
            System.out.println("Cliente não pode pegar mais livros emprestados.");
            return;
        //Livro indisponível para empréstimo
        } else if (livro.getExemplaresDisponiveis() == 0) {
            System.out.println("Livro indisponível.");
            return;
        //Empréstimo bem-sucedido
        } else {
        	cliente.emprestarLivro(livro);
        	livro.emprestar();
        	System.out.println("Livro emprestado com sucesso!");
        }
    }

    //Método para devolução dos livros emprestados
    public void devolverLivro(String cpf, String titulo) {
    	//Busca se existem o cliente e o livro no sistema
        Cliente cliente = buscarCliente(cpf);
        Livro livro = buscarLivro(titulo);

        //Não encontrou o cliente ou o livro
        if (cliente == null || livro == null) {
            System.out.println("Cliente ou livro não encontrado.");
            return;
        //O livro não está com este cliente
        } else if (!cliente.getLivrosEmprestados().contains(livro)) {
            System.out.println("Cliente não possui este livro.");
            return;
        //Devolução bem-sucedida
        } else {
        	cliente.devolverLivro(livro);
        	livro.devolver();
        	System.out.println("Livro devolvido com sucesso!");
        }
    }
    
    //Método para consultar a multa do cliente
    public void consultarMulta(String cpf) {
    	//Busca se o cliente está no sistema
        Cliente cliente = buscarCliente(cpf);
        //Imprime a multa na tela
        if (cliente != null) {
            System.out.printf("Saldo de Multas: R$ %.2f%n", cliente.getMultaPendente());
        //O cliente não está no sistema
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    
    //Método para pagar a multa do cliente
    public void pagarMulta(String cpf) {
    	//Busca se o cliente está no sistema
    	Cliente cliente = buscarCliente(cpf);
    	//Zera a multa e retorna uma mensagem
        if (cliente != null) {
            cliente.quitarMulta();
            System.out.println("Multa quitada com sucesso!");
        //O cliente não está no sistema
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    
    //Método para calcular a multa do cliente
    public void calcularMulta(String cpf, LocalDate dataPegouEmprestado, LocalDate dataAtual) {
    	//Busca se o cliente está no sistema
    	Cliente cliente = buscarCliente(cpf);
    	if (cliente != null) {
    		//Calcula a diferença de dias entre o dia que o cliente pegou o livro emprestado e o dia da devolução(dia atual)
    		long diasAtraso = ChronoUnit.DAYS.between(dataPegouEmprestado, dataAtual);
    		//Utiliza os valores definidos de multa diária e prazo de dias de devolução para o tipo deste cliente
    		double valorDiario = cliente.getMultaDiaria();
        	double limiteDias = cliente.getPrazoDias();
    		if (diasAtraso > limiteDias) {
    			//Calcula a multa com os valores definidos e adiciona ao valor da multa do cliente
    			double valorMulta = valorDiario * (diasAtraso - limiteDias);
            	cliente.adicionarMulta(valorMulta);
            	System.out.printf("Multa aplicada: R$ %.2f%n", valorMulta);
            }
    	//O cliente não está no sistema
        } else {
        	System.out.println("Cliente não encontrado");
        }
    }
    
    //Método para listar um livro pelo autor ou pelo título
    public void pesquisarLivro(String criterio, String termo) {
    	//Variável de controle
        boolean encontrado = false;

        //Varredura da lista de livros em busca do livro desejado
        for (Livro livro : livros) {
        	//Busca por título
            if (criterio.equals("titulo") && livro.getTitulo().contains(termo)) {
                System.out.println(livro);
                encontrado = true;
            //Busca por autor
            } else if (criterio.equals("autor") && livro.getAutor().contains(termo)) {
                System.out.println(livro);
                encontrado = true;
            }
        }
        //Livro não encontrado
        if (!encontrado) {
            System.out.println("Nenhum livro encontrado com o critério fornecido.");
        }
    }

}