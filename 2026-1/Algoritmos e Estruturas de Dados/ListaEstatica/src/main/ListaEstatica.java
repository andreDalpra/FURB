package main;

import java.util.Arrays;
import java.util.Iterator;

public class ListaEstatica {

	private int info[];
	private int tamanho;

	public ListaEstatica() {

		this.tamanho = 0;
		this.info = new int[10];
	}

	void inserir(int p_valor) {
		if (info.length == tamanho) {
			System.out.println("REDIMENCIONANDO");
			redimensionar();
			return;
		}
		info[tamanho] = p_valor;
		tamanho++;
	}

	private void redimensionar() {
		int l_novo[];
		l_novo = new int[tamanho + 10];

		for (int i = 0; i < tamanho; i++) {
			l_novo[i] = info[i];
		}
		info = l_novo;
	}

	void exibir() {
		for (int i = 0; i < tamanho; i++) {
			System.out.println((i + 1) + "º: " + info[i]);
		}
	}

	int buscar(int p_valor) {
		for (int i = 0; i < tamanho; i++) {
			if (info[i] == p_valor) {
				System.out.println("O numero: " + p_valor + "esta na posicao " + i);
				return i;
			}
		}
		System.out.println("O numero: " + p_valor + "nao esta presente no vetor");
		return -1;
	}

	void retirar(int p_valor) {
		int l_remover = 0;
		int l_novo[];
		l_novo = new int[tamanho];

		for (int i = 0; i < tamanho; i++) {
			if (info[i] == p_valor) {
				l_remover = i;
				break;
			}
		}

		for (int i = 0; i < tamanho; i++) {
			if (l_remover != i) {
				l_novo[i] = info[i];
			}
		}
		info = l_novo;
		tamanho--;
	}

	void liberar() {
		int l_novo[];
		l_novo = new int[10];

		info = l_novo;
	}

	boolean esta_vazio() {
		if (tamanho == 0) {
			return true;
		}
		return false;
	}

	int obterElemento(int p_posicao) {
		for (int i = 0; i < tamanho; i++) {
			try {
				if (i == p_posicao) {
					System.out.println("O elemento na posicao " + p_posicao + " é o : " + info[i]);
					return info[i];
				}

			} catch (IndexOutOfBoundsException e) {
				System.out.println(e.getMessage());
			}
		}
		return 0;
	}

	public int getTamanho() {
		return tamanho;
	}

	@Override
	public String toString() {
		return "ListaEstatica info= " + Arrays.toString(info) + ", tamanho= " + tamanho;
	}

}
