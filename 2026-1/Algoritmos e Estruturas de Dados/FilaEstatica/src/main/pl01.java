package main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class pl01 {

	@Test
	void Test1() {
		FilaVetor<Integer> fila = new FilaVetor<>(5);
		assertTrue(fila.estaVazia());
	}

	@Test
	void Test2() {
		FilaVetor<Integer> fila = new FilaVetor<>(5);
		fila.inserir(10);
		assertFalse(fila.estaVazia());
	}

	@Test
	void Test3() {
		FilaVetor<Integer> fila = new FilaVetor<>(10);
		fila.inserir(10);
		fila.inserir(20);
		fila.inserir(30);

		assertEquals(10, fila.retirar());
		assertEquals(20, fila.retirar());
		assertEquals(30, fila.retirar());
		assertTrue(fila.estaVazia());
	}

	@Test
	void Test4() {
		FilaVetor<Integer> fila = new FilaVetor<>(3);
		fila.inserir(10);
		fila.inserir(20);
		fila.inserir(30);

		assertThrows(FilaCheiaException.class, () -> fila.inserir(40));
	}

	@Test
	void Test5() {
		FilaVetor<Integer> fila = new FilaVetor<>(5);
		assertThrows(FilaVaziaException.class, () -> fila.retirar());
	}

	@Test
	void Test6() {
		FilaVetor<Integer> fila = new FilaVetor<>(5);
		fila.inserir(10);
		fila.inserir(20);
		fila.inserir(30);

		assertEquals(10, fila.peek());
		assertEquals(10, fila.retirar());
	}

	@Test
	void Test7() {
		FilaVetor<Integer> fila = new FilaVetor<>(5);
		fila.inserir(10);
		fila.inserir(20);
		fila.inserir(30);

		fila.liberar();
		assertTrue(fila.estaVazia());
	}

	@Test
	void Test8() {
		FilaVetor<Integer> fila1 = new FilaVetor<>(5);
		fila1.inserir(10);
		fila1.inserir(20);
		fila1.inserir(30);

		FilaVetor<Integer> fila2 = new FilaVetor<>(3);
		fila2.inserir(40);
		fila2.inserir(50);
        
		System.out.println(fila1.toString());
		System.out.println(fila2.toString());
		
		fila1.criarFilaConcatenada(fila2);
		fila2.liberar();

		assertEquals("10,20,30,40,50", fila1.toString());
		assertEquals(5, fila1.getTamanho());
	}
}
