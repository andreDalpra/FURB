package main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.DEFAULT)
class pl01 {

	//TESTE 1: estaVazia() = true
	@Test
	void Teste01() {
		ListaEncadeada<Integer> lista = new ListaEncadeada<Integer>();
		assertTrue(lista.estaVazio());		
	}
	
	//TESTE 2: estaVazia() = false
	@Test
	void Teste02() {
		ListaEncadeada<Integer> lista = new ListaEncadeada<Integer>();
		lista.inserir(5);
		assertFalse(lista.estaVazio());
	}
	
	//TESTE 3:
	/* Obter o primeiro objeto da lista.
       Conferir que tenha sido retornado nó e o nó contenha 5.
       Certificar-se que não haja mais nós
	 */
	@Test
	void Teste03() {

	    ListaEncadeada<Integer> lista = new ListaEncadeada<>();
	    lista.inserir(5);

	    NoLista<Integer> no = lista.obterNo(0);

	    assertNotNull(no);
	    assertEquals(5, no.getInfo());
	    assertNull(no.getProximo());
	    System.out.println("TESTE 03: ");
	    System.out.println(no.getProximo());
	    System.out.println();
	}
	
	//TESTE 04:
	/* Obter os objetos da lista e certificar-se que hajam
       apenas 3 nós e os valores devem ser 15, 10 e 5 (nesta ordem).
	 */
	@Test
	void Teste04() {
		ListaEncadeada<Integer> lista = new ListaEncadeada<>();
		lista.inserir(5);
		lista.inserir(10);
		lista.inserir(15);
		
		assertEquals(3, lista.obterComprimento());
		System.out.println("TESTE 04: ");
		lista.exibir();
		System.out.println();
	}
	
	//TESTE 05: Certificar-se que o método buscar() retorne um nó contendo o número 20
	@Test
	void Teste05() {
	    ListaEncadeada<Integer> lista = new ListaEncadeada<>();
	    lista.inserir(5);
	    lista.inserir(10);
	    lista.inserir(15);
	    lista.inserir(20);

	    NoLista<Integer> p_valor = lista.buscar(20);

	    assertEquals(20, p_valor.getInfo());
	}
	
	//TESTE 06: Certificar-se que o método buscar() retorne um nó contendo o número 15
	@Test
	void Teste06() {
	    ListaEncadeada<Integer> lista = new ListaEncadeada<>();
	    lista.inserir(5);
	    lista.inserir(10);
	    lista.inserir(15);
	    lista.inserir(20);

	    NoLista<Integer> p_valor = lista.buscar(15);

	    assertEquals(15, p_valor.getInfo());
	}
	
	//TESTE 07: buscar() deve resultar null.
	@Test
	void Teste07() {
		 ListaEncadeada<Integer> lista = new ListaEncadeada<>();
		 lista.inserir(5);
		 lista.inserir(10);
		 lista.inserir(15);
		 lista.inserir(20);

		 NoLista<Integer> p_valor = lista.buscar(50);

		 assertNull(p_valor);
	}
	
	//TESTE 08: 
	/*Após o algoritmo de remoção, navegar na lista e
      certificar-se que a lista contenha exclusivamente os números 5, 10 e 15.
	 */
	@Test
	void Test08() {
		ListaEncadeada<Integer> lista = new ListaEncadeada<>();
		lista.inserir(5);
		lista.inserir(10);
		lista.inserir(15);
		lista.inserir(20);
		
		lista.retirar(20);
		System.out.println("TESTE 08:");
		System.out.println("Retirei o 20");
		lista.exibir();
	}
	
	//TESTE 09: 
	/*Após o algoritmo de remoção, navegar na lista e
      certificar-se que a lista contenha exclusivamente os números 5, 10 e 20.
	 */
	@Test
	void Test09() {
		ListaEncadeada<Integer> lista = new ListaEncadeada<>();
		lista.inserir(5);
		lista.inserir(10);
		lista.inserir(15);
		lista.inserir(20);
		
		lista.retirar(15);
		System.out.println("TESTE 09:");
		System.out.println("Retirei o 15");
		lista.exibir();
	}
	
	//TESTE 10: obterNo(0) deve resultar no nó que armazena 20
	@Test
	void Test10() {
		ListaEncadeada<Integer> lista = new ListaEncadeada<>();
		lista.inserir(5);
		lista.inserir(10);
		lista.inserir(15);
		lista.inserir(20);
		
		NoLista<Integer> p_valor = lista.obterNo(0);

		 assertEquals(20, p_valor.getInfo());		
	}
	
	//TESTE 11: obterNo(3) deve resultar no nó que armazena 5
	@Test
	void Test11() {
		ListaEncadeada<Integer> lista = new ListaEncadeada<>();
		lista.inserir(5);
		lista.inserir(10);
		lista.inserir(15);
		lista.inserir(20);
		
		NoLista<Integer> p_valor = lista.obterNo(3);

		assertEquals(5, p_valor.getInfo());
	}
	
	//TESTE 12: obterNo(10) deve lançar a exceção (IndexOutOfBoundsException)
	@Test
	void Test12() {
	    ListaEncadeada<Integer> lista = new ListaEncadeada<>();
	    lista.inserir(5);
	    lista.inserir(10);
	    lista.inserir(15);
	    lista.inserir(20);

	    assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
	        lista.obterNo(10);
	    });
	}
	
	//TESTE 13: obterComprimento() deve resultar em 0.
	@Test
	void Test13() {
		ListaEncadeada<Integer> lista = new ListaEncadeada<>();
		assertEquals(0, lista.obterComprimento());
	}
	
	//TESTE 14: obterComprimento() deve resultar em 4.
	@Test
	void Test14() {
		ListaEncadeada<Integer> lista = new ListaEncadeada<>();
	    lista.inserir(5);
	    lista.inserir(10);
	    lista.inserir(15);
	    lista.inserir(20);
	    
	    assertEquals(4, lista.obterComprimento());
	}

}
