package pacote;

public class Livro {
	
	enum Genero {
	    ROMANCE, FICCAO, BIOGRAFIA, TECNICO, TERROR
	}

	private String titulo;
    private String autor;
    private Genero genero;
    private int anoPubli;
    private int exemplaresDisponiveis;

    public Livro(String titulo, String autor, Genero genero, int anoPublicacao, int exemplaresDisponiveis) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.anoPubli = anoPublicacao;
        this.exemplaresDisponiveis = exemplaresDisponiveis;
    }

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
	
	public void emprestar() {
        exemplaresDisponiveis--;
    }

    public void devolver() {
        exemplaresDisponiveis++;
    }

	@Override
    public String toString() {
        return "Livro: " + titulo + ", Autor: " + autor + ", Gênero: " + genero + ", Ano: " + anoPubli + 
               ", Exemplares Disponíveis: " + exemplaresDisponiveis;
    }
	
}