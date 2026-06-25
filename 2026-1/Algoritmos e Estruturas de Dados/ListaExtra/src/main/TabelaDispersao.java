package main;

public class TabelaDispersao {
	private final NoMapa[] info;
	private int quantidade;

	public TabelaDispersao(int tamanho) {
		if (tamanho <= 0) {
			throw new IllegalArgumentException("O tamanho deve ser maior que zero.");
		}
		info = new NoMapa[tamanho];
	}

	public int calcularHash(int chave) {
		return Math.floorMod(chave, info.length);
	}

	public void inserir(int chave, int valor) {
		int indice = calcularHash(chave);
		NoMapa atual = info[indice];
		NoMapa anterior = null;

		while (atual != null) {
			if (atual.getChave() == chave) {
				atual.setValor(valor);
				return;
			}
			anterior = atual;
			atual = atual.getProximo();
		}

		NoMapa novo = new NoMapa(chave, valor);
		if (anterior == null) {
			info[indice] = novo;
		} else {
			anterior.setProximo(novo);
		}
		quantidade++;
	}

	public Integer buscar(int chave) {
		NoMapa atual = info[calcularHash(chave)];

		while (atual != null) {
			if (atual.getChave() == chave) {
				return atual.getValor();
			}
			atual = atual.getProximo();
		}

		return null;
	}

	public boolean remover(int chave) {
		int indice = calcularHash(chave);
		NoMapa atual = info[indice];
		NoMapa anterior = null;

		while (atual != null) {
			if (atual.getChave() == chave) {
				if (anterior == null) {
					info[indice] = atual.getProximo();
				} else {
					anterior.setProximo(atual.getProximo());
				}
				quantidade--;
				return true;
			}
			anterior = atual;
			atual = atual.getProximo();
		}

		return false;
	}

	public double fatorCarga() {
		return (double) quantidade / info.length;
	}

	public int getQuantidade() {
		return quantidade;
	}

	@Override
	public String toString() {
		StringBuilder resultado = new StringBuilder();

		for (int i = 0; i < info.length; i++) {
			resultado.append(i).append(": ");
			NoMapa atual = info[i];

			if (atual == null) {
				resultado.append("vazio");
			}
			while (atual != null) {
				resultado.append('[')
						.append(atual.getChave())
						.append('=')
						.append(atual.getValor())
						.append(']');
				atual = atual.getProximo();
				if (atual != null) {
					resultado.append(" -> ");
				}
			}
			resultado.append(System.lineSeparator());
		}

		return resultado.toString();
	}
}
