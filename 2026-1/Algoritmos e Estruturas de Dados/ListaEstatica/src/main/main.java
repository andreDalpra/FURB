package main;

public class main {
	static ListaEstatica lista = new ListaEstatica();
    
	public static void main(String[] args) {
		
		pl01 teste = new pl01();
		int l_teste;
		
		lista.inserir(5);
		lista.inserir(10);
		lista.inserir(15);
		lista.inserir(20);
		l_teste = lista.obterElemento(0);
		System.out.println(l_teste);
		System.out.println(lista.toString());

		//teste.test();
	}

}
