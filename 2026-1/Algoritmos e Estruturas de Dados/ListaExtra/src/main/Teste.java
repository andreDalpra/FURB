package main;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class Teste {

	@Test
	void deveOrdenarComBubbleSort() {
		Integer[] vetor = { 5, 2, 4, 2, 1, 3 };

		AlgoritmosOrdenacao.bubbleSort(vetor);

		assertArrayEquals(new Integer[] { 1, 2, 2, 3, 4, 5 }, vetor);
	}

	@Test
	void deveOrdenarComQuickSort() {
		Integer[] vetor = { 5, 2, 4, 2, 1, 3 };

		AlgoritmosOrdenacao.quickSort(vetor);

		assertArrayEquals(new Integer[] { 1, 2, 2, 3, 4, 5 }, vetor);
	}

	@Test
	void deveOrdenarComMergeSort() {
		Integer[] vetor = { 5, 2, 4, 2, 1, 3 };

		AlgoritmosOrdenacao.mergeSort(vetor);

		assertArrayEquals(new Integer[] { 1, 2, 2, 3, 4, 5 }, vetor);
	}

	@Test
	void deveTratarVetorVazioNasOrdenacoesRecursivas() {
		Integer[] quick = {};
		Integer[] merge = {};

		AlgoritmosOrdenacao.quickSort(quick);
		AlgoritmosOrdenacao.mergeSort(merge);

		assertArrayEquals(new Integer[] {}, quick);
		assertArrayEquals(new Integer[] {}, merge);
	}

	@Test
	void deveVerificarSeVetorEstaOrdenado() {
		assertTrue(AlgoritmosOrdenacao.estaOrdenado(
				new Integer[] { 1, 2, 2, 3 }));
		assertFalse(AlgoritmosOrdenacao.estaOrdenado(
				new Integer[] { 2, 1 }));
	}

	@Test
	void deveExecutarBuscaBinaria() {
		Integer[] valores = { 10, 20, 20, 20, 30, 40 };

		assertEquals(4, Buscas.buscaBinaria(valores, 30));
		assertEquals(-1, Buscas.buscaBinaria(valores, 25));
	}

	@Test
	void deveBuscarPrimeiraOcorrencia() {
		Integer[] valores = { 10, 20, 20, 20, 30, 40 };

		assertEquals(1, Buscas.buscarPrimeiraOcorrencia(valores, 20));
		assertEquals(-1, Buscas.buscarPrimeiraOcorrencia(valores, 99));
	}

	@Test
	void deveInserirBuscarAtualizarERemoverNaTabela() {
		TabelaDispersao tabela = new TabelaDispersao(13);
		tabela.inserir(30, 300);
		tabela.inserir(4, 40);
		tabela.inserir(17, 170);

		assertEquals(170, tabela.buscar(17));
		assertTrue(tabela.remover(30));
		assertNull(tabela.buscar(30));
		assertEquals(40, tabela.buscar(4));
		assertTrue(tabela.remover(17));
		assertFalse(tabela.remover(99));
		assertEquals(1, tabela.getQuantidade());

		tabela.inserir(4, 400);
		assertEquals(400, tabela.buscar(4));
		assertEquals(1, tabela.getQuantidade());
	}

	@Test
	void deveCalcularFatorDeCarga() {
		TabelaDispersao tabela = new TabelaDispersao(13);
		tabela.inserir(10, 10);
		tabela.inserir(30, 30);
		tabela.inserir(22, 22);
		tabela.inserir(33, 33);
		tabela.inserir(4, 4);
		tabela.inserir(17, 17);

		assertEquals(6.0 / 13.0, tabela.fatorCarga(), 0.0001);
	}

	@Test
	void deveConstruirArvoreBalanceada() {
		Integer[] valores = { 20, 30, 40, 50, 60, 70, 80 };

		ArvoreBinariaBusca<Integer> arvore =
				ArvoreBinariaBusca.deVetorOrdenado(valores);

		assertEquals(50, arvore.getRaiz().getInfo());
		assertEquals(60, arvore.buscar(60).getInfo());
		assertNull(arvore.buscar(100));
		assertEquals(Arrays.asList(valores), arvore.emOrdem());
	}
}
