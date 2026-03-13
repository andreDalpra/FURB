package main;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class pl01 {

	@Test
	void Teste01() {
		ListaDuplamenteEncadeada<Integer> lista = new ListaDuplamenteEncadeada<>();
		
		lista.inserir(5);
		lista.inserir(10);
		lista.inserir(15);
		lista.inserir(20);
		System.out.println("TESTE 1:");
		lista.exibir();
		System.out.println();
		lista.exibirOrdemInversa();
		System.out.println();
	}
	
	@Test
	void Teste02() {
		ListaDuplamenteEncadeada<Integer> lista = new ListaDuplamenteEncadeada<>();
		NoListaDupla<Integer> no = new NoListaDupla<>();
		
		
		lista.inserir(20);
		no = lista.buscar(20);
		assertEquals(20, no.getInfo());
	}
	
	@Test
	void Teste03() {
		ListaDuplamenteEncadeada<Integer> lista = new ListaDuplamenteEncadeada<>();
		NoListaDupla<Integer> no = new NoListaDupla<>();
		
		lista.inserir(5);
		lista.inserir(10);
		lista.inserir(15);
		lista.inserir(20);
		
		no = lista.buscar(10);
		assertEquals(10, no.getInfo());
	}
	
	@Test
	void Teste04() {
		ListaDuplamenteEncadeada<Integer> lista = new ListaDuplamenteEncadeada<>();
		
		lista.inserir(5);
		lista.inserir(10);
		lista.inserir(15);
		lista.inserir(20);
		System.out.println("TESTE 4:");
		lista.retirar(20);
		lista.exibirOrdemInversa();
		System.out.println();
	}
	
	@Test
	void Teste05() {
        ListaDuplamenteEncadeada<Integer> lista = new ListaDuplamenteEncadeada<>();
		
		lista.inserir(5);
		lista.inserir(10);
		lista.inserir(15);
		lista.inserir(20);
		System.out.println("TESTE 5:");
		lista.retirar(10);
		lista.exibirOrdemInversa();
		System.out.println();
	}
	
	@Test
	void Teste06() {
        ListaDuplamenteEncadeada<Integer> lista = new ListaDuplamenteEncadeada<>();
		
		lista.inserir(5);
		lista.inserir(10);
		lista.inserir(15);
		lista.inserir(20);
		System.out.println("TESTE 6:");
		lista.retirar(5);
		lista.exibirOrdemInversa();
		System.out.println();
	}
	
	@Test
	void Teste07() {
        ListaDuplamenteEncadeada<Integer> lista = new ListaDuplamenteEncadeada<>();
		
		lista.inserir(5);
		lista.inserir(10);
		lista.inserir(15);
		lista.inserir(20);
		
		lista.exibirOrdemInversa();
		
		//REMOVENDO OS NOS
		NoListaDupla<Integer> no1 = new NoListaDupla<>();
		NoListaDupla<Integer> no2 = new NoListaDupla<>();
		NoListaDupla<Integer> no3 = new NoListaDupla<>();
		NoListaDupla<Integer> no4 = new NoListaDupla<>();
		
		no1 = lista.buscar(5);
		no2 = lista.buscar(10);
		no3 = lista.buscar(15);
		no4 = lista.buscar(20);
		
		lista.liberar();
		
		assertNull(no1.getAnterior());
		assertNull(no1.getProximo());
		assertNull(no2.getAnterior());
		assertNull(no2.getProximo());
		assertNull(no3.getAnterior());
		assertNull(no3.getProximo());
		assertNull(no4.getAnterior());
		assertNull(no4.getProximo());
	
		
		System.out.println("LIBERANDO A LISTA");
	}


}
