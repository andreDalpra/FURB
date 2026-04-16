package main;

public class PilhaVetor<T> implements Pilha<T> {

	private T[] info;
	private int tamanho;
	private int limite;

	@SuppressWarnings("unchecked")
	public PilhaVetor(int limite) {
		this.tamanho = 0;
		this.limite = limite;
		this.info = (T[]) new Object[limite];
	}

	@Override
	public void push(T valor) {
		if (limite == tamanho) {
			throw new PilhaCheiaException("CAPACIDADE MAXIMA");
		}

		info[tamanho] = valor;
		tamanho++;
	}

	@Override
	public T pop() {
		// Remove da pilha ( o topo da pilha);
		T valor;
		T retorno;
		
		valor = peek();
		retorno = valor;
		valor = null;
		
		tamanho--;

		return retorno;
	}

	@Override
	public T peek() {
		// Obtem o topo da pilha
		if (estaVazia()) {
			throw new PilhaVaziaException("PILHA VAZIA");
		}
		return info[tamanho - 1];
	}

	@Override
	public boolean estaVazia() {
		if (tamanho == 0) {
			return true;
		}
		return false;
	}

	@Override
	public void liberar() {
		T valor;
		while(!estaVazia()) {
			valor = pop();
			System.out.println("Removendo o valor:" + valor);
		}
	}

	@SuppressWarnings("unchecked")
	public String obtemPilha() {
		T[] l_novo;
		T valor;
		String pilha = "";

		l_novo = info;

		for (int i = 0; i < tamanho - 1; i++) {
			valor = pop();
			pilha = pilha + ", " + valor;

		}
		info = l_novo;
		
		return pilha;
	}

	public void concatenar(PilhaVetor<T> p) {
		
		PilhaVetor<T> auxiliar = new PilhaVetor<T>(limite);
		
		if  (p.estaVazia()){
        	throw new PilhaVaziaException("PILHA VAZIA");
        }
		
		while (!p.estaVazia()) {
			auxiliar.push(p.pop());
		}
		
		while(!auxiliar.estaVazia()) {
			push(auxiliar.pop());
		}
		
		if  (tamanho + p.tamanho > limite) {
			throw new PilhaCheiaException("PILHA CHEIA");
		}
	}

	public String toString() {
		String pilha = "";

		pilha = obtemPilha();

		return pilha;

	}

}
