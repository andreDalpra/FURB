package questao2;

public class Main {
	public static void main(String[] args) {
		Livro [] f_livro = new Livro[4];
		
		f_livro[0] = new Livro("O poder do Habito", "Não sei", 2005);
		f_livro[1] = new Livro();
		f_livro[2] = new Livro("O poder do Milênio", "O cara do milênio", 2000);
		f_livro[3] = new Livro("O poder do Século", "O cara do século", 1900);
		
		System.out.println("Mostrando os livros\n");
		for (int i = 0; i < f_livro.length; i++) {
			System.out.println(f_livro[i].exibirInfo());
		}
		
		System.out.println("Quantidade de Livros: "+ Livro.getTotalLivros());
		
		System.out.println("\nExibindo resumo livro: \n"+f_livro[0].exibirInfo(true));
		
	}
}