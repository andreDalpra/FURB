package classes;

import java.util.Scanner;

public class JogoDaVelha_Jogador {

	private JogoDaVelha_Mapa mapa;
	private char letra;

	public JogoDaVelha_Jogador(JogoDaVelha_Mapa mapa) {
		this.mapa = mapa;
		this.letra = 'X';
	}

	public boolean joga(Scanner teclado) {
		int i, j;
		do {
			System.out.println("Jogador");
			System.out.println("LINHA :");
			i = teclado.nextInt();
			System.out.println("COLUNA :");
			j = teclado.nextInt();

			if (i < 0 || i > 2 || j < 0 || j > 2) {
				System.out.println("Posição inválida! Escolha valores entre 0 e 2.");
			} else if (mapa.jogar(i, j, letra)) {
				return true;
			} else {
				System.out.println("Posição já ocupada! Escolha outra.");

			}
		} while (true);
	}

	public char getLetra() {
		return letra;
	}
}
