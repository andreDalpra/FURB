package main;

public final class AlgoritmosOrdenacao {

	private AlgoritmosOrdenacao() {
	}

	public static <T extends Comparable<? super T>> void bubbleSort(T[] info) {
		for (int i = info.length - 1; i >= 1; i--) {
			boolean trocou = false;

			for (int j = 0; j < i; j++) {
				if (info[j].compareTo(info[j + 1]) > 0) {
					trocar(info, j, j + 1);
					trocou = true;
				}
			}

			if (!trocou) {
				return;
			}
		}
	}

	public static <T extends Comparable<? super T>> void quickSort(T[] info) {
		quickSort(info, 0, info.length - 1);
	}

	private static <T extends Comparable<? super T>> void quickSort(T[] info, int inicio, int fim) {
		if (inicio < fim) {
			int indicePivo = particionar(info, inicio, fim);
			quickSort(info, inicio, indicePivo - 1);
			quickSort(info, indicePivo + 1, fim);
		}
	}

	private static <T extends Comparable<? super T>> int particionar(T[] info, int inicio, int fim) {
		int a = inicio;
		int b = fim + 1;
		T pivo = info[inicio];

		while (true) {
			do {
				a++;
			} while (a <= fim && info[a].compareTo(pivo) < 0);

			do {
				b--;
			} while (b >= inicio && info[b].compareTo(pivo) > 0);

			if (a >= b) {
				break;
			}

			trocar(info, a, b);
		}

		trocar(info, inicio, b);
		return b;
	}

	public static <T extends Comparable<? super T>> void mergeSort(T[] info) {
		mergeSort(info, 0, info.length - 1);
	}

	private static <T extends Comparable<? super T>> void mergeSort(T[] info, int inicio, int fim) {
		if (inicio < fim) {
			int meio = (inicio + fim) / 2;
			mergeSort(info, inicio, meio);
			mergeSort(info, meio + 1, fim);
			merge(info, inicio, fim, meio);
		}
	}

	private static <T extends Comparable<? super T>> void merge(T[] info, int inicio, int fim, int meio) {
		Object[] esquerda = new Object[meio - inicio + 1];
		Object[] direita = new Object[fim - meio];

		for (int i = 0; i < esquerda.length; i++) {
			esquerda[i] = info[inicio + i];
		}
		for (int i = 0; i < direita.length; i++) {
			direita[i] = info[meio + 1 + i];
		}

		int cEsq = 0;
		int cDir = 0;
		int i = inicio;

		while (cEsq < esquerda.length && cDir < direita.length) {
			T valorEsquerda = elemento(esquerda, cEsq);
			T valorDireita = elemento(direita, cDir);

			if (valorEsquerda.compareTo(valorDireita) <= 0) {
				info[i++] = valorEsquerda;
				cEsq++;
			} else {
				info[i++] = valorDireita;
				cDir++;
			}
		}

		while (cEsq < esquerda.length) {
			info[i++] = elemento(esquerda, cEsq++);
		}
		while (cDir < direita.length) {
			info[i++] = elemento(direita, cDir++);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T elemento(Object[] vetor, int indice) {
		return (T) vetor[indice];
	}

	public static <T extends Comparable<? super T>> boolean estaOrdenado(T[] vetor) {
		for (int i = 0; i < vetor.length - 1; i++) {
			if (vetor[i].compareTo(vetor[i + 1]) > 0) {
				return false;
			}
		}
		return true;
	}

	private static <T> void trocar(T[] info, int posicao1, int posicao2) {
		T temporario = info[posicao1];
		info[posicao1] = info[posicao2];
		info[posicao2] = temporario;
	}
}
