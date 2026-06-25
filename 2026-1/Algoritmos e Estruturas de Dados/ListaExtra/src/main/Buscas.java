package main;

public final class Buscas {

	private Buscas() {
	}

	public static <T extends Comparable<? super T>> int buscaBinaria(T[] info, T valorBuscar) {
		int inicio = 0;
		int fim = info.length - 1;

		while (inicio <= fim) {
			int meio = inicio + (fim - inicio) / 2;
			int comparacao = valorBuscar.compareTo(info[meio]);

			if (comparacao < 0) {
				fim = meio - 1;
			} else if (comparacao > 0) {
				inicio = meio + 1;
			} else {
				return meio;
			}
		}

		return -1;
	}

	public static <T extends Comparable<? super T>> int buscarPrimeiraOcorrencia(T[] info, T valor) {
		int inicio = 0;
		int fim = info.length - 1;
		int primeiraOcorrencia = -1;

		while (inicio <= fim) {
			int meio = inicio + (fim - inicio) / 2;
			int comparacao = valor.compareTo(info[meio]);

			if (comparacao < 0) {
				fim = meio - 1;
			} else if (comparacao > 0) {
				inicio = meio + 1;
			} else {
				primeiraOcorrencia = meio;
				fim = meio - 1;
			}
		}

		return primeiraOcorrencia;
	}
}
