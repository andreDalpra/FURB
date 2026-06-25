package main;

public class NoArvore<T> {
	private final T info;
	private NoArvore<T> esquerda;
	private NoArvore<T> direita;

	public NoArvore(T info) {
		this.info = info;
	}

	public T getInfo() {
		return info;
	}

	public NoArvore<T> getEsquerda() {
		return esquerda;
	}

	public void setEsquerda(NoArvore<T> esquerda) {
		this.esquerda = esquerda;
	}

	public NoArvore<T> getDireita() {
		return direita;
	}

	public void setDireita(NoArvore<T> direita) {
		this.direita = direita;
	}
}
