package questao2;

public class Livro {
	private String titulo;
	private String autor;
	private int anoPublicacao;
	private static int totalLivros;

	public Livro() {
		this.titulo = "Sem título";
		this.autor = "Desconhecido";
		this.anoPublicacao = 0;
		totalLivros++;
	}

	public Livro(String titulo, String autor, int ano) {
		this.titulo = titulo;
		this.autor = autor;
		anoPublicacao = ano;
		totalLivros++;
	}

	public static int getTotalLivros() {
		return totalLivros;
	}

	public String exibirInfo() {
		return "Título: " + titulo + " / Autor: " + autor + " / Ano Publicação: " + anoPublicacao + "\n";
	}

	public String exibirInfo(boolean resumido) {
		if (resumido) {
			return "Título:" + titulo;
		} 
			return exibirInfo();
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

	public int getAnoPublicacao() {
		return anoPublicacao;
	}

	public void setAnoPublicacao(int anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

}
