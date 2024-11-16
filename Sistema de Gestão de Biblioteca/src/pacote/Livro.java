package pacote;

public class Livro {
	
	//Enum referente ao gênero literário
	enum Genero {
	    ROMANCE, FICCAO, BIOGRAFIA, TECNICO, TERROR
	}

	//Atributos da classe Livro
	private String titulo;
    private String autor;
    private Genero genero;
    private int anoPubli;
    private int exemplaresDisponiveis;

    //Construtor da classe Livro
    public Livro(String titulo, String autor, Genero genero, int anoPublicacao, int exemplaresDisponiveis) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.anoPubli = anoPublicacao;
        this.exemplaresDisponiveis = exemplaresDisponiveis;
    }

    //Getters e Setters da classe Livro
    public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public int getAnoPubli() {
		return anoPubli;
	}

	public void setAnoPubli(int anoPubli) {
		this.anoPubli = anoPubli;
	}

	public int getExemplaresDisponiveis() {
		return exemplaresDisponiveis;
	}

	public void setExemplaresDisponiveis(int exemplaresDisponiveis) {
		this.exemplaresDisponiveis = exemplaresDisponiveis;
	}
	
	//Método para reduzir o número de exemplares de um livro que foi emprestado
	public void emprestar() {
        exemplaresDisponiveis--;
    }

	//Método para aumentar o número de exemplares de um livro que foi devolvido
    public void devolver() {
        exemplaresDisponiveis++;
    }

    //Método para listar o livro com seus atributos
	@Override
    public String toString() {
        return "Livro: " + titulo + ", Autor: " + autor + ", Gênero: " + genero + ", Ano: " + anoPubli + 
               ", Exemplares Disponíveis: " + exemplaresDisponiveis;
    }
	
}