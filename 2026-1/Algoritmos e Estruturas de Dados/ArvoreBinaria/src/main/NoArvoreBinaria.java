package main;

public class NoArvoreBinaria<T> {
	private T info;
	private NoArvoreBinaria<T> esquerda;

	public NoArvoreBinaria(T info) {
		this.info = info;
	}

	public NoArvoreBinaria(T info, NoArvoreBinaria<T> esquerda, NoArvoreBinaria<T> direita) {
		this.info = info;
		this.esquerda = esquerda;
		this.direita = direita;
	}

	public T getInfo() {
		return info;
	}

	public void setInfo(T info) {
		this.info = info;
	}

	public NoArvoreBinaria<T> getEsquerda() {
		return esquerda;
	}

	public void setEsquerda(NoArvoreBinaria<T> esquerda) {
		this.esquerda = esquerda;
	}

	public NoArvoreBinaria<T> getDireita() {
		return direita;
	}

	public void setDireita(NoArvoreBinaria<T> direita) {
		this.direita = direita;
	}

	private NoArvoreBinaria<T> direita;
}
