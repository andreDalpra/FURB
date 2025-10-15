package entidades;

import java.util.Scanner;
import javax.swing.SwingUtilities;

import static controle.Dados.*;
import static mensagem.Mensagem.*;
import mensagem.CadastroMensagens;
import subclasses.InstrutorMusculacao;

public class Main {

	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {		
		CadastroMensagens.cadastro();
		abreTela();
		inicializarDados();
		inicializaMensagem();

		System.out.println("=== Sistema de Academia ===\n");
		System.out.println("Escolha o ambiente da aplicação:");
		System.out.println("1 - Ambiente de Testes (com dados pré-cadastrados)");
		System.out.println("2 - Ambiente de Menu (sem dados pré-cadastrados)");

		String l_opcao = sc.next();

		switch (l_opcao) {
		case "1":
	
				System.out.println("--- Ambiente de Testes ---");
				PlanoTreino pt1 = planos.get(0);
				PlanoTreino p1 = planos.get(1);
				Aluno a1 = alunos.get(0);
				Instrutor i1 = instrutores.get(0);
				Instrutor i2 = instrutores.get(1);

				// --- Testes de plano/treino ---
				System.out.println("\n--- Testando Planos ---");
				pt1.incluirTreino(a1);
				System.out.println(a1.getTreino());

				p1.incluirTreino(a1);
				System.out.println(p1);

				p1.ativar();
				p1.adicionarExercicio(new Exercicio("TESTE MAIN", 1, 0, 0.0));
				System.out.println(p1);
				p1.adicionarExercicio(new Exercicio("TESTE CORRIDA", 2, 3, 0.0));
				System.out.println(p1);

				// --- Teste de instrutores ---
				System.out.println("\n--- Testando Instrutores ---");
				InstrutorMusculacao im = new InstrutorMusculacao("TESTE", "1212312312", "123", "MUSCULACAO", i2);
				System.out.println(im.getResumo());

				i1.adicionarAluno(a1);
				i1.adicionarAluno(a1);
				System.out.println(a1); // tentativa de adicionar o mesmo aluno

				if (isTemMensagem()) {
					System.out.println("[DEBUG-MAIN-1] isTemMensagem() = " + isTemMensagem());
					mostrarMensagem(); // agora garante que o diálogo vai ganhar foco
					System.out.println("[DEBUG-MAIN-2] mostrarMensagem() retornou");
				}

				// --- Teste de polimorfismo: lista geral de pessoas ---
				System.out.println("\n--- Lista de Pessoas (polimorfismo) ---");
				for (Pessoa p : pessoas) {
					System.out.println(p.getResumo());
				}
				listarTodosAlunos();
				//Aluno.cadastraAluno();
				listarTodosAlunos();
			break;

		case "2":
			System.out.println("Menu principal:");
			break;

		default:
			System.out.println("Opção inválida, encerrando programa.");
			return;
		}
	}
}
