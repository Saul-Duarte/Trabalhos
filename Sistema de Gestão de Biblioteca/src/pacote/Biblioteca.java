package pacote;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

class Biblioteca {
    private ArrayList<Livro> livros;
    private ArrayList<Cliente> clientes;

    public Biblioteca() {
        livros = new ArrayList<>();
        clientes = new ArrayList<>();
    }

    public void cadastrarLivro(Livro livro) {
        livros.add(livro);
    }

    public void listarLivros() {
    	if (livros.isEmpty()) {
    		System.out.println("Nenhum livro cadastrado.");
    	} else {
    		System.out.println("Livros cadastrados: ");
    		for (Livro livro : livros) {
                System.out.println(livro);
            }
    	}
    }

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void listarClientes() {
    	if (clientes.isEmpty()) {
    		System.out.println("Nenhum cliente cadastrado.");
    	} else {
    		System.out.println("Clientes cadastrados: ");
    		for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
    	}
    }

    public Cliente buscarCliente(String cpf) {
        return clientes.stream().filter(c -> c.getCpf().equals(cpf)).findFirst().orElse(null);
    }

    public Livro buscarLivro(String titulo) {
        return livros.stream().filter(l -> l.getTitulo().equalsIgnoreCase(titulo)).findFirst().orElse(null);
    }

    public void emprestarLivro(String cpf, String titulo) {
        Cliente cliente = buscarCliente(cpf);
        Livro livro = buscarLivro(titulo);

        if (cliente == null || livro == null) {
            System.out.println("Cliente ou livro não encontrado.");
            return;
        }else if (!cliente.podeEmprestar()) {
            System.out.println("Cliente não pode pegar mais livros emprestados.");
            return;
        }else if (livro.getExemplaresDisponiveis() == 0) {
            System.out.println("Livro indisponível.");
            return;
        }else {
        	cliente.emprestarLivro(livro);
        	livro.emprestar();
        	System.out.println("Livro emprestado com sucesso!");
        }
    }

    public void devolverLivro(String cpf, String titulo) {
        Cliente cliente = buscarCliente(cpf);
        Livro livro = buscarLivro(titulo);

        if (cliente == null || livro == null) {
            System.out.println("Cliente ou livro não encontrado.");
            return;
        }else if (!cliente.getLivrosEmprestados().contains(livro)) {
            System.out.println("Cliente não possui este livro.");
            return;
        }else {
        	cliente.devolverLivro(livro);
        	livro.devolver();
        	System.out.println("Livro devolvido com sucesso!");
        }
    }
    
    public void consultarMulta(String cpf) {
         Cliente cliente = buscarCliente(cpf);
         if (cliente != null) {
             System.out.printf("Saldo de Multas: R$ %.2f%n", cliente.getMultaPendente());
         } else {
             System.out.println("Cliente não encontrado.");
         }
    }
    
    public void pagarMulta(String cpf) {
    	Cliente cliente = buscarCliente(cpf);
        if (cliente != null) {
            cliente.quitarMulta();
            System.out.println("Multa quitada com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    
    public void calcularMulta(String cpf, LocalDate dataPegouEmprestado, LocalDate dataAtual) {
    	Cliente cliente = buscarCliente(cpf);
    	if (cliente != null) {
    		long diasAtraso = ChronoUnit.DAYS.between(dataPegouEmprestado, dataAtual);
    		double valorDiario = cliente.getMultaDiaria();
        	double limiteDias = cliente.getPrazoDias();
    		if (diasAtraso > limiteDias) {
    			double valorMulta = valorDiario * (diasAtraso - limiteDias);
            	cliente.adicionarMulta(valorMulta);
            	System.out.printf("Multa aplicada: R$ %.2f%n", valorMulta);
            }
        } else {
        	System.out.println("Cliente não encontrado");
        }
    }
    
    public void pesquisarLivro(String criterio, String termo) {
        boolean encontrado = false;

        for (Livro livro : livros) {
            if (criterio.equals("titulo") && livro.getTitulo().contains(termo)) {
                System.out.println(livro);
                encontrado = true;
            } else if (criterio.equals("autor") && livro.getAutor().contains(termo)) {
                System.out.println(livro);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("Nenhum livro encontrado com o critério fornecido.");
        }
    }


}