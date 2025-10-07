package entidades;

public class Aluno {

	private String nome;
	private String matricula;
	private int idade;
	private double peso;
	private double altura;
	private Instrutor instrutor;
	private PlanoTreino treino;

	public Aluno(String nome, String matricula, int idade, double peso, double altura) {
		this.nome = nome;
		this.matricula = matricula;
		setIdade(idade);
		this.peso = peso;
		this.altura = altura;
	}

	public Aluno(String nome, String matricula) {
		this.nome = nome;
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public String getResumo(String resumo) {
		return "Aluno: " + nome + " (Matrícula: " + matricula + "), idade: " + idade + ", peso: " + peso + ", altura: "
				+ altura;
	}

	@Override
	public String toString() {
		return "Aluno: " + nome + " (Matrícula: " + matricula + "), idade: " + idade + ", peso: " + peso + ", altura: "
				+ altura;
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