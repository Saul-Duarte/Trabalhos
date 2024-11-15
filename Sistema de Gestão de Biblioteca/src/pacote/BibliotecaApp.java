package pacote;

import java.time.LocalDate;
import java.util.Scanner;

public class BibliotecaApp {

	public static void main(String[] args) {
		
		Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Sistema de Gestão de Biblioteca ===");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Cadastrar Cliente");
            System.out.println("3. Realizar Empréstimo");
            System.out.println("4. Realizar Devolução");
            System.out.println("5. Consultar Multas");
            System.out.println("6. Pagar Multa");
            System.out.println("7. Listar Livros");
            System.out.println("8. Listar Clientes");
            System.out.println("9. Pesquisar Livro por Título ou Autor");
            System.out.println("10. Sair");
            System.out.print("Escolha uma opção: ");
        }
	
        int opcao = scanner.nextInt();
        scanner.nextLine();    
	}

	switch (opcao) {
    case 1:
    	System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Gênero (Romance, Ficcao, Biografia, Tecnico): ");
        Genero genero = Genero.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Ano de Publicação: ");
        int ano = scanner.nextInt();
        System.out.print("Número de Exemplares: ");
        int exemplares = scanner.nextInt();

        Livro livro = new Livro(titulo, autor, genero, ano, exemplares);
        biblioteca.adicionarLivro(livro);
        System.out.println("Livro cadastrado com sucesso!");
        break;
        
    case 2:
    	System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Tipo de Cliente (ALUNO, PROFESSOR): ");
        TipoCliente tipo = TipoCliente.valueOf(scanner.nextLine().toUpperCase());

        Cliente cliente;
        if (tipo == TipoCliente.ALUNO) {
            cliente = new Aluno(nome, cpf);
        } else {
            cliente = new Professor(nome, cpf);
        }

        biblioteca.cadastrarCliente(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
        break;
        
    case 3:
    	System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine();
        System.out.print("Título do Livro: ");
        String titulo = scanner.nextLine();

        try {
            biblioteca.realizarEmprestimo(cpf, titulo);
            System.out.println("Empréstimo realizado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        break;
        
    case 4:
    	System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine();
        System.out.print("Título do Livro: ");
        String titulo = scanner.nextLine();

        try {
            biblioteca.realizarDevolucao(cpf, titulo, LocalDate.now());
            System.out.println("Devolução realizada com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        break;
        
    case 5:
    	System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = biblioteca.buscarCliente(cpf);
        if (cliente != null) {
            System.out.printf("Saldo de Multas: R$ %.2f%n", cliente.getMultaPendente());
        } else {
            System.out.println("Cliente não encontrado.");
        }
        break;
        
    case 6:
    	System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = biblioteca.buscarCliente(cpf);
        if (cliente != null) {
            cliente.pagarMulta();
            System.out.println("Multa quitada com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
        break;
        
    case 7:
    	blioteca.ListarLivros();
    	break;
    	
    case 8:
    	biblioteca.ListarClientes();
    	break;
        
    case 9:
    	System.out.print("Digite o título ou autor do livro: ");
        String termo = scanner.nextLine();

        biblioteca.pesquisarLivros(termo).forEach(System.out::println);
        break;
        
    case 10:
    	System.out.println("Saindo do sistema...");
    	break;
    	
    default:
    	System.out.println("Opção inválida. Tente novamente.");
    	break;
    }      
}
