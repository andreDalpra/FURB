package main;

public class ListaDuplamenteEncadeada<T> {

	@SuppressWarnings("unused")
	private NoListaDupla<T> primeiro;

	public void inserir(T valor) {
		NoListaDupla<T> novo = new NoListaDupla<>();

		novo.setInfo(valor);
		novo.setProximo(primeiro);;
		novo.setAnterior(null);

		if (primeiro != null) {
			primeiro.setAnterior(novo);
		}

		primeiro = novo;
	}
	public NoListaDupla<T> buscar(T valor) {
		NoListaDupla<T> p = primeiro;

		while (p != null) {
			if (p.getInfo().equals(valor)) {
				return p;
			}

			p = p.getProximo();
		}
		return null;
	}

	public void retirar(T valor) {
		NoListaDupla<T> p = buscar(valor);

		if (p != null) { // achou
			if (primeiro == p) {
				primeiro = p.getProximo();
			} else {
				p.getAnterior().setProximo(p.getProximo());
			}

			if (p.getProximo() != null) { // nao eh o ultimo?
				p.getProximo().setAnterior(p.getAnterior());
			}
		}
	}
	
	public void exibir() {
		NoListaDupla<T> p = primeiro;

		while (p != null) {
			System.out.println(p.getInfo());
			p = p.getProximo();
		}
	}

	public NoListaDupla<T> buscaUltimo() {
		NoListaDupla<T> p = primeiro;

		while (p.getProximo() != null) {
			p = p.getProximo();
		}

		return p; // Ultimo nó
	}

	public void exibirOrdemInversa() {
		NoListaDupla<T> p = buscaUltimo();

		while (p != null) {
			System.out.println(p.getInfo());
			p = p.getAnterior();
		}
	}

	public void liberar() {
		NoListaDupla<T> p = primeiro;
		NoListaDupla<T> proximo;

		while (p.getProximo() != null) {
			proximo = p.getProximo();
			p.setProximo(null);
			p.setAnterior(null);
			p.setInfo(null);

			p = proximo;

		}
		primeiro = null;
	}

	public ListaDuplamenteEncadeada() {
		this.primeiro = null;
	}

	public NoListaDupla<T> getPrimeiro() {
		return primeiro;
	}

}
