package main;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		demonstrarOrdenacoes();
		demonstrarComparable();
		demonstrarBuscas();
		demonstrarTabelaDispersao();
		demonstrarArvore();
	}

	private static void demonstrarOrdenacoes() {
		Integer[] bubble = { 40, 20, 50, 10, 30 };
		Integer[] quick = bubble.clone();
		Integer[] merge = bubble.clone();

		AlgoritmosOrdenacao.bubbleSort(bubble);
		AlgoritmosOrdenacao.quickSort(quick);
		AlgoritmosOrdenacao.mergeSort(merge);

		System.out.println("Bubble: " + Arrays.toString(bubble));
		System.out.println("Quick:  " + Arrays.toString(quick));
		System.out.println("Merge:  " + Arrays.toString(merge));
		System.out.println("Está ordenado: " + AlgoritmosOrdenacao.estaOrdenado(merge));
	}

	private static void demonstrarComparable() {
		Produto[] produtos = {
				new Produto("Teclado", 150.0),
				new Produto("Mouse", 80.0),
				new Produto("Monitor", 900.0)
		};
		AlgoritmosOrdenacao.mergeSort(produtos);
		System.out.println("Produtos por preço: " + Arrays.toString(produtos));
	}

	private static void demonstrarBuscas() {
		Integer[] vetor = { 10, 20, 30, 40, 50, 60, 70, 80, 90 };
		Integer[] duplicados = { 10, 20, 20, 20, 30, 40 };

		System.out.println("Índice de 70: " + Buscas.buscaBinaria(vetor, 70));
		System.out.println("Índice de 25: " + Buscas.buscaBinaria(vetor, 25));
		System.out.println("Primeiro índice de 20: "
				+ Buscas.buscarPrimeiraOcorrencia(duplicados, 20));
	}

	private static void demonstrarTabelaDispersao() {
		TabelaDispersao tabela = new TabelaDispersao(13);
		int[] chaves = { 10, 30, 22, 33, 4, 17 };

		for (int chave : chaves) {
			tabela.inserir(chave, chave);
		}

		System.out.println("Tabela de dispersão:");
		System.out.print(tabela);
		System.out.println("Busca da chave 17: " + tabela.buscar(17));
		System.out.printf("Fator de carga: %.4f%n", tabela.fatorCarga());
		System.out.println("Removeu a chave 4: " + tabela.remover(4));
		System.out.println("Busca da chave 4: " + tabela.buscar(4));
	}

	private static void demonstrarArvore() {
		ArvoreBinariaBusca<Integer> arvore = new ArvoreBinariaBusca<>();
		int[] valores = { 50, 30, 70, 20, 40, 60, 80 };

		for (int valor : valores) {
			arvore.inserir(valor);
		}

		System.out.println("Árvore em ordem: " + arvore.emOrdem());
		System.out.println("Encontrou 60: " + (arvore.buscar(60) != null));

		Integer[] ordenado = { 20, 30, 40, 50, 60, 70, 80 };
		ArvoreBinariaBusca<Integer> balanceada =
				ArvoreBinariaBusca.deVetorOrdenado(ordenado);
		System.out.println("Vetor convertido em árvore balanceada: " + balanceada.emOrdem());
	}
}
