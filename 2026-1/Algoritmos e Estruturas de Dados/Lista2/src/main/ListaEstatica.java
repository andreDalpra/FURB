package main;

import java.util.Arrays;
import java.util.Iterator;

public class ListaEstatica<T> {

	private T[] info;
	private int tamanho;

	@SuppressWarnings("unchecked")
	public ListaEstatica() {

		this.tamanho = 0;
		this.info = (T[]) new Object[10];
	}

	void inserir(T p_valor) {
		if (info.length == tamanho) {
			System.out.println("REDIMENCIONANDO");
			redimensionar();
			return;
		}
		info[tamanho] = p_valor;
		tamanho++;
	}

	@SuppressWarnings("unchecked")
	private void redimensionar() {
		T[] l_novo = (T[]) new Object[tamanho + 10];

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

	int buscar(T p_valor) {
		for (int i = 0; i < tamanho; i++) {
			if (info[i] == p_valor) {
				System.out.println("O numero: " + p_valor + "esta na posicao " + i);
				return i;
			}
		}
		System.out.println("O numero: " + p_valor + "nao esta presente no vetor");
		return -1;
	}

	@SuppressWarnings("unchecked")
	void retirar(T p_valor) {
		int l_remover = 0;
		T[] l_novo;
		l_novo = (T[]) new Object[tamanho];

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

	@SuppressWarnings("unchecked")
	void liberar() {
		tamanho = 0;
		T l_novo[];
		l_novo = (T[]) new Object[10];
		;

		info = l_novo;
	}

	boolean esta_vazio() {
		if (tamanho == 0) {
			return true;
		}
		return false;
	}

	T obterElemento(int p_posicao) {
		//Valida se a posicao é valida
		if ((p_posicao < 0) || p_posicao >= tamanho) {
			throw new IndexOutOfBoundsException();
		}
		
		for (int i = 0; i < tamanho; i++) {
			if (i == p_posicao) {
				System.out.println("O elemento na posicao " + p_posicao + " é o : " + info[i]);
				return info[i];
			}
		}
		return null;
	}
	
	void inverter() {
		int l_comeco = 0;
		int l_fim = tamanho - 1;
		while(l_comeco < l_fim) {
			T l_aux = info[l_comeco];
			info[l_comeco] = info[l_fim];
			info[l_fim] = l_aux;
			
			l_comeco ++;
			l_fim--;
		}
		exibir();
	}

	public int getTamanho() {
		return tamanho;
	}

	@Override
	public String toString() {
		return "ListaEstatica info= " + Arrays.toString(info) + ", tamanho= " + tamanho;
	}

}
