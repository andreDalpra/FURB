package questao2;

public class Produto {

	private String nome;
	private double preco;
	private int estoque;
	
	void vender(int quantidade) {
		estoque -= quantidade;
	}
	
	void repor(int quantidade) {
		estoque += quantidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getEstoque() {
		return estoque;
	}

}
