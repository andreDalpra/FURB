package entidades;

import java.util.ArrayList;
import java.util.List;

enum Nivel {
	INICIANTE, INTERMEDIARIO, AVANCADO
}

public class PlanoTreino {

	private String descricao;
	private boolean ativo;
	private Aluno aluno;
	private Nivel nivel;
	private List<Exercicio> exercicio = new ArrayList<>();

	public PlanoTreino(String descricao, boolean ativo, Aluno aluno, Nivel nivel, String nome, int series, int reps,
			double carga) {
		setDescricao(descricao);
		this.ativo = ativo;
		this.exercicio = new ArrayList<>();
		this.exercicio.add(new Exercicio(nome, series, reps, carga));
	}

	public PlanoTreino(String p_descricao) {
		setDescricao(p_descricao);
		this.ativo = false;
	}

	// MÉTODOS

	public void incluirTreino(Aluno p_aluno) {
		this.aluno = p_aluno;
		aluno.setTreino(this);
	}

	public void listarExercicios() {
		String exerciciosStr = "";
		for (Exercicio ex : exercicio) {
			exerciciosStr += ex + "\n";
		}
		System.out.println(exerciciosStr);
	}

	public void adicionarExercicio(Exercicio exercicio) {
		this.exercicio.add(exercicio);
	}

	public void removerExercicio(Exercicio exercicio) {
		this.exercicio.remove(exercicio);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String p_descricao) {
		if (p_descricao == null || p_descricao.isBlank()) {
			throw new IllegalArgumentException("Descrição do plano não pode ser vazia.");
		}
		this.descricao = p_descricao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void ativar() {
		this.ativo = true;
	}

	public void desativar() {
		this.ativo = false;
	}

	public List<Exercicio> getExercicio() {
		return exercicio;
	}

	@Override
	public String toString() {
		String exerciciosStr = "";
		for (Exercicio ex : exercicio) {
			exerciciosStr += ex + "\n";
		}
		return "\nPlanoTreino: " + descricao + "\n (Ativo: " + ativo + ")\n EXERCICIOS: " + exerciciosStr;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
}