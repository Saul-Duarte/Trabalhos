package pacote;

import java.time.LocalDate;
import java.util.Scanner;

public class BibliotecaApp {

	//Método main para iniciar o código
	public static void main(String[] args) {
		
		//Inicialização da classe Biblioteca e de um Scanner
		Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);
        
        //Menu interativo
        System.out.println("=== Sistema de Gestão de Biblioteca ===");

        while (true) {
        	//Opcões do menu
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
            
            //Variável para acessar as opções do menu
            int opcao = scanner.nextInt();
            scanner.nextLine();    
            
            //Controle de escolha das opções
            switch (opcao) {
	        	case 1:
	        		//Entrada de dados para cadastrar um livro
	        		System.out.print("Título: ");
	        		String titulo = scanner.nextLine();
	            
	        		System.out.print("Autor: ");
	        		String autor = scanner.nextLine();
	            
	        		System.out.print("Gênero (Romance, Ficcao, Biografia, Tecnico, Terror): ");
	        		String generoInput = scanner.nextLine().toUpperCase();
	        		//Tratamento de erro para o enum Genero
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
	
	        		//Cadastro do livro realizado
	        		Livro livro = new Livro(titulo, autor, genero, ano, exemplares);
	        		biblioteca.cadastrarLivro(livro);
	        		System.out.println("Livro cadastrado com sucesso!");
	        		break;
            
		        case 2:
		        	//Entrada de dados para cadastrar um cliente
		        	System.out.print("Nome: ");
		            String nome = scanner.nextLine();
		            
		            System.out.print("CPF: ");
		            String cpf = scanner.nextLine();
		            
		            System.out.print("Tipo de Cliente (ALUNO, PROFESSOR): ");
		            String tipoClienteInput = scanner.nextLine().toUpperCase();
		            //Tratamento de erro para o enum TipoCliente
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
		            
		            //Definição do perfil do cliente
		            Cliente cliente;
		            if (tipoCliente == Cliente.TipoCliente.ALUNO) {
		                cliente = new Aluno(nome, cpf, tipoCliente);
		            } else {
		                cliente = new Professor(nome, cpf, tipoCliente);
		            }
		
		            //Cadastro do cliente realizado
		            biblioteca.cadastrarCliente(cliente);
		            System.out.println("Cliente cadastrado com sucesso!");
		            break;
		            
		        case 3:
		        	//Entrada de dados para realizar o empréstimo de um livro
		        	System.out.print("CPF do Cliente: ");
		            String CPF = scanner.nextLine();
		            
		            System.out.print("Título do Livro: ");
		            String tituloE = scanner.nextLine();
		
		            //Chamada do método de empréstimo
		            biblioteca.emprestarLivro(CPF, tituloE);
		            break;
		            
		        case 4:
		        	//Entrada de dados para realizar a devolução de um livro
		        	System.out.print("CPF do Cliente: ");
		            String CPFd = scanner.nextLine();
		            
		            System.out.print("Título do Livro: ");
		            String tituloD = scanner.nextLine();
		            
		            //A variável dataAtual é considerada como a data da devolução
		            LocalDate dataAtual = LocalDate.now();
		            System.out.println("Você está devolvendo o livro hoje.");
		            System.out.println("Coloque a data que você pegou o livro emprestado (aaaa-mm-dd): ");
		            String dataPegouEmprestadoInput = scanner.nextLine();
		            LocalDate dataPegouEmprestado = LocalDate.parse(dataPegouEmprestadoInput);
		
		            //Chamada das funções de devolução de livros e cálculo de multas
		            biblioteca.devolverLivro(CPFd, tituloD);
		            biblioteca.calcularMulta(CPFd, dataPegouEmprestado, dataAtual);
		            break;
		            
		        case 5:
		        	//Entrada de dados para consultar a multa de um cliente
		        	System.out.print("CPF do Cliente: ");
		            String CPFc = scanner.nextLine();
		
		            //Chamada da função de consultar a multa
		            biblioteca.consultarMulta(CPFc);
		            break;
		            
		        case 6:
		        	//Entrada de dados para pagar a multa de um cliente
		        	System.out.print("CPF do Cliente: ");
		            String CPFq = scanner.nextLine();
		
		            //Chamada da função de pagar a multa
		            biblioteca.pagarMulta(CPFq);
		            break;
		            
		        case 7:
		        	//Chamada da função de listar os livros do sistema
		        	biblioteca.listarLivros();
		        	break;
		        	
		        case 8:
		        	//Chamada da função de listar os clientes do sistema
		        	biblioteca.listarClientes();
		        	break;
		            
		        case 9:
		        	//Entrada de dados para realizar a pesquisa de um livro por titulo ou autor
		        	//Definição do critério da pesquisa
		        	System.out.print("Deseja pesquisar por título ou autor? (Digite 'titulo' ou 'autor'): ");
		        	String criterio = scanner.nextLine().toLowerCase();
		        	
		        	//Definição do critério de entrada (título ou autor)
		        	if (criterio.equals("titulo")) {
		        		System.out.print("Digite o título do livro: ");
		        	} else {
		        		System.out.print("Digite o autor do livro: ");
		        	}
		        	String termo = scanner.nextLine();
		        	
		        	//Chamada da função que pesquisa livro por título ou livro
		            biblioteca.pesquisarLivro(criterio, termo);
		            break;
		            
		        case 10:
		        	//Encerramento do sistema
		        	System.out.println("Saindo do sistema...");
		        	scanner.close();
		        	System.exit(0);
		        	
		        default:
		        	//Tratamento de escolha de uma opção inválida
		        	System.out.println("Opção inválida. Tente novamente.");
		        	break;
            }
        }
    }
	
}