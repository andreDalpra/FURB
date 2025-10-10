package entidades;

import java.util.Scanner;

import controle.Dados;
import mensagem.Mensagem;

public class Aluno extends Pessoa{

	private String matricula;
	private int idade;
	private double peso;
	private double altura;
	private Instrutor instrutor;
	private PlanoTreino treino;

	public Aluno(String p_nome, String p_cpf, String p_matricula, int p_idade, double p_peso, double p_altura) {
		super(p_nome, p_cpf);
		this.matricula = p_matricula;
		setIdade(p_idade);
		this.peso = p_peso;
		this.altura = p_altura;
	}

	public Aluno(String nome, String cpf, String matricula) {
		super(nome, cpf);
		this.matricula = matricula;
	}
	
	public static void cadastraAluno() {
	    Scanner sc = new Scanner(System.in);

	    System.out.print("Nome: ");
	    String nome = sc.nextLine();

	    System.out.print("CPF: ");
	    String cpf = sc.nextLine();

	    System.out.print("Matrícula: ");
	    String matricula = sc.nextLine();

	    System.out.print("Idade: ");
	    int idade = sc.nextInt();

	    System.out.print("Peso: ");
	    double peso = sc.nextDouble();

	    System.out.print("Altura: ");
	    double altura = sc.nextDouble();

	    Aluno aluno = new Aluno(nome, cpf, matricula, idade, peso, altura);
	    /* if (validaAluno){
	     *    Dados.alunos.add(aluno);
	     *    return;
	     *  }
	     *  
	     *  montaMensagem
	     * 
	     */
	    Dados.alunos.add(aluno);

	    Mensagem.montaMensagem(100, new String[] {aluno.getNome(), aluno.getMatricula() });
	}
	
	public void validaAluno(String p_nome, String p_cpf, String p_matricula, int p_idade, double p_peso, double p_altura) {
		//TODO: VALIDAR CADALU
	}


	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		if (idade < 0) {
			throw new IllegalArgumentException("Idade não pode ser negativa.");
		}
		this.idade = idade;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}
    @Override
	public String getResumo() {
		return "Aluno: " + getNome() + " (Matrícula: " + matricula + "), idade: " + idade + ", peso: " + peso + ", altura: "+ altura + "\n";
	}

	public Instrutor getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(Instrutor instrutor) {
		this.instrutor = instrutor;
	}

	public PlanoTreino getTreino() {
		return treino;
	}

	public void setTreino(PlanoTreino treino) {
		this.treino = treino;
	}

}