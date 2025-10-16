package entidades;

import java.util.ArrayList;
import java.util.List;

import static mensagem.Mensagem.*;

public class Instrutor extends Pessoa {
	private String cref;
	private String especialidade;
	private List<Aluno> alunos = new ArrayList<>();
	private Instrutor mentor;

	public Instrutor(String nome, String cpf, String cref, String especialidade, Instrutor mentor) {
		super(nome, cpf);
		this.cref = cref;
		this.especialidade = especialidade;
		this.mentor = mentor;
	}

	public void adicionarAluno(Aluno aluno) {
		if (!alunos.contains(aluno)) {
			alunos.add(aluno);
			montaMensagem(101, new String[] { aluno.getNome(), aluno.getMatricula() });
		} else {
			montaMensagem(102, new String[] { aluno.getNome() });
			return;
		}
	}
	
	public boolean validaInstrutor(String nome, String cpf, String cref, String especialidade, Instrutor mentor) {
		if (nome.isBlank()) {
			montaMensagem(104, new String[] {getNome()});
			return false;
		}
		
		return true;
	}

	public void removerAluno(Aluno aluno) {
		alunos.remove(aluno);
	}

	@Override
	public String getResumo() {
	    String mentorNome = (mentor != null) ? mentor.getNome() : "Não possui mentor";
	    return "Instrutor: " + getNome()
	         + " | CREF: " + cref
	         + " | Especialidade: " + especialidade
	         + " | Mentor: " + mentorNome + "\n";
	}


	public String getCref() {
		return cref;
	}

	public void setCref(String cref) {
		this.cref = cref;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Instrutor getMentor() {
		return mentor;
	}

	public void setMentor(Instrutor mentor) {
		this.mentor = mentor;

	}

	@Override
	public String toString() {
		return getResumo();
	}

}
