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
            
            int opcao = scanner.nextInt();
            scanner.nextLine();    
            
            switch (opcao) {
	        	case 1:
	        		System.out.print("Título: ");
	        		String titulo = scanner.nextLine();
	            
	        		System.out.print("Autor: ");
	        		String autor = scanner.nextLine();
	            
	        		System.out.print("Gênero (Romance, Ficcao, Biografia, Tecnico, Terror): ");
	        		String generoInput = scanner.nextLine().toUpperCase();
	        		while (true) {		
	        			try {
			            	Livro.Genero genero = Livro.Genero.valueOf(generoInput);
			            	break;
	        			} catch (IllegalArgumentException e) {
			                System.out.println("Tipo de gênero inválido. Por favor, digite: Romance, Ficcao, Biografia, Tecnico ou Terror. ");
			                System.out.print("Gênero: ");
			                generoInput = scanner.nextLine().toUpperCase();
			            } 
			        }
	        		Livro.Genero genero = Livro.Genero.valueOf(generoInput);
	        		System.out.print("Ano de Publicação: ");
	        		int ano = scanner.nextInt();
	            
	        		System.out.print("Número de Exemplares: ");
	        		int exemplares = scanner.nextInt();
	
	        		Livro livro = new Livro(titulo, autor, genero, ano, exemplares);
	        		biblioteca.cadastrarLivro(livro);
	        		System.out.println("Livro cadastrado com sucesso!");
	        		break;
            
		        case 2:
		        	System.out.print("Nome: ");
		            String nome = scanner.nextLine();
		            
		            System.out.print("CPF: ");
		            String cpf = scanner.nextLine();
		            
		            System.out.print("Tipo de Cliente (ALUNO, PROFESSOR): ");
		            String tipoClienteInput = scanner.nextLine().toUpperCase();
		            while(true) {
		            	try {
			            	Cliente.TipoCliente tipoCliente = Cliente.TipoCliente.valueOf(tipoClienteInput);
		                    break;
		            	} catch (IllegalArgumentException e) {
		            		System.out.println("Tipo de cliente inválido. Por favor, digite ALUNO ou PROFESSOR. ");
		            		System.out.print("Gênero: ");
		            		tipoClienteInput = scanner.nextLine().toUpperCase();
		            	}
		            }
		            Cliente.TipoCliente tipoCliente = Cliente.TipoCliente.valueOf(tipoClienteInput);
		            
		            Cliente cliente;
		            if (tipoCliente == Cliente.TipoCliente.ALUNO) {
		                cliente = new Aluno(nome, cpf, tipoCliente);
		            } else {
		                cliente = new Professor(nome, cpf, tipoCliente);
		            }
		
		            biblioteca.cadastrarCliente(cliente);
		            System.out.println("Cliente cadastrado com sucesso!");
		            break;
		            
		        case 3:
		        	System.out.print("CPF do Cliente: ");
		            String CPF = scanner.nextLine();
		            
		            System.out.print("Título do Livro: ");
		            String tituloE = scanner.nextLine();
		
		            biblioteca.emprestarLivro(CPF, tituloE);
		            break;
		            
		        case 4:
		        	System.out.print("CPF do Cliente: ");
		            String CPFd = scanner.nextLine();
		            
		            System.out.print("Título do Livro: ");
		            String tituloD = scanner.nextLine();
		            
		            LocalDate dataAtual = LocalDate.now();
		            System.out.println("Coloque a data que você pegou o livro emprestado (aaaa-mm-dd): ");
		            String dataPegouEmprestadoInput = scanner.nextLine();
		            LocalDate dataPegouEmprestado = LocalDate.parse(dataPegouEmprestadoInput);
		
		            biblioteca.devolverLivro(CPFd, tituloD);
		            biblioteca.calcularMulta(CPFd, dataPegouEmprestado, dataAtual);
		            break;
		            
		        case 5:
		        	System.out.print("CPF do Cliente: ");
		            String CPFc = scanner.nextLine();
		
		            biblioteca.consultarMulta(CPFc);
		            break;
		            
		        case 6:
		        	System.out.print("CPF do Cliente: ");
		            String CPFq = scanner.nextLine();
		
		            biblioteca.pagarMulta(CPFq);
		            break;
		            
		        case 7:
		        	biblioteca.listarLivros();
		        	break;
		        	
		        case 8:
		        	biblioteca.listarClientes();
		        	break;
		            
		        case 9:
		        	System.out.print("Deseja pesquisar por título ou autor? (Digite 'titulo' ou 'autor'): ");
		        	String criterio = scanner.nextLine().toLowerCase();
		        	
		        	if (criterio.equals("titulo")) {
		        		System.out.print("Digite o título do livro: ");
		        	} else {
		        		System.out.print("Digite o autor do livro: ");
		        	}
		        	String termo = scanner.nextLine();
		        	
		            biblioteca.pesquisarLivro(criterio, termo);
		            break;
		            
		        case 10:
		        	System.out.println("Saindo do sistema...");
		        	scanner.close();
		        	System.exit(0);
		        	
		        default:
		        	System.out.println("Opção inválida. Tente novamente.");
		        	break;
            }
        }
    }
	
}