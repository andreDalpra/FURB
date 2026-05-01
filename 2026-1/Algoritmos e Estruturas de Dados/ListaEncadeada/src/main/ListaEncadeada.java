package main;

public class ListaEncadeada<T> {

	private NoLista<T> primeiro;

	public NoLista<T> getPrimeiro() {
		return primeiro;
	}

	public void inserir(T info) {
 
		NoLista<T> novo = new NoLista<>();

		novo.setInfo(info);
		novo.setProximo(primeiro);

		primeiro = novo;
	}

	public void exibir() {

		NoLista<T> p = primeiro;

		while (p != null) {
			System.out.println(p.getInfo());
			p = p.getProximo();
		}
	}

	public boolean estaVazio() {
		return primeiro == null;
	}

	public NoLista<T> buscar(T valor) {

		NoLista<T> p = primeiro;

		while (p != null) {

			if (p.getInfo().equals(valor)) {
				return p;
			}

			p = p.getProximo();
		}

		return null;
	}

	public void retirar(T valor) {
		NoLista<T> anterior = null;
		NoLista<T> p = primeiro;

		while ((p != null) && (p.getInfo() != valor)) {
			anterior = p;
			p = p.getProximo();
		}

		if (p != null) {
			if (p == primeiro) {
				primeiro = p.getProximo();
			} else {
				anterior.setProximo(p.getProximo());
			}
		}
	}

	public int obterComprimento() {

		NoLista<T> p = primeiro;
		int l_contador = 0;

		while (p != null) {
			p = p.getProximo();
			l_contador++;
		}

		return l_contador;
	}

	public NoLista<T> obterNo(int idx) {

		if (idx < 0) {
			throw new IndexOutOfBoundsException("Índice negativo: " + idx);
		}

		NoLista<T> p = primeiro;
		int contador = 0;

		while (p != null && contador < idx) {
			p = p.getProximo();
			contador++;
		}

		if (p == null) {
		 	throw new IndexOutOfBoundsException("Índice maior que o tamanho da lista: " + idx);
		}

		return p;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		NoLista<T> p = primeiro;

		while (p != null) {
			sb.append(p.getInfo());

			if (p.getProximo() != null) {
				sb.append(" -> ");
			}

			p = p.getProximo();
		}

		return sb.toString();
	}

	public ListaEncadeada() {
		this.primeiro = null;
	}

}
