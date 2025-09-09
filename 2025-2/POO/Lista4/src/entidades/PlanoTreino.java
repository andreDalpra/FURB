package entidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um plano de treino dentro da academia.
 * 
 * <p>Um plano de treino possui uma descrição e pode estar ativo ou inativo.
 * Ele pode ser criado já ativo ou inicialmente desativado, e possui métodos
 * para alterar seu estado.</p>
 * 
 * @author SEU_NOME_AQUI
 */
public class PlanoTreino {

    private String descricao;
    private boolean ativo;
    private List<Aluno> alunos  = new ArrayList<>();
    private List<Exercicios> exercicio;

    /**
     * Construtor completo para criar um plano de treino.
     * 
     * @param descricao descrição do plano (não pode ser nula ou vazia)
     * @param ativo indica se o plano será criado ativo ou não
     * @throws IllegalArgumentException se a descrição for nula ou vazia
     */
    
    public void incluirTreino(Aluno aluno) {
    	if (alunos.size() == 1 ) {
    		throw new RuntimeException("Não da para incluir mais que 1 aluno");
    	}
    	alunos.add(aluno);
    }
    public PlanoTreino(String descricao, boolean ativo, String nome, int series, int reps, double carga) {
        setDescricao(descricao);
        this.ativo = ativo;
        this.exercicio = exercicio.add(exercicio e);
    }

    /**
     * Construtor alternativo que cria um plano inicialmente inativo.
     * 
     * @param descricao descrição do plano (não pode ser nula ou vazia)
     * @throws IllegalArgumentException se a descrição for nula ou vazia
     */
    public PlanoTreino(String descricao) {
        setDescricao(descricao);
        this.ativo = false;
    }

    /**
     * Retorna a descrição do plano.
     * 
     * @return a descrição do plano
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do plano.
     * 
     * @param descricao descrição do plano (não pode ser nula ou vazia)
     * @throws IllegalArgumentException se a descrição for nula ou vazia
     */
    public void setDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do plano não pode ser vazia.");
        }
        this.descricao = descricao;
    }

    /**
     * Verifica se o plano está ativo.
     * 
     * @return {@code true} se o plano estiver ativo, {@code false} caso contrário
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * Ativa o plano de treino.
     */
    public void ativar() {
        this.ativo = true;
    }

    /**
     * Desativa o plano de treino.
     */
    public void desativar() {
        this.ativo = false;
    }

    /**
     * Retorna uma representação textual do plano de treino.
     * 
     * @return string representando o plano
     */
    @Override
    public String toString() {
        return "PlanoTreino: " + descricao + " (Ativo: " + ativo + ")";
    }

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}