package main;

public record Produto(String nome, double preco) implements Comparable<Produto> {

	@Override
	public int compareTo(Produto outro) {
		int comparacaoPreco = Double.compare(preco, outro.preco);
		return comparacaoPreco != 0 ? comparacaoPreco : nome.compareTo(outro.nome);
	}
}
