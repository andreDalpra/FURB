package classes;

public class JogoDaVelha_Mapa {

	private char[][] mapa = new char[3][3];

	public void limpaMapa() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				mapa[i][j] = ' ';
			}
		}
	}

	public void desenha(int jogada) {
		System.out.println("Jogada " + jogada + ":");
		for (int i = 0; i < 3; i++) {
			System.out.println(" " + mapa[i][0] + " | " + mapa[i][1] + " | " + mapa[i][2]);
			if (i < 2)
				System.out.println("-----------");
		}
	}

	public boolean jogar(int i, int j, char jogador) {
		if (i >= 0 && i < 3 && j >= 0 && j < 3 && mapa[i][j] == ' ') {
			mapa[i][j] = jogador;
			return true;
		}
		return false;
	}

	public boolean ganhou(char jogador) {
		for (int i = 0; i < 3; i++) {
			if (mapa[i][0] == jogador && mapa[i][1] == jogador && mapa[i][2] == jogador)
				return true;
			if (mapa[0][i] == jogador && mapa[1][i] == jogador && mapa[2][i] == jogador)
				return true;
		}
		return (mapa[0][0] == jogador && mapa[1][1] == jogador && mapa[2][2] == jogador)
				|| (mapa[0][2] == jogador && mapa[1][1] == jogador && mapa[2][0] == jogador);
	}
}
