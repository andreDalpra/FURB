package entidades;
import java.util.ArrayList;
import java.util.List;

import mensagem.Mensagem;

public class Instrutor {
    private String nome;
    private String cref;
    private String especialidade;
    private List<Aluno> alunos = new ArrayList<>();
    private Instrutor mentor;

    public Instrutor(String nome, String cref, String especialidade, Instrutor mentor) {
        this.nome = nome;
        this.cref = cref;
        this.especialidade = especialidade;
        this.mentor = mentor;
    }
	
	public void adicionarAluno(Aluno aluno, String op_msg) {
		if (!alunos.contains(aluno)) {
			alunos.add(aluno);
		} else {
			op_msg = Mensagem.montaMensagem(102, new String[] {aluno.getMatricula(), aluno.getNome()});
			return;
		}
	}
	
	public void removerAluno(Aluno aluno) {
		alunos.remove(aluno);
	}
	
	public String getResumo(String resumo) {
		resumo = "Nome: " + nome + " / CREF: " + cref + " / Especialidade: " + especialidade + " / Mentor: " + mentor + "\n";
		return resumo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		return "Instrutor [nome=" + nome + ", cref=" + cref + ", especialidade=" + especialidade + ", alunos=" + alunos
				+ ", mentor=" + mentor + ", getNome()=" + getNome() + ", getCref()=" + getCref()
				+ ", getEspecialidade()=" + getEspecialidade() + ", getAlunos()=" + getAlunos() + ", getMentor()="
				+ getMentor() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	

    


}
