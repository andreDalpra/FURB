package questao2;

public class Main {

	public static void main(String[] args) {

		Produto pro = new Produto();

		pro.setNome("teste");
		pro.setPreco(9.99);

		// Tentando acessar diretamento os atributos private
		// pro.preco = 1;
		// pro.nome = "Nome errado";
        pro.repor(100);
		pro.vender(10);
		pro.repor(15);
		System.out.println("Nome Produto: "+ pro.getNome());
		System.out.println("Preço produto: "+ pro.getPreco());
		System.out.println("Quantidade em estoque: " + pro.getEstoque());

	}

}
