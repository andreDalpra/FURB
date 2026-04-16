package main;

public interface Pilha<T> {
	
	public T pop();
	
	public T peek();
	
	public boolean estaVazia();
	
	public void liberar();

	void push(T valor);
}
