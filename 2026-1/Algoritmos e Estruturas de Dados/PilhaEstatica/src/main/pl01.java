package main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class pl01 {

	@Test
	void Test1() {
		PilhaVetor<Integer> pilha = new PilhaVetor<>(10);

		assertTrue(pilha.estaVazia());
	}

	@Test
	void Test2() {
		PilhaVetor<Integer> pilha = new PilhaVetor<>(5);
		pilha.push(1);
		assertFalse(pilha.estaVazia());
	}

	@Test
	void Test3() {
		PilhaVetor<Integer> pilha = new PilhaVetor<>(5);
		pilha.push(10);
		pilha.push(20);
		pilha.push(30);

		assertEquals(30, pilha.pop());
		assertEquals(20, pilha.pop());
		assertEquals(10, pilha.pop());
		assertTrue(pilha.estaVazia());
	}
	
	@Test
	void Test4() {
		PilhaVetor<Integer> pilha = new PilhaVetor<>(3);
		pilha.push(10);
		pilha.push(20);
		pilha.push(30);
		assertThrowsExactly(PilhaCheiaException.class, () -> {
		    pilha.push(40);
		});
	}
	
	@Test
	void Test5() {
		PilhaVetor<Integer> pilha = new PilhaVetor<>(5);
		assertThrowsExactly(PilhaVaziaException.class, () -> {
		    pilha.pop();
		});
	}
	
	@Test
	void Test6() {
		PilhaVetor<Integer> pilha = new PilhaVetor<>(3);
		pilha.push(10);
		pilha.push(20);
		pilha.push(30);
		assertEquals(30, pilha.peek());
    }
	
	@Test
	void Test7() {
		PilhaVetor<Integer> pilha = new PilhaVetor<>(3);
		pilha.push(10);
		pilha.push(20);
		pilha.push(30);
		pilha.liberar();
		assertTrue(pilha.estaVazia());
    }
	
	@Test
	void Test8() {
		PilhaVetor<Integer> pilha = new PilhaVetor<>(10);
		PilhaVetor<Integer> pilha2 = new PilhaVetor<>(10);
		
		pilha.push(10);
		pilha.push(20);
		pilha.push(30);
		
		pilha2.push(40);
		pilha2.push(50);
		
		pilha.concatenar(pilha2);
		pilha.toString();
    }
}

