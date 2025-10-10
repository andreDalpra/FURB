package controle;

import java.util.ArrayList;
import java.util.List;

import entidades.Aluno;
import entidades.Instrutor;
import entidades.Pessoa;
import entidades.PlanoTreino;

public class Dados {

	// listas que guardam todos os objetos
	public static List<Pessoa> pessoas = new ArrayList<>();
	public static List<Aluno> alunos = new ArrayList<>();
	public static List<Instrutor> instrutores = new ArrayList<>();
	public static List<PlanoTreino> planos = new ArrayList<>();

	// método de inicialização
	public static void inicializar() {
		// --- DADOS PRÉ-CADASTRADOS ---
		Instrutor i1 = new Instrutor("JOSILDO", "1782489315", "024", "PARKOUR POV", null);
		Instrutor i2 = new Instrutor("JOSILDO PAI", "21461927194", "025", "PARKOUR MESTRE", i1);

		Aluno a1 = new Aluno("Ana", "112412412412", "231", 25, 62.0, 1.68);
		Aluno a2 = new Aluno("Carlos", "11122233344", "232", 30, 75.0, 1.80);

		PlanoTreino pt1 = new PlanoTreino("Treino de Hipertrofia");
		PlanoTreino pt2 = new PlanoTreino("Treino de Força");

		// adiciona nas listas
		instrutores.add(i1);
		instrutores.add(i2);

		alunos.add(a1);
		alunos.add(a2);

		planos.add(pt1);
		planos.add(pt2);

		pessoas.addAll(alunos);
		pessoas.addAll(instrutores);
	}

	public static void listarTodosAlunos() {
		for (Aluno a : alunos) {
			System.out.println(a + "\n");
		}
	}
}
