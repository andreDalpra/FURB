package classes;

import java.util.Random;

public class JogoDaVelha_PC {

	private JogoDaVelha_Mapa mapa;
	private char letra;

	public JogoDaVelha_PC(JogoDaVelha_Mapa mapa) {
		this.mapa = mapa;
		this.letra = 'O';
	}

	public boolean joga() {
		Random rand = new Random();
		int i, j;

		do {
			i = rand.nextInt(3);
			j = rand.nextInt(3);
		} while (!mapa.jogar(i, j, letra));

		System.out.println("PC[" + i + "," + j + "]");
		return true;
	}

	public char getLetra() {
		return letra;
	}
}
