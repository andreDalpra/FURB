package main;

@SuppressWarnings("unchecked")
public class FilaVetor<T> implements Fila<T> {
    private T[] info;
    private int tamanho;
    private int limite;
    private int inicio;
    
    
	public FilaVetor(int p_limite) {
		this.limite = p_limite;
		this.info = (T[]) new Object[limite];
		this.tamanho = 0;
		this.inicio = 0;
	}
	@Override
	public void inserir(T valor) {
		if (tamanho == limite) {
			throw new FilaCheiaException("FILA CHEIA");
		}
		
		int posicao = (inicio + tamanho) % limite;
		
		info[posicao] = valor;
		tamanho++;		
	}
	@Override
	public boolean estaVazia() {
		if (tamanho == 0) {
			return true;
		}
		return false;
	}
	@Override
	public T peek() {
		if (estaVazia()) {
			throw new FilaVaziaException("FILA VAZIA");
		}
		return info[inicio];
	}
	@Override
	public T retirar() {
		T valor = peek();
		
		inicio = (inicio + 1) % limite;
		tamanho--;
		
		return valor;
	}
	@Override
	public void liberar() {
		info = null;
		tamanho = 0;
		inicio = 0;
	}
	
	public T[] criarFilaConcatenada(FilaVetor<T> p) {
	    FilaVetor<T> auxiliar = new FilaVetor<T>(limite);

	    if (tamanho + p.tamanho > limite) {
	        throw new FilaCheiaException("FILA CHEIA");
	    }
	    if (p.estaVazia()) {
	        throw new FilaVaziaException("FILA VAZIA");
	    }
	  
	    while (!p.estaVazia()) {
	        auxiliar.inserir(p.retirar());
	    }

	    while (!auxiliar.estaVazia()) {
	        inserir(auxiliar.retirar());
	    }
	    
	    return info;
	}
	
	public int getTamanho() {
		return tamanho;
	}
	public int getLimite() {
		return limite;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < tamanho; i++) {
	        sb.append(info[(inicio + i) % limite]);
	        if (i < tamanho - 1) {
	            sb.append(",");
	        }
	    }
	    return sb.toString();
	}
}
