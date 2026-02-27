package main;

import java.util.ArrayList;
import java.util.List;

public class main {
	static ListaEstatica lista = new ListaEstatica();
    
	public static void main(String[] args) {
		List<String> nomes = new ArrayList<>();

		nomes.add("Andre");
		nomes.add("Ana");
		
		nomes.forEach(System.out::println);
	    
		
		

		//teste.test();
	}

}
