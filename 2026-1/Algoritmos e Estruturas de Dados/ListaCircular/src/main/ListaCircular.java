package main;

public class ListaCircular<T> {
	private NoListaCircular<T> primeiro;
	private NoListaCircular<T> ultimo;
	
	public ListaCircular() {
		this.primeiro = null;
		this.ultimo = null;;
	}
	
	void inserir(T valor) {
		NoListaCircular<T> novo = new NoListaCircular<T>();
		
		novo.setInfo(valor);
		novo.setProximo(primeiro);
		
		primeiro = novo;
		ultimo = primeiro;
	}
	
	public NoListaCircular<T> buscar(T valor){
		NoListaCircular<T> p = new NoListaCircular<T>();
		
		while(p != ultimo) {
			if(p.getInfo().equals(valor)) {
				return p;
			}
		    p = p.getProximo();
		}
	    return null;
	}
	void exibir() {
		NoListaCircular<T> p = new NoListaCircular<T>();
		
		p = primeiro;
		
		if(p != null) {
			do {
				System.out.println(p.getInfo());
				p = p.getProximo();
			} while (p != primeiro);
		}
	}
	

}
