package main;

public class main {
	
	static ListaEstatica<Integer> lista = new ListaEstatica<>();
	
    public static void main(String[] args) {
		
		Teste10();
		Teste11();
	}
    
    static void Teste10() {
    	System.out.println("******************");
    	System.out.println("TESTE 10");
    	System.out.println("******************");
    	lista.liberar();
    	lista.inserir(5);
    	lista.inserir(10);
    	lista.inserir(15);
    	lista.inserir(20);
    	System.out.println("LISTA NORMAL");
    	lista.exibir();
    	System.out.println("LISTA INVERTIDA");
    	lista.inverter();
    	System.out.println();
    }
    
    static void Teste11() {
    	System.out.println("******************");
    	System.out.println("TESTE 11");
    	System.out.println("******************");
    	lista.liberar();
    	lista.inserir(5);
    	lista.inserir(10);
    	lista.inserir(15);
    	lista.inserir(20);
    	lista.inserir(25);
    	System.out.println("LISTA NORMAL");
    	lista.exibir();
    	System.out.println("LISTA INVERTIDA");
    	lista.inverter();
    	System.out.println();
    	
    }
}
