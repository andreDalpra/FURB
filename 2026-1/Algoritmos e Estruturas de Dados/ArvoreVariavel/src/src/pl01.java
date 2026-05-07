package src;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class pl01 {

	//ARVORE QUE SERA USADA
	Arvore<Integer> arvore = new Arvore<>();
	
	@BeforeEach
	void criaArvore() {
		NoArvore<Integer> n1  = new NoArvore<>(1);

		NoArvore<Integer> n2  = new NoArvore<>(2);
		NoArvore<Integer> n3  = new NoArvore<>(3);
		NoArvore<Integer> n4  = new NoArvore<>(4);

		NoArvore<Integer> n5  = new NoArvore<>(5);
		NoArvore<Integer> n6  = new NoArvore<>(6);
		NoArvore<Integer> n7  = new NoArvore<>(7);

		NoArvore<Integer> n8  = new NoArvore<>(8);

		NoArvore<Integer> n9  = new NoArvore<>(9);
		NoArvore<Integer> n10 = new NoArvore<>(10);

		n1.setPrimeiro(n2);

		n2.setProximo(n3);
		n3.setProximo(n4);

		n2.setPrimeiro(n5);

		n5.setProximo(n6);
		n6.setProximo(n7);	

		n3.setPrimeiro(n8);


		n4.setPrimeiro(n9);

		n9.setProximo(n10);

		arvore.setRaiz(n1);
	}
	@Test
	void Test1() {
		assertEquals("<1<2<5><6><7>><3<8>><4<9><10>>>", arvore.toString());
	}
	
	@Test
	void Test2() {
		assertEquals(false, arvore.estaVazia());
	}
	
	@Test
	void Test3() {
		assertEquals(10, arvore.contaNos());
	}
	
	@Test
	void Test4() {
		assertEquals(true, arvore.pertence(7));
	}
	
	@Test
	void Test5() {
		assertEquals(false, arvore.pertence(55));
	}
	
	

}
