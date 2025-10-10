package entidades;

import java.util.Scanner;

import controle.Dados;
import mensagem.CadastroMensagens;
import mensagem.Mensagem;
import subclasses.InstrutorMusculacao;

public class Main {

	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("=== Testando Sistema de Academia ===\n");
		CadastroMensagens.cadastro();
		Dados.inicializar();

		PlanoTreino pt1 = Dados.planos.get(0);
		PlanoTreino p1 = Dados.planos.get(1);
		Aluno a1 = Dados.alunos.get(0);
		Instrutor i1 = Dados.instrutores.get(0);
		Instrutor i2 = Dados.instrutores.get(1);

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
		i1.adicionarAluno(a1); // tentativa de adicionar o mesmo aluno

		if (Mensagem.isTemMensagem()) {
			Mensagem.mostrarMensagem();
		}

		// --- Teste de polimorfismo: lista geral de pessoas ---
		System.out.println("\n--- Lista de Pessoas (polimorfismo) ---");
		for (Pessoa p : Dados.pessoas) {
			System.out.println(p.getResumo());
		}
		Dados.listarTodosAlunos();
		Aluno.cadastraAluno();
		Dados.listarTodosAlunos();;
	}
}
