package main;

import java.util.ArrayList;
import java.util.List;

public class ArvoreBinariaBusca<T extends Comparable<? super T>> {
	private NoArvore<T> raiz;

	public NoArvore<T> getRaiz() {
		return raiz;
	}

	public void inserir(T valor) {
		NoArvore<T> novo = new NoArvore<>(valor);

		if (raiz == null) {
			raiz = novo;
			return;
		}

		NoArvore<T> atual = raiz;
		while (true) {
			if (valor.compareTo(atual.getInfo()) < 0) {
				if (atual.getEsquerda() == null) {
					atual.setEsquerda(novo);
					return;
				}
				atual = atual.getEsquerda();
			} else {
				if (atual.getDireita() == null) {
					atual.setDireita(novo);
					return;
				}
				atual = atual.getDireita();
			}
		}
	}

	public NoArvore<T> buscar(T valor) {
		NoArvore<T> atual = raiz;

		while (atual != null) {
			int comparacao = valor.compareTo(atual.getInfo());
			if (comparacao == 0) {
				return atual;
			}
			atual = comparacao < 0 ? atual.getEsquerda() : atual.getDireita();
		}

		return null;
	}

	public void imprimirEmOrdem(NoArvore<T> no) {
		if (no != null) {
			imprimirEmOrdem(no.getEsquerda());
			System.out.print(no.getInfo() + " ");
			imprimirEmOrdem(no.getDireita());
		}
	}

	public List<T> emOrdem() {
		List<T> valores = new ArrayList<>();
		adicionarEmOrdem(raiz, valores);
		return valores;
	}

	private void adicionarEmOrdem(NoArvore<T> no, List<T> valores) {
		if (no != null) {
			adicionarEmOrdem(no.getEsquerda(), valores);
			valores.add(no.getInfo());
			adicionarEmOrdem(no.getDireita(), valores);
		}
	}

	public static <T extends Comparable<? super T>> ArvoreBinariaBusca<T> deVetorOrdenado(T[] vetor) {
		ArvoreBinariaBusca<T> arvore = new ArvoreBinariaBusca<>();
		arvore.raiz = construirBalanceada(vetor, 0, vetor.length - 1);
		return arvore;
	}

	private static <T extends Comparable<? super T>> NoArvore<T> construirBalanceada(
			T[] vetor, int inicio, int fim) {
		if (inicio > fim) {
			return null;
		}

		int meio = inicio + (fim - inicio) / 2;
		NoArvore<T> no = new NoArvore<>(vetor[meio]);
		no.setEsquerda(construirBalanceada(vetor, inicio, meio - 1));
		no.setDireita(construirBalanceada(vetor, meio + 1, fim));
		return no;
	}
}
