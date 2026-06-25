package main;

import java.util.Objects;

public class NoMapa {
	private final int chave;
	private int valor;
	private NoMapa proximo;

	public NoMapa(int chave, int valor) {
		this.chave = chave;
		this.valor = valor;
	}

	public int getChave() {
		return chave;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public NoMapa getProximo() {
		return proximo;
	}

	public void setProximo(NoMapa proximo) {
		this.proximo = proximo;
	}

	@Override
	public boolean equals(Object objeto) {
		if (this == objeto) {
			return true;
		}
		if (!(objeto instanceof NoMapa outro)) {
			return false;
		}
		return chave == outro.chave;
	}

	@Override
	public int hashCode() {
		return Objects.hash(chave);
	}
}
