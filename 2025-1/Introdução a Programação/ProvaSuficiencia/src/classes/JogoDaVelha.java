package classes;

import java.util.Scanner;

public class JogoDaVelha {

	private JogoDaVelha_Mapa jogoMapa;
	private JogoDaVelha_PC pc;
	private JogoDaVelha_Jogador jogador;

	public JogoDaVelha() {
		this.jogoMapa = new JogoDaVelha_Mapa();
		this.pc = new JogoDaVelha_PC(jogoMapa);
		this.jogador = new JogoDaVelha_Jogador(jogoMapa);
	}

	private void jogar(Scanner teclado) {
		boolean pessoaJoga = true;
		int jogada = 0;

		jogoMapa.limpaMapa();
		System.out.println("Bem-vindo ao Jogo da Velha!");

		while (true) {
			jogoMapa.desenha(jogada);
			jogada++;

			if (pessoaJoga) {
				if (jogador.joga(teclado) && jogoMapa.ganhou(jogador.getLetra())) {
					System.out.println("Jogador VENCEU!!!");
					break;
				}
			} else {

				if (pc.joga() && jogoMapa.ganhou(pc.getLetra())) {
					System.out.println("PC VENCEU!!!");
					break;
				}
			}

			if (jogada == 9) {
				System.out.println("Empate!");
				break;
			}

			pessoaJoga = !pessoaJoga;
		}

		jogoMapa.desenha(jogada);
	}

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		while (true) {
			JogoDaVelha jogo = new JogoDaVelha();
			jogo.jogar(teclado);

			System.out.println("Deseja jogar novamente? (s/n)");
			char resposta = teclado.next().charAt(0);
			if (resposta != 's' && resposta != 'S') {
				System.out.println("GAME OVER");
				System.exit(0);
			}
		}

	}
}
