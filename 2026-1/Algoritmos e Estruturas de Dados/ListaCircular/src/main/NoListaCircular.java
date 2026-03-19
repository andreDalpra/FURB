package main;

public class NoListaCircular<T> {
	private T info;
	private NoListaCircular<T> proximo;

	public T getInfo() {
		return info;
	}

	public void setInfo(T info) {
		this.info = info;
	}

	public NoListaCircular<T> getProximo() {
		return proximo;
	}

	public void setProximo(NoListaCircular<T> proximo) {
		this.proximo = proximo;
	}

}
