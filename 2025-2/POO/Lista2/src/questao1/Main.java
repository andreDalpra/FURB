package questao1;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Pessoa p = new Pessoa();
		// Capturando os DADOS
		for (int i = 0; i < p.getAltura().length; i++) {
			System.out.println("Qual seu nome? ");
			p.setNome(i, sc.next());
			System.out.println("Qual seu peso? ");
			p.setPeso(i, sc.nextDouble());
			System.out.println("Qual sua altura? ");
			p.setAltura(i, sc.nextDouble());
		}
		System.out.println("Relatório IMC");
		// IMPRIMINDO OS DADOS
		for (int i = p.getAltura().length - 1; i >= 0; i--) {
			System.out.println("Nome: " + p.getNome(i));
			System.out.println("\nPeso: " + p.getPeso(i));
			System.out.println("\nAltura: " + p.getAltura(i));
			System.out.println("\nIMC: " + p.calcularIMC(i));
		}
		sc.close();
	}
}
