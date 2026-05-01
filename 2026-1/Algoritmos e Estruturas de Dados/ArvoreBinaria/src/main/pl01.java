package main;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class pl01 {
	//ARVORE QUE SERA USADA
	ArvoreBinaria<Integer> arvore = new ArvoreBinaria<>();
	
	public void criaArvore() {
		//NOS DA ARVORE
		NoArvoreBinaria<Integer> no5 = new NoArvoreBinaria<>(5);
		NoArvoreBinaria<Integer> no6 = new NoArvoreBinaria<>(6);
		NoArvoreBinaria<Integer> no3 = new NoArvoreBinaria<>(3, no5, no6);
		
		NoArvoreBinaria<Integer> no4 = new NoArvoreBinaria<>(4);
		NoArvoreBinaria<Integer> no2 = new NoArvoreBinaria<>(2, no4, null);
		
		NoArvoreBinaria<Integer> no1 = new NoArvoreBinaria<>(1, no2, no3);
		arvore.setRaiz(no1);
	}
	@Test
	void Test1() {
		assertTrue(arvore.estaVazia());
	}
	
	@Test
	void Test2() {
		//NOS
		NoArvoreBinaria<Integer> no1 = new NoArvoreBinaria<>(5);
		arvore.setRaiz(no1);
		assertFalse(arvore.estaVazia());
	}
	
	@Test
	void Test3() {
		criaArvore();
		System.out.println(arvore.toString());
		assertEquals("<1 <<2 <<4 <> <>>> <>>> <<3 <<5 <> <>>> <<6 <> <>>>>>>", arvore.toString());
	}
	
	@Test
	void Test4() {
		criaArvore();
		
		//BUSCA A RAIZ
		assertTrue(arvore.pertence(1));
	}
	
	@Test
	void Test5() {
		criaArvore();
		
		//BUSCA O VALOR 3
		assertTrue(arvore.pertence(3));
	}
	
	@Test
	void Test6() {
		criaArvore();
		
		//BUSCA O VALOR 6
		assertTrue(arvore.pertence(6));
	}
	
	@Test
	void Test7() {
		criaArvore();
		
		//TENTA ACHAR O 10 (NAO EXISTE)
		assertFalse(arvore.pertence(10));
	}
	
	
	@Test
	void Test8() {
		criaArvore();
		
		//CONTA A QUANTIDADE DE NOS NA ARVORE
		assertEquals(6, arvore.contaNos());
	}
}
